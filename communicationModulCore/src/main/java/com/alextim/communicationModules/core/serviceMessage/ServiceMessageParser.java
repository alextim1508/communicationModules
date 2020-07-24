package com.alextim.communicationModules.core.serviceMessage;

import com.alextim.communicationModules.core.CommunicationMessage;

public interface ServiceMessageParser {
    void parsing(CommunicationMessage<Object, Object> message);
}
