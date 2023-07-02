package expression.exceptions;

import expression.Const;
import expression.Variable;
import expression.*;

import java.util.*;

public class ExpressionParser implements TripleParser {
    private static String source;
    private static int pos;
    private static TripleExpression currentExpr = null; //текущее выражение (обновляется после каждого getElement, зануляется при '(')
    private static boolean inAction = false; //является ли распознаваемый элемент потенциальным операндом бинарного выражения
    private enum Operations {EXPRESSION, SET, CLEAR, ADD, SUBTRACT, MULTIPLY, DIVISION, COUNT, NEGATE}

    private static final Set<Character> NOT_CHAR_OPERATIONS_CHARS = new HashSet<>(Set.of('s', 'c'));
    private static final Set<Character> OPERANDS_CHARS = new HashSet<>(Set.of('x', 'y', 'z'));
    private static final Map<String, Operations> OPERATIONS = new HashMap<>(Map.of(
            ")", Operations.EXPRESSION,
            "set", Operations.SET,
            "clear", Operations.CLEAR,
            "+", Operations.ADD,
            "-", Operations.SUBTRACT,
            "*", Operations.MULTIPLY,
            "/", Operations.DIVISION,
            "count", Operations.COUNT
    ));
    private static final Map<Operations, Integer> LESS_PRIORITY = new HashMap<>(Map.of(
            Operations.EXPRESSION, 40,
            Operations.SET, 30,
            Operations.CLEAR, 30,
            Operations.ADD, 20,
            Operations.SUBTRACT, 10,
            Operations.COUNT, 1,
            Operations.NEGATE, 1,
            Operations.MULTIPLY, 6,
            Operations.DIVISION, 6
    ));

    private static int bracketBalance;

    public TripleExpression parse(String expression) throws ParseException {
        source = expression;
        pos = 0;
        bracketBalance = 0;
        currentExpr = null;
        TripleExpression result = null;

        while (!eof()) {
            skipWhitespace();
            result = getElement();
            skipWhitespace();
        }
        return result;
    }

    private static boolean isOperand() {
        return OPERANDS_CHARS.contains(source.charAt(pos));
    }

    private static void checkDoubleOperandUse() throws ParseException {
        if (eof()) {
            return;
        }
        if (NOT_CHAR_OPERATIONS_CHARS.contains(source.charAt(pos))) {
            throw new UnknownOperationException();
        }
        skipWhitespace();
        if (!eof() && (isOperand() || between('0', '9') || take('('))) {
            throw new MissingOperationException();
        }
    }

    private static TripleExpression getElement(int... type) throws ParseException {
        if (eof()) {
            throw new MissingOperandException(type.length > 0 ? 1 : 2);
        }
        if (take(')')) {
            bracketBalance--;
            if (bracketBalance < 0) {
                throw new BracketSequenceException('(');
            }
            checkDoubleOperandUse();

        } else if (take('x') || take('y') || take('z')) {
            currentExpr = getVar();
        } else if (take('*') || take('/')) {
            currentExpr = getFirstPriority();
        } else if (take('+') || take('-')) {
            currentExpr = getSecondPriority();
        } else if (take('s') || take('c')) {
            currentExpr = getThirdPriority();
        } else if (take('(')) {
            currentExpr = null;
            bracketBalance++;
            return getExpression();
        } else if (between('0', '9')) {
            currentExpr = getNumber();
        } else {
            throw new UnknownOperationException();
        }
        inAction = false;
        return currentExpr;
    }

    private static TripleExpression getVar() throws ParseException {
        TripleExpression res = null;
        switch (source.charAt(pos)) {
            case 'x' -> res = new Variable("x");
            case 'y' -> res = new Variable("y");
            case 'z' -> res = new Variable("z");
        }
        pos++;
        checkDoubleOperandUse();
        return res;
    }

    private static TripleExpression getFirstPriority() throws ParseException {
        inAction = true;
        if (take('*')) {
            pos++;
            skipWhitespace();
            return resultIfNotNull(Operations.MULTIPLY);
        } else if (take('/')) {
            pos++;
            skipWhitespace();
            return resultIfNotNull(Operations.DIVISION);
        }
        return null;
    }

    private static TripleExpression resultIfNotNull(Operations type) throws ParseException {
        int ntype = LESS_PRIORITY.get(type) % 2;
        if (ntype == 0) {
            if (currentExpr != null) {
                TripleExpression firstOperand = currentExpr;
                currentExpr = null;
                TripleExpression secondOperand = analizeByPriority(type);
                switch (type) {
                    case SET -> {
                        return new CheckedSet((CommonInterface) firstOperand, (CommonInterface) secondOperand);
                    }
                    case CLEAR -> {
                        return new CheckedClear((CommonInterface) firstOperand, (CommonInterface) secondOperand);
                    }
                    case ADD -> {
                        return new CheckedAdd((CommonInterface) firstOperand, (CommonInterface) secondOperand);
                    }
                    case SUBTRACT -> {
                        return new CheckedSubtract((CommonInterface) firstOperand, (CommonInterface) secondOperand);
                    }
                    case MULTIPLY -> {
                        return new CheckedMultiply((CommonInterface) firstOperand, (CommonInterface) secondOperand);
                    }
                    case DIVISION -> {
                        return new CheckedDivide((CommonInterface) firstOperand, (CommonInterface) secondOperand);
                    }
                }
            } else {
                throw new MissingOperandException(2);
            }
        } else {
            currentExpr = null;
            skipWhitespace();
            TripleExpression operand = getElement(1);
            if (operand == null) throw new MissingOperandException(1);
            switch (type) {
                case COUNT -> {
                    return new CheckedCount((CommonInterface) operand);
                }
                case NEGATE -> {
                    return new CheckedNegate((CommonInterface) operand);
                }
            }
        }
        return null;
    }

