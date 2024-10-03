public class Name {
    String first;
    String middle;
    String last;

    String preferred;

    private Name(String f, String m, String l, String p) {
        this.first = f;
        this.middle = m;
        this.last = l;
        this.preferred = p;
    }

    public String displayed() {
        if (this.preferred != null) {
            return this.preferred;
        }
        return this.first;
    }

    public String fullName() {
        return this.first + " " +
            this.middle + " " +
            this.last;
    }

    public static NameResult hasPreferred(String f, String m, String l, String p) {
        /*assert(p != null)*/
        return new NameResult(true ,new Name(f, m, l, p));
    }

    public static NameResult noPreferred(String f, String m, String l) {
        return new NameResult(false ,new Name(f, m, l, null));

    }

    public static class NameResult {
        boolean hasPreferred;
        Status preferred;
        boolean checkedP;

        boolean checkedF;
        boolean checkedM;
        boolean checkedL;

        Name names;

        public static enum Status {
            START_NUM, //starts with numbers
            HAS_SPEC, //has special
            DNE, //does not exist
            OOPN, //out of place number
            SUCCESS
        }

        private static enum Position {
            FIRST,
            MIDDLE,
            LAST,
            PREFERRED
        }

        private Status checkName(Position which) {
            if (!this.exists(which)) {
                return DNE
            }

            return SUCCESS;
        }

        private NameResult(boolean hp, Name n) {
            this.checkedP = false;
            this.hasPreferred = hp;

            this.checkedF = false;
            this.checkedM = false;
            this.checkedL = false;

            this.names = n;
        }

        public Name getName() {
            boolean pref = this.checkedP ^ !this.hasPreferred; //been checked, or doesn't have preferred
            //should not both be true at the same time, cannot check that which does not exist
            //nor should be hung up on checking something that doesn't exist

            if (pref && checkedF && checkedM && checkedL) {
                return this.names;
            }
            return null;
        }

        private boolean exists(Position p) {
           return this.gSName(p) != null;
        }

        private String gSName(Position p) {//get specific name
            switch (p) {
                case FIRST:
                    return names.first;
                case MIDDLE:
                    return names.middle;
                case LAST:
                    return names.last;
                case PREFERRED:
                    return names.last;
            }
        }

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

        public static boolean isANS(char c) { //is aaccepted name special, isAUNS - ia accepedt username special
            if (!isSpecial(c)) return false;
            final char[] ACCEPTED = {' ', '-', '\'', '.'};

            for (int fv = 0; fv < ACCEPTED.length; fv++) {
                if (ACCEPEDT[fv] == c) return true;
            }
            return false;
        }

        public Status checkName(Position p) {
            String tname = this.gSN(p);
            if (tname == null) return Status.DNE;
            if (isNumber(tname.charAt(0))) return Status.HAS_SPEC;

            boolean end_nums = false;
            char cc = 0;//current char
            boolean in = false;// is number

            for (int fv = 0; fv < tname.length(); fv++) {
                cc = tname.charAt(fv);
                in = isNumber(cc);
                if (end_nums) {
                    if(!in) return OOPN;
                } else {
                    end_nums = in;
                    if (isSpecial(cc) && (!isANS(cc))) return HAS_SPEC;
                }


            }
            return SUCCESS;
        }
    }
}
