package com.alextim.communicationModules.canModul.message.builder;

import com.alextim.communicationModules.canModul.message.UcanServiceMessage;
import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessageBuilder;

import java.util.Arrays;
import java.util.Collections;

import static com.alextim.communicationModules.canModul.UcanFeatures.UcanControllerCommand.*;

public class UcanServiceMessageBuilder implements ServiceMessageBuilder {

    public CommunicationMessage<Object, Object> getConnectionParameters(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(GET_CONNECTION_PARAMETERS));
    }

    public CommunicationMessage<Object, Object> openConnection(Object managementId, Object[] param) {
        return new UcanServiceMessage(managementId, Arrays.asList(OPEN_CONNECTION, param[0]));
    }

    public CommunicationMessage<Object, Object> closeConnection(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(CLOSE_CONNECTION));
    }

    public CommunicationMessage<Object, Object> getStatus(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(GET_STATUS));
    }

    public CommunicationMessage<Object, Object> getMsgCount(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(GET_MSG_COUNT));
    }

    public CommunicationMessage<Object, Object> getErrorCount(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(GET_ERROR_COUNT));
    }

    public CommunicationMessage<Object, Object> getVersionDll(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(GET_VERSION_DLL));
    }

    public CommunicationMessage<Object, Object> reset(Object managementId) {
        return new UcanServiceMessage(managementId, Collections.singletonList(RESET));
    }
}
