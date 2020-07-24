package com.alextim.communicationModules.canModul;

import com.alextim.communicationModules.canModul.listener.UcanEventCallback;
import com.alextim.communicationModules.canModul.listener.UcanConnectCallback;

import com.alextim.communicationModules.canModul.message.UcanMessage;
import com.alextim.communicationModules.canModul.ucanStructure.UcanInitStructure;
import com.alextim.communicationModules.canModul.ucanStructure.UcanMsgCountInfoStructure;
import com.alextim.communicationModules.canModul.ucanStructure.UcanStatusStructure;
import com.alextim.communicationModules.core.CommunicationController;
import com.alextim.communicationModules.core.CommunicationMessage;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.ptr.IntByReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

import static com.alextim.communicationModules.canModul.UcanFeatures.Channel.USBCAN_CHANNEL_CH0;
import static com.alextim.communicationModules.canModul.UcanFeatures.FunctionReturnCode.USBCAN_SUCCESSFUL;
import static com.alextim.communicationModules.canModul.UcanFeatures.FunctionReturnCode.USBCAN_WARN_NODATA;
import static com.alextim.communicationModules.canModul.UcanFeatures.HandleState.USBCAN_INVALID_HANDLE;
import static com.alextim.communicationModules.canModul.UcanFeatures.Reset.USBCAN_RESET_ALL;
import static com.alextim.communicationModules.canModul.UcanFeatures.VersionType.K_VER_TYPE_USER_DLL;

@RequiredArgsConstructor @Slf4j
public class UсanController implements CommunicationController {

    private final UcanLibrary usbCanLibrary;
    private final UcanEventCallback eventsUsbCan;
    private final UcanConnectCallback connectUsbCan;

	private final IntByReference usbCanHandle = new IntByReference();
	private final BYTE usbCanChannel = new BYTE(USBCAN_CHANNEL_CH0.getCode());

    final int READ_MESGS_MAX = 256;

	@Override
	public void initialize() {
        usbCanHandle.setValue(USBCAN_INVALID_HANDLE.getCode());
        initConnectControl();
        log.info("initialize ok");
	}

    private void initConnectControl() {
        BYTE res = usbCanLibrary.UcanInitHwConnectControlEx(connectUsbCan, null);
        if(res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanInitHwConnectControlEx", res.intValue());
        }
        log.debug("UcanInitHwConnectControlEx OK");
    }

    @Override
	public void open(List<Object> param) {
	    if(param.size() != 1 || !(param.get(0) instanceof UcanFeatures.BaudRate))
            throw new RuntimeException("One parameter Features.BaudRate expected");

        initHardware();
        initCan((UcanFeatures.BaudRate)param.get(0));
        log.info("open ok");
    }

	private void initHardware() {
		BYTE res = usbCanLibrary.UcanInitHardwareEx(usbCanHandle.getPointer(),
													usbCanChannel,
													eventsUsbCan,
													null);

		if(res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanInitHardwareEx", res.intValue());
		}
        log.debug("UcanInitHardwareEx OK");
	}

	private void initCan(UcanFeatures.BaudRate baudRate) {
        byte[] bytesValue = ByteBuffer.allocate(Integer.BYTES).putInt(baudRate.getCode()).array();
        UcanInitStructure.ByRef param = new UcanInitStructure.ByRef();
		param.setM_dwSize(new DWORD(0x18));
		param.setM_bMode(new BYTE(0x00));
		param.setM_bBTR0(new BYTE(bytesValue[bytesValue.length - 2]));
		param.setM_bBTR1(new BYTE(bytesValue[bytesValue.length - 1]));
		param.setM_bOCR(new BYTE(0x1a));
		param.setM_dwAMR(new DWORD(0xffffffff));
		param.setM_dwACR(new DWORD(0x00));
		param.setM_dwBaudrate(new DWORD(0x00));
		param.setM_wNrOfRxBufferEntries(new WORD(4096));
		param.setM_wNrOfTxBufferEntries( new WORD(4096));

		BYTE res =  usbCanLibrary.UcanInitCanEx2(	new BYTE(usbCanHandle.getValue()),
													usbCanChannel,
													param);

		if(res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanInitCanEx2", res.intValue());
		}
        log.debug("UcanInitCanEx2 OK");
	}

