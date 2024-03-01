package br.com.fulltime.fullarm.infra;

import org.springframework.stereotype.Service;

@Service
public class ChecksumGeneratorImpl implements ChecksumGenerator {
    @Override
    public String generateChecksum(String hexString) {
        String templateChecksumString = hexString + "00";
        byte[] byteArray = HexStringConverter.hexStringToByteArray(templateChecksumString);
        byte checksum = byteArray[0];
        for (int i = 1; i < byteArray.length; i++) {
            checksum ^= byteArray[i];
        }

        byte[] bytes = new byte[]{(byte) ~checksum};
        return HexStringFormatter.printHexBinary(bytes);
    }
}
