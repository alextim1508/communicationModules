package com.alextim.communicationModules.socketModul.message.builder;

import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageBuilder;
import com.alextim.communicationModules.socketModul.message.SocketServiceMessage;

import java.util.Arrays;
import java.util.Collections;

import static com.alextim.communicationModules.socketModul.SocketFeatures.SocketControllerCommand.*;

public class SocketServiceMessageBuilder implements ServiceMessageBuilder {

    public CommunicationMessage<Object, Object> getConnectionParameters(Object managementId) {
        return new SocketServiceMessage(managementId, Collections.singletonList(GET_CONNECTION_PARAMETERS));
    }

    public CommunicationMessage<Object, Object> openConnection(Object managementId, Object[] param) {
        return new SocketServiceMessage(managementId, Arrays.asList(OPEN_CONNECTION, param[0], param[1]));
    }

    public CommunicationMessage<Object, Object> closeConnection(Object managementId) {
        return new SocketServiceMessage(managementId, Collections.singletonList(CLOSE_CONNECTION));
    }

    public CommunicationMessage<Object, Object> getStatus(Object managementId) {
        return new SocketServiceMessage(managementId, Collections.singletonList(GET_STATUS));
    }
}
