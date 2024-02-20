package br.com.fulltime.fullarm.infra.packet.processor;

import java.util.List;

public interface LongFramePackageProcessor extends PackageProcessor {
    List<String> splitBytes(String hexString);
}
