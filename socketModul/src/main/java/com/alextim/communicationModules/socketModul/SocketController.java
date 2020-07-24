package com.alextim.communicationModules.socketModul;

import com.alextim.communicationModules.core.CommunicationController;
import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.CommunicationMessenger;
import com.alextim.communicationModules.socketModul.message.SocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor @Slf4j
public class SocketController implements CommunicationController {

    private final CommunicationMessenger handler;

    public Socket socket;
    private Thread listener;

    @Override
    public void open(List<Object> param) {
        if(param.size() != 2 || !(param.get(0) instanceof String) || !(param.get(1) instanceof Integer))
            throw new RuntimeException("Two parameters String and Integer expected");

        try {
            socket = new Socket((String)param.get(0), (Integer) param.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener = new Thread(()->{
            while(!Thread.interrupted()) {
                try {
                    socket.getInputStream().read(new byte[0]);
                    handler.onReceive();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log.debug("onEventReceive");
                handler.onReceive();
            }
            log.debug("listener close");
        });

        listener.start();
        log.debug("Open OK");
    }

    @Override
    public void close() {
        if(socket != null && !socket.isClosed()) {
            try {
                listener.interrupt();
                listener.join();
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.debug("Close OK");
    }

    @Override
    public void initialize() {
    }

    @Override
    public void deinitialize() {
    }

    @Override
    public void writeMsgs(List<? extends CommunicationMessage<Integer, Byte>> msgs) {
        msgs.forEach(msg -> {
                msg.setDate(new Date());
                byte[] idBytes = ByteBuffer.allocate(Integer.BYTES).putInt(msg.getId()).array();

                List<Byte> data = msg.getData();

                try {
                    socket.getOutputStream().write(new byte[] {
                            (byte)2,
                            idBytes[3],
                            idBytes[2],
                            (byte)8,
                            data.get(0),
                            data.get(1),
                            data.get(2),
                            data.get(3),
                            data.get(4),
                            data.get(5),
                            data.get(6),
                            data.get(7)});
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
        });
    }

    @Override
    public void readMsgs(List<? super CommunicationMessage<Integer, Byte>> msgs) {
        byte[] buf = new byte[12];
        try {
            List<Byte> data = new ArrayList<>();
            int size = socket.getInputStream().read(buf, 0, buf.length);

            if(buf[0] == 0) {
                throw new RuntimeException("error " + ByteBuffer.wrap(new byte[] {
                        buf[1],
                        buf[2],
                        buf[3],
                        buf[4]}).getInt());
            } else if(buf[0] == 1) {
                socket.getOutputStream().write(buf);
            } else {
                for (int i = 0; i < size; i++) {
                    data.add(buf[i]);
                }
                SocketMessage socketMessage = new SocketMessage(data);
                socketMessage.setDate(new Date());
                msgs.add(socketMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getStatus() {
        if(socket.isConnected())
            return SocketFeatures.SocketStatus.SOCKET_CONNECTED;
        return SocketFeatures.SocketStatus.SOCKET_NOT_CONNECTED;
    }
}
