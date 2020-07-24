package com.alextim.communicationModules.canModul.message;

import com.alextim.communicationModules.core.CommunicationMessage;
import com.alextim.communicationModules.core.serviceMessage.ServiceMessage;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UcanServiceMessage implements ServiceMessage, CommunicationMessage<Object, Object> {

    private final Object id;
    private final List<Object> data;

    private Date date;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UcanServiceMessage{ data: ");
        data.forEach(o -> builder.append(o).append(" "));
        builder.append(" }");
        return builder.toString();
    }

}
