public class Tester {
    static int testsRun = 0;
    static int testsSuccess = 0;
    public static void main(String[] args) {
        testEmail();

        float tRCast = testsRun;
        float tSCast = testsSuccess;
        float centage = tSCast / tRCast;
        System.out.println(centage);

    }

    private static void testEmail() {
        runTest(tE1_Valid(), "tE1", "Can create a valid email", "can't create a valid email");

    }

    private static boolean tE1_Valid() {
        final String testStr = "test@test.test";
        Email.EmailResult er = Email.make_email(testStr);
        boolean result = er.check() == Email.EmailResult.Status.SUCCESS;
        Email e = er.get_email();
        return result && (e.toString().equals(testStr));
    }

    private static void runTest(boolean result, String TID, String success, String fail) {
        testsRun += 1;
        if (result) {
            testsSuccess += 1;
            System.out.println(TID + ": "+ success);
        }
        else System.out.println(TID + ":  " + fail);

    }
}
