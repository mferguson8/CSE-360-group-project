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

    public NameResult withPreferred() {

    }

    public static class NameResult {
        boolean hasPreferred;
        Status preferred;
        boolean checkedP;

        Status first;
        boolean checkedF;

        Status middle;
        boolean checkedM;

        Status last;
        boolean checkedL;
        public static enum Status {
            START_NUM, //starts withnumbers
            HAS_SPEC, //has special
            SUCCESS
        }
    }
}
