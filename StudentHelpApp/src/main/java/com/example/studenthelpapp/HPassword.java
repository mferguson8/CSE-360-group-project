public class HPassword { //hashed password
    String password;

    public static HPassword hashSalt(String rawPswd) { //hashes and salts a password
        //TODO this function MUST be modified by Phase 3
        //This should NOT store actual password in memory
        final RngStrGen rsg = new RngStrGen(true, true, true);
        final String SALT = rsg.seededGenerate(rawPswd.length(), 0);
        return new HPassword(rawPswd);
    }

    private HPassword(String pswd) {
        this.password = pswd;
    }

    public String getHashed() {return this.password;}

    public static boolean areEqual(String pswdA, String pswdB) { //perhaps unnecessary
        return pswdA.equals(pswdB);
    }

    public static class OTP {
        String otp;

        final static int OTPL= 8; // one time password length
        final static RngStrGen RSG = new RngStrGen(true, false, false);

        // for more divers character sets in otps
        //static boolean aryGened = false;
        //static int[] otpc = []; //one time password characters
        public static OTP getOTP() {
            return new OTP(RSG.generate(OTPL));

        }

        private OTP(String notp) {
            this.otp = notp;
        }

        public String get() {return this.otp;}
        public HPassword toHashed() {return HPassword.hashSalt(this.otp);}

    }



}
