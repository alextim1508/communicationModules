package com.alextim.communicationModules.canModul.ucanStructure;

import com.sun.jna.Memory;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class UcanMsgCountInfoStructure extends Structure {

    public WinDef.WORD m_wSentMsgCount;
    public WinDef.WORD m_wRecvdMsgCount;

    public UcanMsgCountInfoStructure(int size) {
        super(new Memory(size));
        setAlignType(ALIGN_NONE);
    }

    public UcanMsgCountInfoStructure() {
        super();
        setAlignType(ALIGN_NONE);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("m_wSentMsgCount", "m_wRecvdMsgCount");
    }

    public static class ByRef extends UcanMsgCountInfoStructure implements ByReference {
        public ByRef(int size) {
            super(size);
        }

        public ByRef() {
            super();
        }
    }
}

