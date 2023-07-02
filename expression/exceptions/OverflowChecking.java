package expression.exceptions;

public class OverflowChecking {
    public static boolean multiplyOverflow(int a, int b) {
        boolean flag = false;
        if (a == Integer.MIN_VALUE && b != 0 && b != 1) {
            flag = true;
        } else if (b == Integer.MIN_VALUE && a != 0 && a != 1) {
            flag = true;
        } else if (a > 0 && b > 0) {
            if (a > Integer.MAX_VALUE / b || b > Integer.MAX_VALUE / a) {
                flag = true;
            }
        } else if (a < 0 && b < 0) {
            if (-a > Integer.MAX_VALUE / (-b) || -b > Integer.MAX_VALUE / (-a)) {
                flag = true;
            }
        } else if (a < 0 && b > 0) {
            if (a < Integer.MIN_VALUE / b) {
                flag = true;
            }
        } else if (a > 0 && b < 0) {
            if (b < Integer.MIN_VALUE / a) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean addOverflow(int a, int b) {
        return (a > 0 && b > 0 && b > Integer.MAX_VALUE - a)
                || (a < 0 && b < 0 && b < Integer.MIN_VALUE - a);
    }

    public static boolean divideOverflow(int a, int b) {
        return (a == Integer.MIN_VALUE && b == -1);
    }

    public static boolean subtractOverflow(int a, int b) {
        boolean flag = false;
        if (a < 0 && b > 0) { // -a - b = -(a+b)
            if (a == Integer.MIN_VALUE || (b > Integer.MAX_VALUE + a + 1)) {
                flag = true;
            }
        } else if (a > 0 && b < 0) { // a - -b = a + b
            if (b == Integer.MIN_VALUE || (a > Integer.MAX_VALUE + b)) {
                flag = true;
            }
        } else if (a == 0 && b == Integer.MIN_VALUE) {
            flag = true;
        }
        return flag;
    }

    public static boolean negateOverflow(int a) {
        return a == Integer.MIN_VALUE;
    }
}
