package com.alextim.communicationModules.canModul.listener;


import com.alextim.communicationModules.core.CommunicationMessenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor @Slf4j
public class UcanConnectListener extends UcanConnectCallback {

    private final CommunicationMessenger handler;

    @Override
    void onEventConnect() {
        log.debug("onEventConnect ");
        handler.onConnect();
    }

    @Override
    void onEventDisconnect() {
        log.debug("onEventDisconnect");
        handler.onDisconnect();
    }

    @Override
    void onEventFatalDisconnect() {
        log.error("onEventFatalDisconnect");
        handler.onDisconnect();
    }
}