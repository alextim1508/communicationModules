package com.alextim.communicationModules.canModul;


import com.alextim.communicationModules.canModul.message.UcanMessage;
import com.alextim.communicationModules.canModul.ucanStructure.UcanInitStructure;
import com.alextim.communicationModules.canModul.ucanStructure.UcanMsgCountInfoStructure;
import com.alextim.communicationModules.canModul.ucanStructure.UcanStatusStructure;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.BYTE;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.DWORDByReference;
import com.sun.jna.platform.win32.WinDef.WORDByReference;

public interface UcanLibrary extends Library {

    BYTE UcanInitHwConnectControlEx(Callback callback, Pointer arg);
    
    BYTE UcanDeinitHwConnectControl();
    
    BYTE UcanInitHardwareEx(Pointer pUsbCanHandle, BYTE bDeviceNr_p, Callback eventHandler, Pointer arg);

	BYTE UcanDeinitHardware(BYTE usbCanHandle);

    BYTE UcanInitCanEx2(BYTE usbCanHandle, BYTE channel, UcanInitStructure.ByRef init);

	BYTE UcanDeinitCanEx(BYTE usbCanHandle, BYTE channel);

	BYTE UcanGetStatusEx(BYTE usbCanHandle, BYTE channel, UcanStatusStructure.ByRef status);

	BYTE UcanGetMsgCountInfoEx(BYTE usbCanHandle, BYTE channel, UcanMsgCountInfoStructure.ByRef msgCountInfo);

	BYTE UcanGetCanErrorCounter(BYTE usbCanHandle, BYTE channel, DWORDByReference txErrorCounter, DWORDByReference rxErrorCounter);

	BYTE UcanResetCanEx(BYTE usbCanHandle, BYTE channel, DWORD resetFlags);

    BYTE UcanWriteCanMsgEx(BYTE usbCanHandle, BYTE channel, UcanMessage.ByRef canMsg, DWORDByReference count);
	
    BYTE UcanReadCanMsgEx(BYTE usbCanHandle, WORDByReference channel, UcanMessage.ByRef canMsg, DWORDByReference count);

	DWORD UcanGetVersionEx(DWORD verType);

    BYTE UcanGetModuleTime (BYTE usbCanHandle, DWORDByReference time);
}