    private static TripleExpression getSecondPriority() throws ParseException {
        if (take('+')) {
            inAction = true;
            pos++;
            skipWhitespace();
            return resultIfNotNull(Operations.ADD);
        } else {
            if (betweenNext('0', '9')
                    && (currentExpr == null || inAction)) { //слева знак операции или начало выражения, причем сразу после минусы цифры - отрицательное число
                return getNumber();
            }
            pos++; //смотрим символ после минуса
            if (currentExpr == null || inAction) { //начало выражения или слева был знак операции - унарный минус
                skipWhitespace();
                return resultIfNotNull(Operations.NEGATE);
            }
            inAction = true;
            skipWhitespace();
            return resultIfNotNull(Operations.SUBTRACT);
        }
    }

    private static TripleExpression getThirdPriority() throws ParseException {
        inAction = true;
        StringBuilder sb = new StringBuilder();
        if (take('s')) {
            for (int i = 0; i < 2; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("et")) {
                pos++;
                return resultIfNotNull(Operations.SET);
            } else {
                throw new UnknownOperationException();
            }
        } else if (take('c')) {
            for (int i = 0; i < 4; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("lear")) {
                pos++;
                return resultIfNotNull(Operations.CLEAR);
            } else {
                if (sb.toString().equals("ount")) {
                    pos++;
                    if (!eof() && (source.charAt(pos) != ' ' && source.charAt(pos) != '(')) {
                        throw new UnknownOperationException();
                    }
                    return resultIfNotNull(Operations.COUNT);
                } else {
                    throw new UnknownOperationException();
                }
            }
        }
        return null;
    }

    private static TripleExpression analizeByPriority(Operations operation) throws ParseException {
        TripleExpression result = null;
        boolean isExpr = operation == Operations.EXPRESSION;
        skipWhitespace();
        while (!eof() && !checkLessPriority(operation)) { //парс останавливается перед операциями меньшего приоритета
            //skipWhitespace();
            result = getElement();
            skipWhitespace();
            if (eof() && isExpr) {
                throw new BracketSequenceException(')');
            }
        }
        if (isExpr) pos++; //пропустим ')'
        if (result == null) {
            throw new MissingOperandException(LESS_PRIORITY.get(operation) % 2 == 0 ? 2 : 1);
        }
        return result;
    }

    private static boolean checkLessPriority(Operations operation) {
        if (take('-') && inAction) return false;
        int thisOp;
        StringBuilder sb = new StringBuilder();
        if (NOT_CHAR_OPERATIONS_CHARS.contains(source.charAt(pos))) {
            if (take('s')) {
                sb.append('s');
                for (int i = 0; i < 2; i++) sb.append(source.charAt(++pos));
                pos -= 2;
            } else if (take('c')) {
                sb.append('c');
                for (int i = 0; i < 4; i++) sb.append(source.charAt(++pos));
                pos -= 4;
            }
            //pos++;
        } else {
            sb.append(source.charAt(pos));
        }
        try {
            thisOp = LESS_PRIORITY.get(OPERATIONS.get(sb.toString()));
        } catch (NullPointerException e) {
            return false;
        }
        //return LESS_PRIORITY.get(operation).contains(source.charAt(pos));
        boolean isBinary = operation != Operations.COUNT;
        if (isBinary) {
            return LESS_PRIORITY.get(operation) <= thisOp;
        } else {
            return LESS_PRIORITY.get(operation) < thisOp;
        }
    }

    private static TripleExpression getExpression() throws ParseException {
        pos++; //пропустим '('
        skipWhitespace();
        return analizeByPriority(Operations.EXPRESSION);
    }

    private static boolean betweenNext(char from, char to) {
        return pos < source.length() - 1 && (from <= source.charAt(pos + 1) && source.charAt(pos + 1) <= to);
    }

    private static TripleExpression getNumber() throws ParseException {
        final StringBuilder sb = new StringBuilder();
        takeInteger(sb);
        int res;
        try {
            res = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new IllegalConstException(sb);
        }
        return new Const(res);
    }

    private static void takeInteger(final StringBuilder sb) throws ParseException {
        if (take('-')) {
            sb.append('-');
            pos++;
        }
        if (take('0')) {
            sb.append('0');
            pos++;
        } else if (between('1', '9')) {
            takeDigits(sb);
        }
        checkDoubleOperandUse();
    }

    private static void takeDigits(final StringBuilder sb) {
        while (!eof() && between('0', '9')) {
            sb.append(source.charAt(pos++));
        }
    }

    private static boolean between(char from, char to) {
        return from <= source.charAt(pos) && source.charAt(pos) <= to;
    }

    private static boolean take(char expected) {
        return source.charAt(pos) == expected;
    }

    private static void skipWhitespace() {
        while (!eof() && Character.isWhitespace(source.charAt(pos))) {
            pos++;
        }
    }

    private static boolean eof() {
        return pos >= source.length();
    }

    public static void main(String[] args) {
        String example = "+";
        ExpressionParser p = new ExpressionParser();
        try {
            TripleExpression expr = p.parse(example);
            System.out.println(expr.toString());
            System.out.println(expr.evaluate(1, 1, 1));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
