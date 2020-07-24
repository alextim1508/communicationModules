package com.alextim.communicationModules.canModul.message.parser;

import com.alextim.communicationModules.canModul.UcanFeatures.*;
import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageParser;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageType;
import lombok.Data;

import java.util.List;

import static com.alextim.communicationModules.canModul.UcanFeatures.UcanControllerCommand.*;

@Data
public class UcanServiceMessageParser implements ServiceMessageParser {

    private ServiceMessageType serviceMessageType;
    private UcanControllerCommand command;
    private String message;

    private UcanMsgCount ucanMsgCount;
    private UcanErrorCount ucanErrorCount;
    private UcanStatus ucanStatus;
    private UcanVersion ucanVersion;
    private List<Class<?>> connectionParameters;

    public void parsing(CommunicationMessage<Object, Object> msg) {
        serviceMessageType = (ServiceMessageType) msg.getId();

        List<Object> data = msg.getData();
        command = getCommandFromDataMsg(data);

        if(command.equals(GET_CONNECTION_PARAMETERS)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = GET_CONNECTION_PARAMETERS.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                List<?> subList = data.subList(1, data.size());
                connectionParameters = (List<Class<?>>) subList;
                message = "Параметры подключения : " + connectionParameters;
            }
        }
        else if(command.equals(OPEN_CONNECTION)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = OPEN_CONNECTION.getTitle() + " с параметрами " + data.subList(1, data.size());
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                message = "Подключение открыто";
            }
        }
        else if(command.equals(CLOSE_CONNECTION)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = CLOSE_CONNECTION.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                message = "Подключение закрыто";
            }
        }
        else if(command.equals(GET_STATUS)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = GET_STATUS.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                ucanStatus = (UcanStatus) data.get(1);
                message = "Статус: " + ucanStatus;
            }
        }
        else if(command.equals(GET_VERSION_DLL)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = GET_VERSION_DLL.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                ucanVersion = (UcanVersion) data.get(1);
                message = "Версия dll: " + ucanVersion;
            }
        }
        else if(command.equals(RESET)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = RESET.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                message = "Сброшено";
            }
        }
        else if(command.equals(GET_MSG_COUNT)) {
            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = GET_MSG_COUNT.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                ucanMsgCount = (UcanMsgCount)data.get(1);
                message = "Количество сообщений: " + ucanMsgCount;
            }
        }
        else if(command.equals(GET_ERROR_COUNT)) {

            if(serviceMessageType.equals(ServiceMessageType.SERVICE_MANAGEMENT)) {
                message = GET_ERROR_COUNT.getTitle();
            } else if(serviceMessageType.equals(ServiceMessageType.SERVICE_REPLY)) {
                ucanErrorCount = (UcanErrorCount)data.get(1);
                message = "Количество ошибок: " + ucanErrorCount;
            }
        }
    }

    private UcanControllerCommand getCommandFromDataMsg(List<Object> data) {
        return (UcanControllerCommand) data.get(0);
    }
}
