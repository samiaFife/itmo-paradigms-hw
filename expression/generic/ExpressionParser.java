package expression.generic;

import expression.exceptions.*;
import expression.expandedOperations.Const;
//import expression.expandedExpression.TripleExpression;
import expression.expandedOperations.Variable;
import expression.expandedExpression.ExpandedExpression;
import expression.expandedExpression.UncheckedIntegerExpression;
import expression.expandedOperations.*;

import java.util.*;

public class ExpressionParser<T> {
    private static String source;
    private static int pos;
    private final ExpandedExpression<T> expanded;
    private TripleExpression<T> currentExpr = null; //текущее выражение (обновляется после каждого getElement, зануляется при '(')
    private static boolean inAction = false; //является ли распознаваемый элемент потенциальным операндом бинарного выражения

    private enum Operations {EXPRESSION, SET, CLEAR, ADD, SUBTRACT, MULTIPLY, DIVISION, COUNT, NEGATE, ABS, SQUARE, MOD}

    private static final Set<Character> NOT_CHAR_OPERATIONS_CHARS = new HashSet<>(Set.of('s', 'c', 'a', 'm'));
    private static final Set<Character> OPERANDS_CHARS = new HashSet<>(Set.of('x', 'y', 'z'));
    private static final Map<String, Operations> OPERATIONS = new HashMap<>(Map.ofEntries(
            Map.entry(")", Operations.EXPRESSION),
            Map.entry("set", Operations.SET),
            Map.entry("clear", Operations.CLEAR),
            Map.entry("+", Operations.ADD),
            Map.entry("-", Operations.SUBTRACT),
            Map.entry("*", Operations.MULTIPLY),
            Map.entry("/", Operations.DIVISION),
            Map.entry("count", Operations.COUNT),
            Map.entry("abs", Operations.ABS),
            Map.entry("mod", Operations.MOD),
            Map.entry("square", Operations.SQUARE)
    ));
    private static final Set<Operations> UNARY_OPERATIONS = new HashSet<>(Set.of(
            Operations.COUNT,
            Operations.ABS,
            Operations.SQUARE
    ));
    private static final Map<Operations, Integer> LESS_PRIORITY = new HashMap<>(Map.ofEntries(
            Map.entry(Operations.EXPRESSION, 40),
            Map.entry(Operations.SET, 30),
            Map.entry(Operations.CLEAR, 30),
            Map.entry(Operations.ADD, 20),
            Map.entry(Operations.SUBTRACT, 10),
            Map.entry(Operations.COUNT, 1),
            Map.entry(Operations.NEGATE, 1),
            Map.entry(Operations.MOD, 6),
            Map.entry(Operations.SQUARE, 1),
            Map.entry(Operations.ABS, 1),
            Map.entry(Operations.MULTIPLY, 6),
            Map.entry(Operations.DIVISION, 6)
    ));

    private static int bracketBalance;

    public ExpressionParser(ExpandedExpression<T> mode) {
        this.expanded = mode;
    }

    public TripleExpression<T> parse(String expression) throws ParseException {
        source = expression;
        pos = 0;
        bracketBalance = 0;
        currentExpr = null;
        TripleExpression<T> result = null;

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
        if (!eof() && (isOperand() || between('0', '9') || take('(') || isUnaryOperation())) {
            throw new MissingOperationException();
        }
    }

    private static String getNotCharOperation() throws UnknownOperationException {
        StringBuilder sb = new StringBuilder("" + source.charAt(pos));
        if (take('s')) {
            for (int i = 0; i < 2; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("set")) {
                //pos++;
                return sb.toString();
            } else {
                for (int i = 0; i < 3; i++) sb.append(source.charAt(++pos));
                if (sb.toString().equals("square")) {
                    //pos++;
                    if (checkAfterUnary()) throw new UnknownOperationException();
                    return sb.toString();
                } else {
                    throw new UnknownOperationException();
                }
            }
        } else if (take('c')) {
            for (int i = 0; i < 4; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("clear")) {
                //pos++;
                return sb.toString();
            } else {
                if (sb.toString().equals("count")) {
                    if (checkAfterUnary()) throw new UnknownOperationException();
                    return sb.toString();
                } else {
                    throw new UnknownOperationException();
                }
            }
        } else if (take('m')) {
            for (int i = 0; i < 2; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("mod")) {
                //pos++;
                if (checkAfterUnary()) throw new UnknownOperationException();
                return sb.toString();
            } else {
                throw new UnknownOperationException();
            }
        } else if (take('a')) {
            for (int i = 0; i < 2; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("abs")) {
                //pos++;
                if (checkAfterUnary()) throw new UnknownOperationException();
                return sb.toString();
            } else {
                throw new UnknownOperationException();
            }
        }
        return sb.toString();
    }

    private static boolean checkAfterUnary() {
        pos++;
        boolean flag = !eof() && (source.charAt(pos) != ' ' && source.charAt(pos) != '(');
        pos--;
        return flag;
    }

