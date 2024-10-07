public class StrChecker {
    public static boolean isLower(char c) {
        return ('a' <= c) && (c <= 'z');
    }

    public static boolean isUpper(char c) {
        return ('A' <= c) && (c <= 'Z');
    }

    public static boolean isAlpha(char c) {
        return isLower(c) || isUpper(c);
    }

    public static boolean isNumber(char c) {
        return ('0' <= c) && (c <= '9');
    }

    public static boolean isSpecial(char c) {
        return !(isLower(c) || isUpper(c) || isAlpha(c) || isNumber(c));
    }

    private static boolean iGV(char c) { //is gmail valid
        return isLower(c) ||
            isNumber(c) ||
            (c == '.');
    }
    public static boolean isANS(char c) { //is aaccepted name special, isAUNS - ia accepedt username special
        if (!StrChecker.isSpecial(c)) return false;
        final char[] ACCEPTED = {' ', '-', '\'', '.'};

        for (int fv = 0; fv < ACCEPTED.length; fv++) {
            if (ACCEPTED[fv] == c) return true;
        }
        return false;
    }
}
