public class Password {
    String password;

    final static String SALT = "iamsalt";
    public static Password hashSalt(String rawPswd) { //hashes and salts a password
        //TODO this function MUST be modified by Phase 3
        //This should NOT sotre actual password in memory
        return new Password(rawPswd + SALT);
    }

    private Password(String pswd) {
        this.password = pswd;
    }

}
