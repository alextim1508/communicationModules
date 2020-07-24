package com.alextim.communicationModules.canModul.message;

import com.alextim.communicationModules.core.CommunicationMessage;
import com.sun.jna.Memory;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UcanMessage extends Structure implements CommunicationMessage<Integer, Byte> {

    private static int sizeStructure = 18;

    public WinDef.DWORD m_dwID;
    public WinDef.BYTE m_bFF;
    public WinDef.BYTE m_bDLC;
    public WinDef.BYTE m_bData0;
    public WinDef.BYTE m_bData1;
    public WinDef.BYTE m_bData2;
    public WinDef.BYTE m_bData3;
    public WinDef.BYTE m_bData4;
    public WinDef.BYTE m_bData5;
    public WinDef.BYTE m_bData6;
    public WinDef.BYTE m_bData7;
    public WinDef.DWORD m_dwTime;

    public UcanMessage(int size) {
        super(new Memory(sizeStructure*size));
        setAlignType(ALIGN_NONE);
    }

    public UcanMessage() {
        setAlignType(ALIGN_NONE);
    }

    public UcanMessage(int id, List<Byte> data) {
        super();
        setAlignType(ALIGN_NONE);

        if(data.size() != 8 ) {
            throw new IllegalArgumentException("Length data != 8");
        }

        m_dwID.setValue(id);
        m_bDLC.setValue(data.size());
        m_bData0.setValue(data.get(0));
        m_bData1.setValue(data.get(1));
        m_bData2.setValue(data.get(2));
        m_bData3.setValue(data.get(3));
        m_bData4.setValue(data.get(4));
        m_bData5.setValue(data.get(5));
        m_bData6.setValue(data.get(6));
        m_bData7.setValue(data.get(7));
    }

    public void copyBy(UcanMessage instance) {
        this.m_dwID = instance.m_dwID ;
        this.m_bDLC = instance.m_bDLC;
        this.m_bData0 = instance.m_bData0;
        this.m_bData1 =instance.m_bData1;
        this.m_bData2 =instance.m_bData2;
        this.m_bData3 =instance.m_bData3;
        this.m_bData4 =instance.m_bData4;
        this.m_bData5 =instance.m_bData5;
        this.m_bData6 = instance.m_bData6;
        this.m_bData7 =instance.m_bData7;
        this.m_dwTime =instance.m_dwTime;
    }

    @Override
    public Integer getId() {
        return m_dwID.intValue();
    }

    @Override
    public List<Byte> getData() {
        return Arrays.asList(
                m_bData0.byteValue(),
                m_bData1.byteValue(),
                m_bData2.byteValue(),
                m_bData3.byteValue(),
                m_bData4.byteValue(),
                m_bData5.byteValue(),
                m_bData6.byteValue(),
                m_bData7.byteValue());
    }

    @Override
    public void setDate(Date date) {
        m_dwTime = new WinDef.DWORD((int)date.getTime());
    }

    @Override
    public Date getDate() {
        return m_dwTime.longValue() == 0 ? null : new Date(m_dwTime.longValue());
    }

    @Override
    public String toString() {
        return "UcanMessage{" +
                String.format("id: 0x%x, ", m_dwID.intValue()) +
                String.format("data: 0x%x 0x%x 0x%x 0x%x 0x%x 0x%x 0x%x 0x%x, ",
                        m_bData0.byteValue(),  m_bData1.byteValue(),  m_bData2.byteValue(),  m_bData3.byteValue(),
                        m_bData4.byteValue(),  m_bData5.byteValue(),  m_bData6.byteValue(),  m_bData7.byteValue()) +
                String.format("time: %s}", getDate());
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "m_dwID",
                "m_bFF",
                "m_bDLC",
                "m_bData0",
                "m_bData1",
                "m_bData2",
                "m_bData3",
                "m_bData4",
                "m_bData5",
                "m_bData6",
                "m_bData7",
                "m_dwTime");
    }

    public static class ByRef extends UcanMessage implements ByReference {
        public ByRef(int size) {
            super(sizeStructure * size);
        }

        public ByRef() {
            super();
        }

        public ByRef(int id, List<Byte> data) {
            super(id, data);
        }
    }
}