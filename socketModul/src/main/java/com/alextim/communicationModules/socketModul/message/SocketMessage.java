package com.alextim.communicationModules.socketModul.message;

import com.alextim.communicationModules.core.CommunicationMessage;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

@Data
public class SocketMessage implements CommunicationMessage<Integer, Byte> {

    private final List<Byte> data;
    private final Integer id;

    public SocketMessage(List<Byte> data) {
        this.id = Short.toUnsignedInt(ByteBuffer.wrap(new byte[]{data.get(2), data.get(1)}).getShort());
        this.data = data.subList(4, data.size());
    }

    public SocketMessage(int id, List<Byte> data) {
        this.data = data;
        this.id = id;
    }

    @Override
    public Date getDate() {
        return new Date(); //todo!
    }

    @Override
    public void setDate(Date date) {
    }
}
