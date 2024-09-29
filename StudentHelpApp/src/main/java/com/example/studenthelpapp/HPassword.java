import java.util.Random;

public class HPassword { //hashed password
    final static String SALT = "iamsalt";

    String password;

    public static HPassword hashSalt(String rawPswd) { //hashes and salts a password
        //TODO this function MUST be modified by Phase 3
        //This should NOT sotre actual password in memory
        return new HPassword(rawPswd + SALT);
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

        // for more divers character sets in otps
        //static boolean aryGened = false;
        //static int[] otpc = []; //one time password characters
        public static OTP getOTP() {
            StringBuilder notp = new StringBuilder("");
            Random rng = new Random();

            for (int fv = 0; fv < OTPL; fv++) {
                notp.append((char)(rng.nextInt(10) + 48)); //[0,10), ascii numbers 48-57 : 0-9
            }

            return new OTP(notp.toString());

        }

        private OTP(String notp) {
            this.otp = notp;
        }

        public String get() {return this.otp;}
        public HPassword toHashed() {return HPassword.hashSalt(this.otp);}

    }



}
