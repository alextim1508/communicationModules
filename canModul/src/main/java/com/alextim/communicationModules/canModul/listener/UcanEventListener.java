package com.alextim.communicationModules.canModul.listener;


import com.alextim.communicationModules.core.CommunicationMessenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor @Slf4j
public class UcanEventListener extends UcanEventCallback {

    private final CommunicationMessenger handler;

    @Override
    void onEventReceive() {
        log.debug("onEventReceive");
        handler.onReceive();
    }

    @Override
    void onEventInitHw() {
        log.debug("onEventInitHw");
    }

    @Override
    void onEventInitCan() {
        log.debug("onEventInitCan");
        handler.onInit();
    }

    @Override
    void onEventStatus() {
        log.debug("onEventStatus");
        handler.onStatus();
    }

    @Override
    void onEventDeInitCan() {
        log.debug("onEventDeInitCan");
        handler.onDeInit();
    }

    @Override
    void onEventDeInitHw() {
        log.debug("onEventDeInitHw");
    }
}