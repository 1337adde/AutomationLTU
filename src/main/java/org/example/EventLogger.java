package org.example;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.Pointer;
import com.sun.jna.Native;
import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinNT.EVENTLOGRECORD;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.Memory;
import com.sun.jna.WString;
public class EventLogger {
    private static final String APP_NAME = "AutomationLTU";

    public static void logUserLogin(String message, int eventLogType) {
        HANDLE h = Advapi32.INSTANCE.RegisterEventSource(null, APP_NAME);
        String[] strings = new String[1];
        strings[0] = message;
        // Allocating bytes to memory, chars in java are 16bytes
        Pointer p = new Memory((strings[0].length() + 1) * Native.WCHAR_SIZE);
        p.setWideString(0, strings[0]);
        Advapi32.INSTANCE.ReportEvent(h, eventLogType, 0, 0, null, 1, 0, new String[]{p.getString(0)}, null);
        Kernel32.INSTANCE.CloseHandle(h);
    }

}

