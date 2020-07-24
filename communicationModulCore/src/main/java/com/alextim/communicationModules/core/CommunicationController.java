package com.alextim.communicationModules.core;

import java.util.List;

public interface CommunicationController {
    void initialize();
    void deinitialize();
    void open(List<Object> param);
    void close();
    void writeMsgs(List<? extends CommunicationMessage<Integer, Byte>> msgs);
    void readMsgs(List<? super CommunicationMessage<Integer, Byte>> msgs);
    Object getStatus();
}
