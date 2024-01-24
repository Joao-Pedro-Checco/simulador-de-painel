package br.com.fulltime.fullarm.infra;

public class HexStringConverter {
    public static byte[] hexStringToByteArray(String hexString) {
        String spacelessString = hexString.replace(" ", "");
        int len = spacelessString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(spacelessString.charAt(i), 16) << 4)
                    + Character.digit(spacelessString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
