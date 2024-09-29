import java.util.Random;
public class RngStrGen {// random string generation
    //for salt and otp generation

    private char[] bag = {};

    public RngStrGen(boolean numbers, boolean upper, boolean lower) {
        int llen = 0; //length len
        llen += numbers ? 10 : 0;
        llen += upper ? 26 : 0;
        llen += lower ? 26 : 0;

        bag = new char[llen];

        int curInd = 0;

        if (numbers) {
            for (char num = 48; num <= 57; num++) { //ascii numbers: 48-57
                bag[curInd] = num;
                curInd++;
            }
        }

        if (upper) {
            for (char up = 65; up <= 90; up++) { //ascii uppercase letters 65-90
                bag[curInd] = up;
                curInd++;

            }
        }
        if (lower) {
            for (char low = 97; low <= 122; low++) { //ascii lowercase letters 97-122
                bag[curInd] = low;
                curInd++;

            }
        }

    }

    public String generate(int length) {
        StringBuilder neo = new StringBuilder("");
        Random rng = new Random();

        for (int fv = 0; fv < length; fv++) {
            neo.append(bag[rng.nextInt(bag.length)]);
        }

        return neo.toString();
    }
}
