package com.alextim.communicationModules.socketModul.message;

import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessage;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SocketServiceMessage implements ServiceMessage, CommunicationMessage<Object, Object> {

    private final Object id;
    private final List<Object> data;

    private Date date;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServiceMessage{ data: ");
        data.forEach(o -> builder.append(o).append(" "));
        builder.append(" }");
        return builder.toString();
    }
}
