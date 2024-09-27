public class Email {
    // sections as dictated by: https://itstillworks.com/3-parts-email-address-22094.html
    String username;
    String domain;

    public static EmailResult make_email(String input){
        int at = input.indexOf('@');
        if (at == -1) {
            return EmailResult.make_invalid(EmailResult.Status.NO_DOM);
        } // no domain/no @
        if (input.indexOf('@', at + 1) != -1) {
            return EmailResult.make_invalid(EmailResult.Status.MULT_ATS);
        } //multiple @s
        Email em = new Email(
            input.substring(0, at),
            input.substring(at+1, input.length())
        );
        return EmailResult.make_valid(EmailResult.Status.SUCCESS, em);
    }

    private Email(String u, String d) {
        this.username = u;
        this.domain = d;
    }

    public String to_string() {return this.username + "@" + this.domain;}

    public static class EmailResult {
        Status status;
        boolean checked = false;
        private Email email;

        public static enum Status {
            NO_DOM, //no domain
            MULT_ATS, //multiple ats
            SUCCESS
        }

        public Status check() {
            if (this.status == Status.SUCCESS) checked = true;
            return this.status;
        }

        public Email get_email() {
            if (checked) return this.email;
            return null;
        }

        public static EmailResult make_invalid(Status s) {
            return new EmailResult(s, null);
        }

        public static EmailResult make_valid(Status s, Email e) {
            if (s == Status.SUCCESS) return new EmailResult(s, e);
            return make_invalid(s);
        }

        public EmailResult(Status s, Email e) {
            this.status = s;
            this.email = e;
        }

        public static String parse_status(Status s) {
            switch (s) {
                case SUCCESS:
                    return "success";
                case NO_DOM:
                    return "No @s, no domain";
                case MULT_ATS:
                    return "Too many @s";
            }
            return "Null status";
        }
    }
}
