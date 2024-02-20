package br.com.fulltime.fullarm.infra;

public class HexStringFormatter {
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
}
