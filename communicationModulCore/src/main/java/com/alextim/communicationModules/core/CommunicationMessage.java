package com.alextim.communicationModules.core;

import java.util.Date;
import java.util.List;

public interface CommunicationMessage<IdType, DataType> {
    IdType getId();
    List<DataType> getData();
    Date getDate();
    void setDate(Date date);
}
