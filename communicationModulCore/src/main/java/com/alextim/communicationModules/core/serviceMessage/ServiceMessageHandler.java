package com.alextim.communicationModules.core.serviceMessage;

import com.alextim.communicationModules.core.CommunicationController;
import com.alextim.communicationModules.core.CommunicationMessage;

public interface ServiceMessageHandler {
     CommunicationMessage<Object, Object> handle(CommunicationController controller, CommunicationMessage<Object, Object> message);
}