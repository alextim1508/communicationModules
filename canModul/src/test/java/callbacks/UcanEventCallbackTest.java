package callbacks;

import com.alextim.communicationModules.canModul.listener.UcanEventListener;
import com.alextim.communicationModules.core.CommunicationMessenger;
import com.sun.jna.platform.win32.WinDef;
import org.junit.Before;
import org.junit.Test;

import static com.alextim.communicationModules.canModul.UcanFeatures.Event.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class UcanEventCallbackTest {

    private CommunicationMessenger communicationMessenger;

    private UcanEventListener eventsUcan;


    @Before
    public void setUp() throws Exception {
        communicationMessenger = mock(CommunicationMessenger.class);
//        when(communicationMessenger.receiveMsgs()).thenAnswer(answer->{
//            return new
//        });
        doNothing().when(communicationMessenger).onInit();
        doNothing().when(communicationMessenger).onStatus();
        doNothing().when(communicationMessenger).onDeInit();
        eventsUcan = new UcanEventListener(communicationMessenger);
    }

    @Test
    public void getCallbackByEventUSBCAN_EVENT_RECEIVE(){
//        eventsUcan.callback(null, new WinDef.DWORD(USBCAN_EVENT_RECEIVE.getCode()), null, null);
//        verify(communicationMessenger, times(1)).receiveMsgs();
    }

    @Test
    public void getCallbackByEvenUSBCAN_EVENT_INITHW(){
        eventsUcan.callback(null, new WinDef.DWORD(USBCAN_EVENT_INITHW.getCode()), null, null);
    }

    @Test
    public void getCallbackByEventUSBCAN_EVENT_INITCAN(){
        eventsUcan.callback(null, new WinDef.DWORD(USBCAN_EVENT_INITCAN.getCode()), null, null);
    }

    @Test
    public void getCallbackByEventUSBCAN_EVENT_STATUS(){
        eventsUcan.callback(null, new WinDef.DWORD(USBCAN_EVENT_STATUS.getCode()), null, null);
    }

    @Test
    public void getCallbackByEventUSBCAN_EVENT_DEINITCAN(){
        eventsUcan.callback(null, new WinDef.DWORD(USBCAN_EVENT_DEINITCAN.getCode()), null, null);
    }

    @Test
    public void getCallbackByEventUSBCAN_EVENT_DEINITHW(){
        eventsUcan.callback(null, new WinDef.DWORD(USBCAN_EVENT_DEINITHW.getCode()), null, null);
    }


}
