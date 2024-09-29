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

    private static long StrToSeed(String sseed) {
        long lseed = 0;
        for (int fv = 0; fv < sseed.length(); fv++) {
            lseed += (sseed.charAt(fv) << (fv % 56)) - fv;
        }

        return lseed;
    }

    private String optionalSeededGenerate(int length, String seed, boolean force) {
        if (seed == "" && !force) {
            return this.generate(length);
        }
        StringBuilder neo = new StringBuilder("");
        Random rng = new Random(StrToSeed(seed));

        for (int fv = 0; fv < length; fv++) {
            neo.append(bag[rng.nextInt(bag.length)]);
        }

        return neo.toString();
    }

    public String seededGenerate(int length, String seed) {
        return this.optionalSeededGenerate(length, seed, false);
    }

    public String forceSeededGenerate(int length, String seed) {
        return this.optionalSeededGenerate(length, seed, true);
    }

    private String optionalSeededGenerate(int length, long seed, boolean force) {
        if (seed == 0 && !force) {
            return this.generate(length);
        }

        StringBuilder neo = new StringBuilder("");
        Random rng = new Random(seed);

        for (int fv = 0; fv < length; fv++) {
            neo.append(bag[rng.nextInt(bag.length)]);
        }

        return neo.toString();
    }

    public String seededGenerate(int length, long seed) {
        return this.optionalSeededGenerate(length, seed, false);
    }

    public String forceSeededGenerate(int length, long seed) {
        return this.optionalSeededGenerate(length, seed, true);
    }
}