    private static boolean isUnaryOperation() throws ParseException {
        String op = getNotCharOperation();
        pos -= op.length() - 1;
        return (UNARY_OPERATIONS.contains(OPERATIONS.get(op)));
    }

    private TripleExpression<T> getElement(int... type) throws ParseException {
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
        } else if (take('s') || take('c') || take('m') || take('a')) {
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

    private TripleExpression<T> getVar() throws ParseException {
        TripleExpression<T> res = null;
        switch (source.charAt(pos)) {
            case 'x' -> res = new Variable<T>("x");
            case 'y' -> res = new Variable<T>("y");
            case 'z' -> res = new Variable<T>("z");
        }
        pos++;
        checkDoubleOperandUse();
        return res;
    }

    private TripleExpression<T> getFirstPriority() throws ParseException {
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

    private TripleExpression<T> resultIfNotNull(Operations type) throws ParseException {
        int ntype = LESS_PRIORITY.get(type) % 2;
        if (ntype == 0) {
            if (currentExpr != null) {
                TripleExpression<T> firstOperand = currentExpr;
                currentExpr = null;
                TripleExpression<T> secondOperand = analizeByPriority(type);
                switch (type) {
                    case SET -> {
                        return new ExpandedSet<T>(firstOperand, secondOperand, expanded);
                    }
                    case CLEAR -> {
                        return new ExpandedClear<T>(firstOperand, secondOperand, expanded);
                    }
                    case ADD -> {
                        return new ExpandedAdd<T>(firstOperand, secondOperand, expanded);
                    }
                    case SUBTRACT -> {
                        return new ExpandedSubtract<T>(firstOperand, secondOperand, expanded);
                    }
                    case MULTIPLY -> {
                        return new ExpandedMultiply<T>(firstOperand, secondOperand, expanded);
                    }
                    case DIVISION -> {
                        return new ExpandedDivide<T>(firstOperand, secondOperand, expanded);
                    }
                    case MOD -> {
                        return new ExpandedMod<T>(firstOperand, secondOperand, expanded);
                    }
                }
            } else {
                throw new MissingOperandException(2);
            }
        } else {
            currentExpr = null;
            skipWhitespace();
            TripleExpression<T> operand = getElement(1);
            if (operand == null) throw new MissingOperandException(1);
            switch (type) {
                case COUNT -> {
                    return new ExpandedCount<T>(operand, expanded);
                }
                case NEGATE -> {
                    return new ExpandedNegate<T>(operand, expanded);
                }
                case SQUARE -> {
                    return new ExpandedSquare<T>(operand, expanded);
                }
                case ABS -> {
                    return new ExpandedAbs<T>(operand, expanded);
                }
            }
        }
        return null;
    }

    private TripleExpression<T> getSecondPriority() throws ParseException {
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

    private TripleExpression<T> getThirdPriority() throws ParseException {
        inAction = true;
        String op = getNotCharOperation();
        pos++;
        return resultIfNotNull(OPERATIONS.get(op));
    }

    private TripleExpression<T> analizeByPriority(Operations operation) throws ParseException {
        TripleExpression<T> result = null;
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

    private static boolean checkLessPriority(Operations operation) throws UnknownOperationException {
        if (take('-') && inAction) return false;
        int thisOp;
        StringBuilder sb = new StringBuilder();
        if (NOT_CHAR_OPERATIONS_CHARS.contains(source.charAt(pos))) {
            String op = getNotCharOperation();
            pos -= op.length() - 1;
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

    private TripleExpression<T> getExpression() throws ParseException {
        pos++; //пропустим '('
        skipWhitespace();
        return analizeByPriority(Operations.EXPRESSION);
    }

    private boolean betweenNext(char from, char to) {
        return pos < source.length() - 1 && (from <= source.charAt(pos + 1) && source.charAt(pos + 1) <= to);
    }

    private TripleExpression<T> getNumber() throws ParseException {
        final StringBuilder sb = new StringBuilder();
        takeNumber(sb);
        T res;
        try {
            res = expanded.parseNum(sb.toString());
        } catch (NumberFormatException e) {
            throw new IllegalConstException(sb);
        }
        return new Const<T>(res);
    }

    private void takeNumber(final StringBuilder sb) throws ParseException {
        if (take('-')) {
            sb.append('-');
            pos++;
        }
        takeDigits(sb);
        if (!eof() && take('.')) {
            sb.append('.');
            pos++;
        }
        takeDigits(sb);
        checkDoubleOperandUse();
    }

    private void takeDigits(final StringBuilder sb) {
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
        String example = "x + (-30)";
        ExpressionParser<Integer> p = new ExpressionParser<Integer>(new UncheckedIntegerExpression());
        try {
            TripleExpression<Integer> expr = p.parse(example);
            System.out.println(expr.toString());
            System.out.println(expr.evaluate(-2147483648, 4, 3));
        } catch (ParseException | CalculateException e) {
            System.out.println(e.getMessage());
        }
    }
}
