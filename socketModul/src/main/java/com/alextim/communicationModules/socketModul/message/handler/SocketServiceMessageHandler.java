package com.alextim.communicationModules.socketModul.message.handler;

import com.alextim.communicationModules.core.CommunicationController;
import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageHandler;
import com.alextim.communicationModules.socketModul.SocketFeatures;
import com.alextim.communicationModules.socketModul.message.SocketServiceMessage;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.alextim.communicationModules.core.serviceMessage.ServiceMessageType.SERVICE_REPLY;
import static com.alextim.communicationModules.socketModul.SocketFeatures.SocketControllerCommand.*;

public class SocketServiceMessageHandler implements ServiceMessageHandler {

    public CommunicationMessage<Object, Object> handle(CommunicationController controller, CommunicationMessage<Object, Object> communicationMessage) {
        communicationMessage.setDate(new Date());

        List<Object> data = communicationMessage.getData();
        Object command = data.get(0);

        if(command.equals(GET_CONNECTION_PARAMETERS)) {
            SocketServiceMessage msg = new SocketServiceMessage(SERVICE_REPLY, Arrays.asList(GET_CONNECTION_PARAMETERS, String.class, Integer.class));
            msg.setDate(new Date());
            return msg;

        } else if(command.equals(OPEN_CONNECTION)) {
            controller.open(data.subList(1, data.size()));
            SocketServiceMessage msg = new SocketServiceMessage(SERVICE_REPLY, Collections.singletonList(OPEN_CONNECTION));
            msg.setDate(new Date());
            return msg;

        } else if(command.equals(CLOSE_CONNECTION)) {
            controller.close();
            SocketServiceMessage msg = new SocketServiceMessage(SERVICE_REPLY, Collections.singletonList(CLOSE_CONNECTION));
            msg.setDate(new Date());
            return msg;

        } else if(command.equals(GET_STATUS)) {
            SocketFeatures.SocketStatus status = (SocketFeatures.SocketStatus) controller.getStatus();
            SocketServiceMessage msg = new SocketServiceMessage(SERVICE_REPLY, Arrays.asList(GET_STATUS, status));
            msg.setDate(new Date());
            return msg;
        }
        throw new RuntimeException("Unknown SocketServiceMessage");
    }


}
