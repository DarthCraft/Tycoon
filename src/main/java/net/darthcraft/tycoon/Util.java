package net.darthcraft.tycoon;

public class Util {

    public static final int posNegMod(int num, int div) {
        return num < 0 ?  ((num % div) + div) % div : num % div;
    }
}
