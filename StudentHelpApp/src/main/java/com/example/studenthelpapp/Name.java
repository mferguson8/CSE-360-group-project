package com.example.studenthelpapp;

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
            EMPTY, //If the name is an empty string (only allowed for middle)
            SUCCESS
        }

        public static enum Position {
            FIRST,
            MIDDLE,
            LAST,
            PREFERRED
        }

        private Status checkNE(Position which) { //check name exists
            if (!this.exists(which)) {
                return Status.DNE;
            }

            return Status.SUCCESS;
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
            return null; //should be unreachable
        }

        public Status checkNV(Position p) { //check name valididty
            String tname = this.gSName(p);
            if (tname == null) {
                return Status.DNE;
            } else if(tname.isEmpty() && p == Position.MIDDLE) {
            	//Only middle name is allowed to be empty
                this.setChecked(p, true);
                return Status.EMPTY;
            }
            if (StrChecker.isNumber(tname.charAt(0))) return Status.HAS_SPEC;

            boolean end_nums = false;
            char cc = 0;//current char
            boolean in = false;// is number

            for (int fv = 0; fv < tname.length(); fv++) {
                cc = tname.charAt(fv);
                in = StrChecker.isNumber(cc);
                if (end_nums) {
                    if(!in) return Status.OOPN;
                } else {
                    end_nums = in;
                    if (StrChecker.isSpecial(cc) && (!StrChecker.isANS(cc))) return Status.HAS_SPEC;
                }


            }
            this.setChecked(p, true);
            return Status.SUCCESS;
        }

        private void setChecked(Position p, boolean v) {
            switch (p) {
                case Position.FIRST:
                    this.checkedF = v;
                    break;
                case Position.MIDDLE:
                    this.checkedM = v;
                    break;
                case Position.LAST:
                    this.checkedL = v;
                    break;
                case Position.PREFERRED:
                    if (this.hasPreferred && v) this.checkedP = v;
                    this.checkedP = false;

            }
        }
    }
}
