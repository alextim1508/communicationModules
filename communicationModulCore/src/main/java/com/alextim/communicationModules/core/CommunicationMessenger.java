package com.alextim.communicationModules.core;

public interface CommunicationMessenger {
    void onReceive();

    void onInit();
    void onDeInit();

    void onConnect();
    void onDisconnect();

    void onStatus();
}
