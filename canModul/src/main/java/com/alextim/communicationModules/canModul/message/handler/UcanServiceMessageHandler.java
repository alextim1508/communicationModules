package com.alextim.communicationModules.canModul.message.handler;

import com.alextim.communicationModules.canModul.UcanFeatures;
import com.alextim.communicationModules.canModul.UсanController;
import com.alextim.communicationModules.canModul.message.UcanServiceMessage;
import com.alextim.communicationModules.core.CommunicationController;
import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageHandler;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.alextim.communicationModules.canModul.UcanFeatures.UcanControllerCommand.*;
import static com.alextim.communicationModules.core.serviceMessage.ServiceMessageType.SERVICE_REPLY;

public class UcanServiceMessageHandler implements ServiceMessageHandler {

    public CommunicationMessage<Object, Object> handle(CommunicationController controller, CommunicationMessage<Object, Object> communicationMessage) {
        List<Object> data = communicationMessage.getData();
        Object command = data.get(0);

        if(command.equals(GET_CONNECTION_PARAMETERS)) {
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Arrays.asList(GET_CONNECTION_PARAMETERS, UcanFeatures.BaudRate.class));
            msg.setDate(new Date(((UсanController) controller).getModuleTime()));
            return msg;
        }
        else if(command.equals(OPEN_CONNECTION)) {
            controller.open(data.subList(1, data.size()));
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Collections.singletonList(OPEN_CONNECTION));
            msg.setDate(new Date(TimeUnit.SECONDS.toMillis(((UсanController) controller).getModuleTime()))); //Todo!!
            return msg;
        }
        else if(command.equals(CLOSE_CONNECTION)) {
            controller.close();
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Collections.singletonList(CLOSE_CONNECTION));
            msg.setDate(new Timestamp(((UсanController) controller).getModuleTime()));
            return msg;
        }
        else if(command.equals(GET_STATUS)) {
            UcanFeatures.UcanStatus status = (UcanFeatures.UcanStatus)controller.getStatus();
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Arrays.asList(GET_STATUS, status));
            msg.setDate(new Date(((UсanController) controller).getModuleTime()));
            return msg;
        }
        else if(command.equals(GET_VERSION_DLL)) {
            UcanFeatures.UcanVersion version = ((UсanController)controller).getVersionDll();
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Arrays.asList(GET_VERSION_DLL, version));
            msg.setDate(new Date(((UсanController) controller).getModuleTime()));
            return msg;
        }
        else if(command.equals(RESET)) {
            ((UсanController)controller).reset();
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Collections.singletonList(RESET));
            msg.setDate(new Date(((UсanController) controller).getModuleTime()));
            return msg;
        }
        else if(command.equals(GET_MSG_COUNT)) {
            UcanFeatures.UcanMsgCount msgCount = ((UсanController)controller).getMsgCount();
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Arrays.asList(GET_MSG_COUNT, msgCount));
            msg.setDate(new Date(((UсanController) controller).getModuleTime()));
            return msg;
        }
        else if(command.equals(GET_ERROR_COUNT)) {
            UcanFeatures.UcanErrorCount errorCount = ((UсanController)controller).getErrorCount();
            UcanServiceMessage msg = new UcanServiceMessage(SERVICE_REPLY, Arrays.asList(GET_ERROR_COUNT, errorCount));
            msg.setDate(new Date(((UсanController) controller).getModuleTime()));
            return msg;
        }
        throw new RuntimeException("Unknown UcanServiceMessage");
    }

}
