package com.alextim.communicationModules.canModul.listener;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.BYTE;
import com.sun.jna.platform.win32.WinDef.DWORD;

import static com.alextim.communicationModules.canModul.UcanFeatures.Event.*;

public abstract class UcanEventCallback implements Callback {

    abstract void onEventReceive();
    abstract void onEventInitHw();
    abstract void onEventInitCan();
    abstract void onEventStatus();
    abstract void onEventDeInitCan();
    abstract void onEventDeInitHw();

    public void callback(BYTE ucanHandle, DWORD event, BYTE channel, Pointer ptr) {

        if(event.intValue() == USBCAN_EVENT_RECEIVE.getCode()) {
            onEventReceive();
        }
        else if (event.intValue() == USBCAN_EVENT_INITHW.getCode()) {
            onEventInitHw();
        }
        else if(event.intValue() == USBCAN_EVENT_INITCAN.getCode()) {
            onEventInitCan();
        }
        else if(event.intValue() == USBCAN_EVENT_STATUS.getCode()) {
            onEventStatus();
        }
        else if(event.intValue() == USBCAN_EVENT_DEINITCAN.getCode()) {
            onEventDeInitCan();
        }
        else if(event.intValue() == USBCAN_EVENT_DEINITHW.getCode()) {
            onEventDeInitHw();
        }
    }
}