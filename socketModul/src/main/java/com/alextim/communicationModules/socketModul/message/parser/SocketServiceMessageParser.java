package com.alextim.communicationModules.socketModul.message.parser;

import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageParser;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageType;
import com.alextim.communicationModules.socketModul.SocketFeatures;
import lombok.Data;

import java.util.List;

import static com.alextim.communicationModules.core.serviceMessage.ServiceMessageType.SERVICE_MANAGEMENT;
import static com.alextim.communicationModules.core.serviceMessage.ServiceMessageType.SERVICE_REPLY;
import static com.alextim.communicationModules.socketModul.SocketFeatures.*;
import static com.alextim.communicationModules.socketModul.SocketFeatures.SocketControllerCommand.*;

@Data
public class SocketServiceMessageParser implements ServiceMessageParser {

    private ServiceMessageType serviceMessageType;

    private SocketControllerCommand command;
    private String message;

    private SocketFeatures.SocketStatus socketStatus;
    private List<Class<?>> connectionParameters;

    @Override
    public void parsing(CommunicationMessage<Object, Object> msg) {
        serviceMessageType = (ServiceMessageType) msg.getId();

        List<Object> data = msg.getData();
        command = getCommandFromDataMsg(data);

        if(command.equals(GET_CONNECTION_PARAMETERS)) {
            if(serviceMessageType.equals(SERVICE_MANAGEMENT)) {
                message = GET_CONNECTION_PARAMETERS.getTitle();
            } else if(serviceMessageType.equals(SERVICE_REPLY)) {
                List<?> subList = data.subList(1, data.size());
                connectionParameters = (List<Class<?>>) subList;
                message = "Параметры подключения : " + connectionParameters;
            }

        } else if(command.equals(OPEN_CONNECTION)) {
            if(serviceMessageType.equals(SERVICE_MANAGEMENT)) {
                message = OPEN_CONNECTION.getTitle() + " с параметрами " + data.subList(1, data.size());
            } else if(serviceMessageType.equals(SERVICE_REPLY)) {
                message = "Подключение открыто";
            }

        } else if(command.equals(CLOSE_CONNECTION)) {
            if(serviceMessageType.equals(SERVICE_MANAGEMENT)) {
                message = CLOSE_CONNECTION.getTitle();
            } else if(serviceMessageType.equals(SERVICE_REPLY)) {
                message = "Подключение закрыто";
            }

        } else if(command.equals(GET_STATUS)) {
            if(serviceMessageType.equals(SERVICE_MANAGEMENT)) {
                message = GET_STATUS.getTitle();
            } else if(serviceMessageType.equals(SERVICE_REPLY)) {
                socketStatus = (SocketFeatures.SocketStatus) data.get(1);
                message = "Статус: " + socketStatus;
            }
        }
    }

    private SocketControllerCommand getCommandFromDataMsg(List<Object> data) {
        return (SocketControllerCommand) data.get(0);
    }
}
