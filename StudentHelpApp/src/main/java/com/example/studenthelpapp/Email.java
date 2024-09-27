public class Email {
    String username;
    String domain;

    public static Email make_email(String input){
        int at = input.indexOf('@');
        if (at == -1) {return null;} // no domain/no @
        if (input.indexOf('@', at + 1) != -1) {return null;} //multiple @s
        return new Email(
            input.substring(0, at),
            input.substring(at+1, input.length())
        );
    }

    private Email(String u, String d) {
        this.username = u;
        this.domain = d;
    }

    public String to_string() {return this.username + "@" + this.domain;}
}
