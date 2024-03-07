package br.com.fulltime.fullarm.infra.packet.parser.status;

import br.com.fulltime.fullarm.core.packet.GenericPackage;
import br.com.fulltime.fullarm.core.packet.constants.PackageType;
import br.com.fulltime.fullarm.core.packet.StatusPackage;
import br.com.fulltime.fullarm.core.panel.components.*;
import br.com.fulltime.fullarm.infra.ChecksumGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StatusPackageParserImpl implements StatusPackageParser {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
    private final ChecksumGenerator checksumGenerator;

    public StatusPackageParserImpl(ChecksumGenerator checksumGenerator) {
        this.checksumGenerator = checksumGenerator;
    }

    @Override
    public String parsePackage(GenericPackage genericPackage) {
        StatusPackage statusPackage = (StatusPackage) genericPackage;
        List<Zone> zones = statusPackage.getZones();
        List<Boolean> activatedPartitions = statusPackage.getActivatedPartitions();
        List<Pgm> pgmList = statusPackage.getPgmList();

        String openZonesBytes = parseZones(zones, Zone::isOpen, 0, zones.size());
        String violatedZonesBytes = parseZones(zones, Zone::isViolated, 0, zones.size());
        String bypassedZones = parseZones(zones, Zone::isBypassed, 0, zones.size());
        String panelModel = statusPackage.getPanelModel();
        String firmwareVersion = statusPackage.getFirmwareVersion();
        String partitionStatus = statusPackage.isPartitioned() ? "01" : "00";
        String abPartitions = parseActivatedPartitions(activatedPartitions.subList(0, 2));
        String cdPartitions = parseActivatedPartitions(activatedPartitions.subList(2, 4));
        String generalStatusByte = parseGeneralStatus(statusPackage);
        String dateTime = parseDateTime(statusPackage.getDateTime());
        String powerSupplyByte = parsePowerSupply(statusPackage);
        String keyboardAndReceiverProblems = parseKeyboardAndReceiverProblems(statusPackage);
        String expanderProblemsByte1 = parseExpanderProblemsByte1(statusPackage);
        String expanderProblemsByte2 = parseExpanderProblemsByte2(statusPackage);
        String batteryLevelByte = parseBatteryLevelByte(statusPackage.getBattery());
        String keyboardTamperByte = parseKeyboardTamper(statusPackage.getKeyboards());
        String systemProblemsByte = parseSystemProblems(statusPackage);
        String tamperedZonesByte = parseZones(zones, Zone::isTampered, 0, 8);
        String shortCircuitedZonesByte = parseZones(zones, Zone::isShortCircuit, 0, 8);
        String sirenAndPgmStatusByte = parseSirenAndPgmStatus(statusPackage);
        String lowBatterySensorBytes = parseZones(zones, Zone::isBatteryLowOnWirelessSensor, 16, zones.size());
        String pgmStatusByte1 = parsePgmStatus(pgmList, Pgm::isTurnedOn, 3, 11);
        String pgmStatusByte2 = parsePgmStatus(pgmList, Pgm::isTurnedOn, 11, pgmList.size());

        String hexString = "37E9" + openZonesBytes + violatedZonesBytes + bypassedZones +
                panelModel + firmwareVersion + partitionStatus + abPartitions + cdPartitions + generalStatusByte +
                dateTime + powerSupplyByte + keyboardAndReceiverProblems + expanderProblemsByte1 +
                expanderProblemsByte2 + "00" + batteryLevelByte + keyboardTamperByte + systemProblemsByte +
                tamperedZonesByte + shortCircuitedZonesByte + sirenAndPgmStatusByte + lowBatterySensorBytes +
                pgmStatusByte1 + pgmStatusByte2;

        String checksum = checksumGenerator.generateChecksum(hexString);
        return formatBytes(hexString + checksum);
    }

    @Override
    public boolean canParse(GenericPackage genericPackage) {
        if (!(genericPackage instanceof StatusPackage)) {
            return false;
        }

        return genericPackage.getPackageType() == PackageType.STATUS;
    }

    private String parseZones(List<Zone> zones, Function<Zone, Boolean> lambda, int start, int end) {
        List<Boolean> boolList = zones.subList(start, end).stream().map(lambda).collect(Collectors.toList());
        BitSet bitSet = boolListToBitSet(boolList);
        List<Byte> bytes = bitSetToByteList(bitSet, boolList.size() / 8);

        return printHexBinary(bytes);
    }

    private String parsePgmStatus(List<Pgm> pgmList, Function<Pgm, Boolean> lambda, int start, int end) {
        List<Boolean> pgmsTurnedOn = pgmList.subList(start, end).stream().map(lambda).collect(Collectors.toList());
        BitSet bitSet = boolListToBitSet(pgmsTurnedOn);
        List<Byte> bytes = bitSetToByteList(bitSet, pgmsTurnedOn.size() / 8);

        return printHexBinary(bytes);
    }

    private String parseActivatedPartitions(List<Boolean> activatedPartitions) {
        BitSet bitSet = boolListToBitSet(activatedPartitions);

        int byteAmount = activatedPartitions.size() % 8 == 0 ? activatedPartitions.size() / 8 : 1;
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseGeneralStatus(StatusPackage statusPackage) {
        BitSet bitSet = new BitSet();
        bitSet.set(0, statusPackage.isProblemsDetected());
        bitSet.set(1, statusPackage.isSirenTurnedOn());
        bitSet.set(2, statusPackage.isBurgledZones());
        bitSet.set(3, statusPackage.isPanelArmed());

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseDateTime(LocalDateTime localDateTime) {
        byte hour = (byte) localDateTime.getHour();
        byte minute = (byte) localDateTime.getMinute();
        byte day = (byte) localDateTime.getDayOfMonth();
        byte month = (byte) localDateTime.getMonthValue();
        byte year = (byte) (localDateTime.getYear() - 2000);

        List<Byte> bytes = Arrays.asList(hour, minute, day, month, year);
        return printHexBinary(bytes);
    }

    private String parsePowerSupply(StatusPackage statusPackage) {
        BitSet bitSet = new BitSet();
        bitSet.set(0, statusPackage.isElectricNetworkFail());
        bitSet.set(1, statusPackage.isLowBattery());
        bitSet.set(2, statusPackage.isBatteryAbsentOrInverted());
        bitSet.set(3, statusPackage.isAuxiliaryOutputOverload());

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseKeyboardAndReceiverProblems(StatusPackage statusPackage) {
        List<Boolean> keyboards = statusPackage.getKeyboards().stream().map(Keyboard::isProblem).collect(Collectors.toList());
        List<Boolean> receivers = statusPackage.getReceivers().stream().map(Receiver::isProblem).collect(Collectors.toList());
        BitSet bitSet = bitSetFromBoolLists(keyboards, receivers);

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseExpanderProblemsByte1(StatusPackage statusPackage) {
        List<Boolean> pgmExpanders = statusPackage.getPgmExpanders().stream().map(Expander::isProblem).collect(Collectors.toList());
        List<Boolean> zoneExpanders = statusPackage.getZoneExpanders().stream().map(Expander::isProblem).collect(Collectors.toList());
        BitSet bitSet = bitSetFromBoolLists(pgmExpanders, zoneExpanders);

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseExpanderProblemsByte2(StatusPackage statusPackage) {
        List<Expander> zoneExpanders = statusPackage.getZoneExpanders();
        BitSet bitSet = new BitSet();

        bitSet.set(0, zoneExpanders.get(4).isProblem());
        bitSet.set(1, zoneExpanders.get(5).isProblem());

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseBatteryLevelByte(Battery battery) {
        BitSet bitSet = new BitSet();
        bitSet.set(0, battery.isBatteryOutlineOn());
        bitSet.set(1, battery.isLevelOneCellOn());
        bitSet.set(2, battery.isLevelTwoCellOn());
        bitSet.set(3, battery.isLevelThreeCellOn());
        bitSet.set(4, battery.isBatteryOutlineBlink());
        bitSet.set(5, battery.isLevelOneCellBlink());
        bitSet.set(6, battery.isLevelTwoCellBlink());
        bitSet.set(7, battery.isLevelThreeCellBlink());

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseKeyboardTamper(List<Keyboard> keyboards) {
        BitSet bitSet = new BitSet();

        for (int i = 0; i < keyboards.size(); i++) {
            bitSet.set(i + 4, keyboards.get(i).isTamper());
        }

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseSystemProblems(StatusPackage statusPackage) {
        BitSet bitSet = new BitSet();
        bitSet.set(0, statusPackage.isSirenWireCut());
        bitSet.set(1, statusPackage.isSirenWireShortCircuited());
        bitSet.set(2, statusPackage.isPhoneLineCut());
        bitSet.set(3, statusPackage.isFailureToCommunicateEvent());

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private String parseSirenAndPgmStatus(StatusPackage statusPackage) {
        List<Pgm> pgmList = statusPackage.getPgmList();
        BitSet bitSet = new BitSet();
        bitSet.set(2, statusPackage.isSirenTurnedOn());
        bitSet.set(4, pgmList.get(2).isTurnedOn());
        bitSet.set(5, pgmList.get(1).isTurnedOn());
        bitSet.set(6, pgmList.get(0).isTurnedOn());

        int byteAmount = getByteAmount(bitSet);
        List<Byte> bytes = bitSetToByteList(bitSet, byteAmount);
        return printHexBinary(bytes);
    }

    private int getByteAmount(BitSet bitSet) {
        int length = bitSet.length();
        return length % 8 == 0 && !bitSet.isEmpty() ? length / 8 : 1;
    }

    private BitSet bitSetFromBoolLists(List<Boolean> list1, List<Boolean> list2) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < list1.size(); i++) {
            bitSet.set(i, list1.get(i));
        }

        for (int i = 0; i < list1.size(); i++) {
            bitSet.set(i + 4, list2.get(i));
        }

        return bitSet;
    }

    private BitSet boolListToBitSet(List<Boolean> bits) {
        BitSet bitSet = new BitSet(bits.size());

        for (int i = 0; i < bits.size(); i++) {
            bitSet.set(i, bits.get(i));
        }

        return bitSet;
    }

    private String printHexBinary(List<Byte> data) {
        StringBuilder r = new StringBuilder(data.size() * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    private String formatBytes(String hexString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            result.append(hexString, i, i + 2);
            result.append(" ");
        }

        return result.toString().trim();
    }

    private List<Byte> bitSetToByteList(BitSet bitSet, int byteAmount) {
        byte[] byteArray = bitSet.toByteArray();
        List<Byte> byteList = new ArrayList<>();
        if (byteArray.length < byteAmount) {
            for (int i = 0; i < byteAmount; i++) {
                byteList.add((byte) 0);
            }

            for (int i = 0; i < byteArray.length; i++) {
                byteList.set(i, byteArray[i]);
            }
            return byteList;
        }

        for (byte b : byteArray) {
            byteList.add(b);
        }

        return byteList;
    }
}
