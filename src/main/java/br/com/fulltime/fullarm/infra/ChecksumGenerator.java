package br.com.fulltime.fullarm.infra;

public interface ChecksumGenerator {
    byte generateChecksum(String hexString);
}
