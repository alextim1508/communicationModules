package com.alextim.communicationModules.canModul.ucanStructure;


import com.sun.jna.Memory;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class UcanStatusStructure extends Structure {

    public WinDef.WORD m_wCanStatus;
    public WinDef.WORD m_wUsbStatus;

    public UcanStatusStructure(int size) {
        super(new Memory(size));
        setAlignType(ALIGN_NONE);
    }

    public UcanStatusStructure() {
        super();
        setAlignType(ALIGN_NONE);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("m_wCanStatus", "m_wUsbStatus");
    }

    public static class ByRef extends UcanStatusStructure implements ByReference {
        public ByRef(int size) {
            super(size);
        }

        public ByRef() {
            super();
        }
    }
}

