package org.acme;

public class Token {

    private static final String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateToken() {
        return generateToken(32);
    }

    public static String generateToken(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int ran = (int)(Math.random()*charSet.length());
            stringBuilder.append(charSet.charAt(ran));
        }
        return stringBuilder.toString();
    }

}
