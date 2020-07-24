package com.alextim.communicationModules.canModul.listener;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.DWORD;

import static com.alextim.communicationModules.canModul.UcanFeatures.Event.USBCAN_EVENT_CONNECT;
import static com.alextim.communicationModules.canModul.UcanFeatures.Event.USBCAN_EVENT_DISCONNECT;
import static com.alextim.communicationModules.canModul.UcanFeatures.Event.USBCAN_EVENT_FATALDISCON;


public abstract class UcanConnectCallback implements Callback {

    abstract void onEventConnect();
    abstract void onEventDisconnect();
    abstract void onEventFatalDisconnect();

    public void callback(DWORD event, DWORD param, Pointer arg) {
        if(event.intValue() == USBCAN_EVENT_CONNECT.getCode()) {
            onEventConnect();
        }
        else if(event.intValue() == USBCAN_EVENT_DISCONNECT.getCode()) {
            onEventDisconnect();
        }
        else if(event.intValue() == USBCAN_EVENT_FATALDISCON.getCode()) {
            onEventFatalDisconnect();
        }
    }
}