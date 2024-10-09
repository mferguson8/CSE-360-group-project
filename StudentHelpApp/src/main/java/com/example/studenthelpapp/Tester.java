public class Tester {
    static int testsRun = 0;
    static int testsSuccess = 0;
    public static void main(String[] args) {
        testEmail();
        testName();

        float tRCast = testsRun;
        float tSCast = testsSuccess;
        float centage = tSCast / tRCast;
        System.out.println(centage);

    }

    private static void runTest(boolean result, String TID, String success, String fail) {
        testsRun += 1;
        if (result) {
            testsSuccess += 1;
            System.out.println(TID + " (success): "+ success);
        }
        else System.out.println(TID + " (failure):  " + fail);
    }

    /* RUN TESTS */
    private static void testEmail() {
        runTest(tE1_Valid(), "tE1",
                "Can create a valid email", "can't create a valid email");
        runTest(tE2_OneAtOnly(), "tE2",
                "Can't create an email with multiple @s", "¿can? create an email with multiple @s");
        runTest(tE3_MustHaveTopDom(), "tE3",
                "Can't create an email with no top level domain", "¿can? create an email with no top level domain");
        runTest(tE4_NoDom(), "tE4",
                "Can't create an email without domain", "¿can? create an email with no domain");
        //TODO: check for invalid characters

    }

    private static void testName() {
        runTest(tN1_ValidWith(), "tN1",
                "Can create a valid name with preferred", "can't create a valid name with preferred");
        //TODO: test for null preferred, with hasPreferred

    }

    /* TESTS */
    private static boolean tE1_Valid() {
        final String testStr = "test@test.test";
        Email.EmailResult er = Email.make_email(testStr);
        boolean result = er.check() == Email.EmailResult.Status.SUCCESS;
        Email e = er.get_email();
        return result && (e.toString().equals(testStr));
    }

    private static boolean tE2_OneAtOnly() {
        final String testStr = "test@test@test.test";
        Email.EmailResult er = Email.make_email(testStr);
        Email.EmailResult.Status erc = er.check();
        if (erc == Email.EmailResult.Status.MULT_ATS) return true;
        //room for debugging info if needed

        return false;
    }

    private static boolean tE3_MustHaveTopDom() {
        final String testStr = "test@testtest";
        Email.EmailResult er = Email.make_email(testStr);
        Email.EmailResult.Status erc = er.check();
        if (erc == Email.EmailResult.Status.INV_DOM) return true;

        return false;
    }


    private static boolean tE4_NoDom() {
        final String testStr = "testtest.test";
        Email.EmailResult er = Email.make_email(testStr);
        Email.EmailResult.Status erc = er.check();
        if (erc == Email.EmailResult.Status.NO_DOM) return true;

        return false;
    }

    private static boolean tN1_ValidWith() {
       final Name.NameResult nr = Name.hasPreferred("first", "middle", "last", "pref");
       boolean result = nr.checkNV(Name.NameResult.Position.FIRST) == Name.NameResult.Status.SUCCESS;
       result = result && (nr.checkNV(Name.NameResult.Position.MIDDLE) == Name.NameResult.Status.SUCCESS);
       result = result && (nr.checkNV(Name.NameResult.Position.LAST) == Name.NameResult.Status.SUCCESS);
       result = result && (nr.checkNV(Name.NameResult.Position.PREFERRED) == Name.NameResult.Status.SUCCESS);
       Name n = nr.getName();
       //System.out.println("pre null");
       if (n == null) return false;
       //System.out.println("post null");
       result = result && n.fullName().equals("first middle last");
       return result;
    }

    private static boolean tN2_VWithOutMid() {
       final Name.NameResult nr = Name.hasPreferred("first", "", "last", "pref");
       boolean result = nr.checkNV(Name.NameResult.Position.FIRST) == Name.NameResult.Status.SUCCESS;
       result = result && (nr.checkNV(Name.NameResult.Position.MIDDLE) == Name.NameResult.Status.EMPTY);
       result = result && (nr.checkNV(Name.NameResult.Position.LAST) == Name.NameResult.Status.SUCCESS);
       result = result && (nr.checkNV(Name.NameResult.Position.PREFERRED) == Name.NameResult.Status.SUCCESS);
       Name n = nr.getName();
       if (n == null) return false;
       result = result && n.fullName().equals("first last");

       return result;
    }
}
