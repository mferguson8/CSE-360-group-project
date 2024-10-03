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
            START_NUM, //starts withnumbers
            HAS_SPEC, //has special
            DNE, //does not exist
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
            switch (p) {
                case FIRST:
                    return names.first != null;
                case MIDDLE:
                    return names.middle != null;
                case LAST:
                    return names.last != null;
                case PREFERRED:
                    return names.last != null;
            }
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
    }
}
