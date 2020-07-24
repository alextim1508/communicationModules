package com.alextim.communicationModules.canModul;

import com.alextim.communicationModules.core.ControllerCommand;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UcanFeatures {

    @AllArgsConstructor
    public enum UcanControllerCommand implements ControllerCommand {
        GET_CONNECTION_PARAMETERS("Получить параметры подключения", (byte) 0x01),
        OPEN_CONNECTION("Открыть подключение", (byte) 0x02),
        CLOSE_CONNECTION("Закрыть подключение", (byte) 0x03),
        GET_VERSION_DLL("Запрос версии драйвера", (byte) 0x04),
        GET_STATUS("Запрос статуса", (byte) 0x05),
        RESET("Сброс", (byte) 0x06),
        GET_ERROR_COUNT("Количество ошибок драйева", (byte) 0x07),
        GET_MSG_COUNT("Количество принятых/отправленных сообщений", (byte) 0x08);

        @Getter
        private String title;

        @Getter
        private byte code;

        public static Optional<UcanControllerCommand> findByCode(byte code) {
            return Arrays.stream(UcanControllerCommand.values())
                    .filter(parameter -> code == parameter.getCode()).findFirst();
        }
    }

    @Data
    public static class UcanVersion {
        private final int versionMajor;
        private final int versionMinor;
        private final int versionRelease;
    }

    @Data
    public static class UcanErrorCount {
        private final int trErrorCounter;
        private final int recErrorCounter;
    }

    @Data
    public static class UcanStatus {
        private final CanStatus canStatus;
        private final UsbStatus usbStatus;
    }

    @Data
    public static class UcanMsgCount {
        private final int trMsgCount;
        private final int recMsgCount;
    }

    @AllArgsConstructor
    public enum CanStatus {
        USBCAN_CANERR_OK(0x0000),
        USBCAN_CANERR_XMTFULL(0x0001),
        USBCAN_CANERR_OVERRUN(0x0002),
        USBCAN_CANERR_BUSLIGHT(0x0004),
        USBCAN_CANERR_BUSHEAVY(0x0008),
        USBCAN_CANERR_BUSOFF(0x0010),
        USBCAN_CANERR_QRCVEMPTY(0x0020),
        USBCAN_CANERR_QOVERRUN(0x0040),
        USBCAN_CANERR_QXMTFULL(0x0080),
        USBCAN_CANERR_REGTEST(0x0100),
        USBCAN_CANERR_MEMTEST(0x0200),
        USBCAN_CANERR_TXMSGLOST(0x0400);

        @Getter
        private int code;

        public static Optional<CanStatus> findByCode(byte code) {
            return Arrays.stream(CanStatus.values())
                    .filter(parameter -> code == parameter.getCode()).findFirst();
        }
    }

    @AllArgsConstructor
    public enum UsbStatus {
        USBCAN_USBERR_OK(0x0000),
        USBCAN_USBERR_STATUS_TIMEOUT(0x2000),
        USBCAN_USBERR_WATCHDOG_TIMEOUT(0x4000);

        @Getter
        private int code;

        public static Optional<UsbStatus> findByCode(byte code) {
            return Arrays.stream(UsbStatus.values())
                    .filter(parameter -> code == parameter.getCode()).findFirst();
        }
    }

    @AllArgsConstructor
    public enum BaudRate {
        USBCAN_BAUD_1MBit(0x0014),
        USBCAN_BAUD_800kBit(0x0016),
        USBCAN_BAUD_500kBit(0x001c),
        USBCAN_BAUD_250kBit(0x011c),
        USBCAN_BAUD_125kBit(0x031c),
        USBCAN_BAUD_100kBit(0x432f),
        USBCAN_BAUD_50kBit(0x472f),
        USBCAN_BAUD_20kBit(0x532f),
        USBCAN_BAUD_10kBit(0x672f);

        @Getter
        private int code;
    }

    @AllArgsConstructor
    public enum FunctionReturnCode {
        USBCAN_SUCCESSFUL(0x00),
        USBCAN_ERR_RESOURCE(0x01),
        USBCAN_ERR_MAXMODULES(0x02),
        USBCAN_ERR_HWINUSE(0x03),
        USBCAN_ERR_ILLVERSION(0x04),
        USBCAN_ERR_ILLHW(0x05),
        USBCAN_ERR_ILLHANDLE(0x06),
        USBCAN_ERR_ILLPARAM(0x07),
        USBCAN_ERR_BUSY(0x08),
        USBCAN_ERR_TIMEOUT(0x09),
        USBCAN_ERR_IOFAILED(0x0a),
        USBCAN_ERR_DLL_TXFULL(0x0b),
        USBCAN_ERR_MAXINSTANCES(0x0c),
        USBCAN_ERR_CANNOTINIT(0x0d),
        USBCAN_ERR_DISCONNECT(0x0e),
        USBCAN_ERR_NOHWCLASS(0x0f),
        USBCAN_ERR_ILLCHANNEL(0x10),
        USBCAN_ERR_RESERVED1(0x11),
        USBCAN_ERR_ILLHWTYPE(0x12),
        USBCAN_ERR_SERVER_TIMEOUT(0x13),
        USBCAN_ERRCMD_NOTEQU(0x40),
        USBCAN_ERRCMD_REGTST(0x41),
        USBCAN_ERRCMD_ILLCMD(0x42),
        USBCAN_ERRCMD_EEPROM(0x43),
        USBCAN_ERRCMD_RESERVED1(0x44),
        USBCAN_ERRCMD_RESERVED2(0x45),
        USBCAN_ERRCMD_RESERVED3(0x46),
        USBCAN_ERRCMD_ILLBDR(0x47),
        USBCAN_ERRCMD_NOTINIT(0x48),
        USBCAN_ERRCMD_ALREADYINIT(0x49),
        USBCAN_ERRCMD_ILLSUBCMD(0x4A),
        USBCAN_ERRCMD_ILLIDX(0x4B),
        USBCAN_ERRCMD_RUNNING(0x4C),
        USBCAN_WARN_NODATA(0x80),
        USBCAN_WARN_SYS_RXOVERRUN(0x81),
        USBCAN_WARN_DLL_RXOVERRUN(0x82),
        USBCAN_WARN_RESERVED1(0x83),
        USBCAN_WARN_RESERVED2(0x84),
        USBCAN_WARN_FW_TXOVERRUN(0x85),
        USBCAN_WARN_FW_RXOVERRUN(0x86),
        USBCAN_WARN_FW_TXMSGLOST(0x87),
        USBCAN_WARN_NULL_PTR(0x90),
        USBCAN_WARN_TXLIMIT(0x91),
        USBCAN_RESERVED(0xc0);

        @Getter
        private int code;

        public static Optional<FunctionReturnCode> findByCode(int code) {
            return Stream.of(FunctionReturnCode.values())
                    .filter(functionReturnCode -> functionReturnCode.getCode() == code).findFirst();
        }
    }

    @AllArgsConstructor
    public enum Channel {
        USBCAN_CHANNEL_CH0(0),
        USBCAN_CHANNEL_CH1(1),
        USBCAN_CHANNEL_ANY(255),
        USBCAN_CHANNEL_ALL(254),
        USBCAN_CHANNEL_CAN1(0),
        USBCAN_CHANNEL_CAN2(1),
        USBCAN_CHANNEL_LIN(1);

        @Getter
        private int code;
    }

    @AllArgsConstructor
    public enum Reset {
        USBCAN_RESET_ALL(0x00000000),
        USBCAN_RESET_NO_STATUS(0x00000001),
        USBCAN_RESET_NO_CANCTRL(0x00000002),
        USBCAN_RESET_NO_TXCOUNTER(0x00000004),
        USBCAN_RESET_NO_RXCOUNTER(0x00000008),
        USBCAN_RESET_NO_TXBUFFER_CH(0x00000010),
        USBCAN_RESET_NO_TXBUFFER_DLL(0x00000020),
        USBCAN_RESET_NO_TXBUFFER_FW(0x00000080),
        USBCAN_RESET_NO_RXBUFFER_CH(0x00000100),
        USBCAN_RESET_NO_RXBUFFER_DLL(0x00000200),
        USBCAN_RESET_NO_RXBUFFER_SYS(0x00000400),
        USBCAN_RESET_NO_RXBUFFER_FW(0x00000800),
        USBCAN_RESET_FIRMWARE(0xFFFFFFFF);

        @Getter
        private int code;
    }

    @AllArgsConstructor
    public enum VersionType {
        K_VER_TYPE_USER_LIB(0x01),
        K_VER_TYPE_USER_DLL(0x01),
        K_VER_TYPE_SYS_DRV(0x02),
        K_VER_TYPE_FIRMWARE(0x03),
        K_VER_TYPE_NET_DRV(0x04),
        K_VER_TYPE_SYS_LD(0x05),
        K_VER_TYPE_SYS_L2(0x06),
        K_VER_TYPE_SYS_L3(0x07),
        K_VER_TYPE_SYS_L4(0x08),
        K_VER_TYPE_SYS_L5(0x09),
        K_VER_TYPE_CPL(0x0A),
        K_VER_TYPE_SYS_L21(0x0B),
        K_VER_TYPE_SYS_L22(0x0B);

        @Getter
        private int code;
    }

    @AllArgsConstructor
    public enum Event {
        USBCAN_EVENT_INITHW(0),
        USBCAN_EVENT_INITCAN(1),
        USBCAN_EVENT_RECEIVE(2),
        USBCAN_EVENT_STATUS(3),
        USBCAN_EVENT_DEINITCAN(4),
        USBCAN_EVENT_DEINITHW(5),
        USBCAN_EVENT_CONNECT(6),
        USBCAN_EVENT_DISCONNECT(7),
        USBCAN_EVENT_FATALDISCON(8);

        @Getter
        private int code;
    }

    @AllArgsConstructor
    public enum HandleState {
        USBCAN_INVALID_HANDLE(0xff);

        @Getter
        private int code;
    }
}
