package kr.kernel.teachme.common.util;

public class StringUtil {
    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String removeNotNumeric(final String str) {
        return str.replaceAll("\\D", "");
    }
}