	public void close() {
		deInitCan();
		deInitHardware();
        log.info("close ok");
	}

	private void deInitCan() {
		BYTE res = usbCanLibrary.UcanDeinitCanEx( 	new BYTE(usbCanHandle.getValue()),
													usbCanChannel);

		if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanDeinitCanEx", res.intValue());
		}
        log.info("deInitCan ok");
	}

	private void deInitHardware() {
		BYTE res = usbCanLibrary.UcanDeinitHardware(new BYTE(usbCanHandle.getValue()));

		if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanDeinitHardware", res.intValue());
		}
        log.debug("UcanDeinitHardware ok");
	}

	@Override
	public void deinitialize() {
        usbCanHandle.setValue(USBCAN_INVALID_HANDLE.getCode());
        deinitConnectControl();
    }

	private void deinitConnectControl() {
		BYTE res =  usbCanLibrary.UcanDeinitHwConnectControl();
		if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanDeinitHwConnectControl", res.intValue());
		}
        log.debug("UcanDeinitHwConnectControl ok");
	}

    @Override
	public void writeMsgs(List<? extends CommunicationMessage<Integer, Byte>> msgs) {
        UcanMessage.ByRef canMsgs = new UcanMessage.ByRef(msgs.size());
        UcanMessage[] arrayCanMsgs = (UcanMessage[])canMsgs.toArray(msgs.size());
        for(int i=0; i< msgs.size(); i++)
            arrayCanMsgs[i].copyBy((UcanMessage)msgs.get(i));
        long moduleTime = getModuleTime();
        msgs.forEach(msg -> msg.setDate(new Date(moduleTime)));

        BYTE res =  usbCanLibrary.UcanWriteCanMsgEx (	new BYTE(usbCanHandle.getValue()),
                                                        new BYTE(USBCAN_CHANNEL_CH0.getCode()),
                                                        canMsgs,
                                                        new DWORDByReference(new DWORD(msgs.size())));

        if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanWriteCanMsgEx", res.intValue());
        }
        log.debug("UcanWriteCanMsgEx OK. Size {} ", msgs.size());
	}

	@Override
	public void readMsgs(List<? super CommunicationMessage<Integer, Byte>> msgs) {
		if(msgs.size() > READ_MESGS_MAX)
		    throw new RuntimeException("Size must be < " + READ_MESGS_MAX);

		DWORDByReference countMsg = new DWORDByReference(new DWORD(READ_MESGS_MAX));
		UcanMessage.ByRef canMsgs = new UcanMessage.ByRef(READ_MESGS_MAX);

		BYTE res =  usbCanLibrary.UcanReadCanMsgEx( new BYTE(usbCanHandle.getValue()),
                                                    new WORDByReference(new WORD(usbCanChannel.intValue())),
													canMsgs,
													countMsg);

		if(res.intValue() != USBCAN_SUCCESSFUL.getCode() && res.intValue() != USBCAN_WARN_NODATA.getCode()) {
            errorHandler("UcanReadCanMsgEx", res.intValue());
		}

        int size = countMsg.getValue().intValue();
        UcanMessage.ByRef[] canMsgsArray = (UcanMessage.ByRef[])canMsgs.toArray(READ_MESGS_MAX);
        for (int i = 0; i < size; i++) {
            if(canMsgsArray[i].m_dwID.intValue() != 0x7ff) //todo!
                msgs.add(canMsgsArray[i]);
        }

        log.debug("UcanReadCanMsgEx OK. List read msgs size {}", size);
	}

	public UcanFeatures.UcanMsgCount getMsgCount() {
		UcanMsgCountInfoStructure.ByRef msgCount = new UcanMsgCountInfoStructure.ByRef();
		BYTE res = usbCanLibrary.UcanGetMsgCountInfoEx(	new BYTE(usbCanHandle.getValue()),
														usbCanChannel,
														msgCount);
		
        if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanGetMsgCountInfoEx", res.intValue());
        }
        int trMsgCount = msgCount.getM_wSentMsgCount().intValue();
        int recMsgCount	= msgCount.getM_wRecvdMsgCount().intValue();
        log.debug("UcanGetMsgCountInfoEx OK");
        return new UcanFeatures.UcanMsgCount(trMsgCount, recMsgCount);
	}

	public void reset() {
		BYTE res =  usbCanLibrary.UcanResetCanEx(new BYTE(usbCanHandle.getValue()),
												usbCanChannel,
												new DWORD(USBCAN_RESET_ALL.getCode()));
        if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanResetCanEx", res.intValue());
        }
        log.debug("UcanResetCanEx OK");
	 }

	@Override
	public Object getStatus()  {
		UcanStatusStructure.ByRef status = new UcanStatusStructure.ByRef();
		BYTE res =  usbCanLibrary.UcanGetStatusEx(new BYTE(usbCanHandle.getValue()),
												usbCanChannel,
												status);
		if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanGetStatusEx", res.intValue());
		}

        UcanFeatures.CanStatus canStatus = UcanFeatures.CanStatus.findByCode(status.getM_wCanStatus().byteValue()).get();
        UcanFeatures.UsbStatus usbStatus = UcanFeatures.UsbStatus.findByCode(status.getM_wUsbStatus().byteValue()).get();
        log.debug("UcanGetStatusEx OK");
		return new UcanFeatures.UcanStatus(canStatus, usbStatus);
	}

	public UcanFeatures.UcanErrorCount getErrorCount()  {
		DWORDByReference refTrErrorCounter = new DWORDByReference();
		DWORDByReference refRecErrorCounter = new DWORDByReference();

		BYTE res = usbCanLibrary.UcanGetCanErrorCounter(new BYTE(usbCanHandle.getValue()),
														usbCanChannel,
														refTrErrorCounter,
														refRecErrorCounter);
		if (res.intValue() != USBCAN_SUCCESSFUL.getCode()) {
            errorHandler("UcanGetCanErrorCounter", res.intValue());
        }

		int transmittedErrorCounter = refTrErrorCounter.getValue().intValue();
		int	receivedErrorCounter	= refRecErrorCounter.getValue().intValue();
        log.debug("UcanGetCanErrorCounter OK");
        return new UcanFeatures.UcanErrorCount(transmittedErrorCounter, receivedErrorCounter);
	}

	public UcanFeatures.UcanVersion getVersionDll() {
		DWORD tp = new DWORD();
		tp.setValue(K_VER_TYPE_USER_DLL.getCode());

		DWORD version = usbCanLibrary.UcanGetVersionEx(tp);
		int verMajor = version.intValue() & 0x000000FF;
		int verMinor = (version.intValue() & 0x0000FF00) >> 8;
		int verRelease = (version.intValue() & 0xFFFF0000) >> 16;
        log.debug("UcanGetVersionEx OK");
        return new UcanFeatures.UcanVersion(verMajor, verMinor, verRelease);
	}

    public long getModuleTime() {
        DWORDByReference time = new DWORDByReference();
        usbCanLibrary.UcanGetModuleTime(new BYTE(usbCanHandle.getValue()), time);

        log.debug("getModuleTime OK");
        return time.getValue().longValue();
    }

	private void errorHandler(String funName, int code) {
        log.error("{} error: {}", funName, UcanFeatures.FunctionReturnCode.findByCode(code));
        throw new RuntimeException(funName + " error " + UcanFeatures.FunctionReturnCode.findByCode(code).get());
    }
}