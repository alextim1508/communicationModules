package com.alextim.communicationModules.canModul.ucanStructure;

import com.sun.jna.Memory;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class UcanInitStructure extends Structure {

    public WinDef.DWORD m_dwSize;
    public WinDef.BYTE m_bMode;
    public WinDef.BYTE m_bBTR0;
    public WinDef.BYTE m_bBTR1;
    public WinDef.BYTE m_bOCR;
    public WinDef.DWORD m_dwAMR;
    public WinDef.DWORD m_dwACR;
    public WinDef.DWORD m_dwBaudrate;
    public WinDef.WORD m_wNrOfRxBufferEntries;
    public WinDef.WORD m_wNrOfTxBufferEntries;

    public UcanInitStructure(int size) {
        super(new Memory(size));
        setAlignType(ALIGN_NONE);
    }

    public UcanInitStructure() {
        super();
        setAlignType(ALIGN_NONE);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "m_dwSize",
                "m_bMode",
                "m_bBTR0",
                "m_bBTR1",
                "m_bOCR",
                "m_dwAMR",
                "m_dwACR",
                "m_dwBaudrate",
                "m_wNrOfRxBufferEntries",
                "m_wNrOfTxBufferEntries");
    }

    public static class ByRef extends UcanInitStructure implements ByReference {

    }
}
