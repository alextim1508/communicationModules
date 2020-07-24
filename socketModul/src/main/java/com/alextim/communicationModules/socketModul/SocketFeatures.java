package com.alextim.communicationModules.socketModul;

import com.alextim.communicationModules.core.ControllerCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketFeatures {

    @AllArgsConstructor
    public enum SocketControllerCommand implements ControllerCommand {
        GET_CONNECTION_PARAMETERS("Получить параметры подключения", (byte) 0x01),
        OPEN_CONNECTION("Открыть подключение", (byte) 0x02),
        CLOSE_CONNECTION("Закрыть подключение", (byte) 0x03),
        GET_STATUS("Запрос статуса", (byte) 0x04);

        @Getter
        private String title;

        @Getter
        private byte code;

        public static Optional<SocketControllerCommand> findByCode(byte code) {
            return Arrays.stream(SocketControllerCommand.values())
                    .filter(parameter -> code == parameter.getCode()).findFirst();
        }
    }

    @AllArgsConstructor
    public enum SocketStatus {
        SOCKET_CONNECTED("Подключен", (byte) 0x01),
        SOCKET_NOT_CONNECTED("Не подключен", (byte) 0x02);

        @Getter
        private String title;

        @Getter
        private byte code;

    }
}
