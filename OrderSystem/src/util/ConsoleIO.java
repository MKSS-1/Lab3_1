package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIO {
    private ConsoleIO() {}

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static String requestStringInput(String prompt) {
        print(prompt);
        return readString();
    }

    public static int requestIntInput(String prompt) {
        print(prompt);
        return readInt();
    }

    public static String readString() {
        String result;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            result = in.readLine();
        } catch(IOException e) {
            result = "";
        }
        return result;
    }

    public static int readInt() {
        int result = 0;
        String v = null;
        try {
            v = readString();
            result = Integer.decode(v);
        } catch(NumberFormatException e) {
            return 0;
        }

        return result;
    }

    public static boolean readBoolean() {
        boolean result;
        try {
            result = Boolean.parseBoolean(readString());
        } catch(NumberFormatException e) {
            result = false;
        }

        return result;
    }

    public static double readDouble() {
        double result;
        try {
            result = Double.parseDouble(readString());
        } catch(NumberFormatException e) {
            result = 0d;
        }

        return result;
    }

    public static float readFloat() {
        float result;
        try {
            result = Float.parseFloat(readString());
        }
        catch(NumberFormatException e) {
            result = 0f;
        }

        return result;
    }
}
