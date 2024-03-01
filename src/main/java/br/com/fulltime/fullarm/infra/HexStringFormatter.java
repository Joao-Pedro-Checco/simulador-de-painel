package br.com.fulltime.fullarm.infra;

public class HexStringFormatter {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String format(String hexString) {
        if (hexString.length() == 2) {
            return hexString;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            String hexPair = hexString.substring(i, i + 2);
            stringBuilder.append(hexPair).append(" ");
        }

        return stringBuilder.toString().trim();
    }

    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
}
