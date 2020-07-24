package com.alextim.communicationModules.core.serviceMessage;

import com.alextim.communicationModules.core.CommunicationMessage;

public interface ServiceMessageBuilder {
    CommunicationMessage<Object, Object> getConnectionParameters(Object id);
    CommunicationMessage<Object, Object> openConnection(Object id, Object... param);
    CommunicationMessage<Object, Object> closeConnection(Object id);
    CommunicationMessage<Object, Object> getStatus(Object id);
}
