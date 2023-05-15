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

    // Logs will be found in Event Viewer under Windows logs, Applications.
    // Source is "AutomationLTU", log will be displayed in the Details tab.

    /*
    Eventlog types:

    (EVENTLOG_SUCCESS, treated same as INFORMATION)

    EVENTLOG_AUDIT_FAILURE

    EVENTLOG_AUDIT_SUCCESS

    EVENTLOG_ERROR_TYPE

    EVENTLOG_INFORMATION_TYPE

    EVENTLOG_WARNING_TYPE
    */

    private static final String APP_NAME = "AutomationLTU";
    public static void log(String message, int eventLogType) { // Message will be displayed in Event Viewer "Info" tab.
        HANDLE h = Advapi32.INSTANCE.RegisterEventSource(null, APP_NAME);
        WString wMessage = new WString(message); // Wide character format required for Event Viewer
        Advapi32.INSTANCE.ReportEvent(
                h,                                  // Handle to the event log
                eventLogType,                       // See line 18 for EVENTLOG types
                0,                                  // Reserved parameter, should be set to 0.
                0,                                  // Reserved parameter, should be set to 0.
                null,                               // Event identifier, not specified.
                1,                                  // Number of replacement strings
                0,                                  // Binary data associated with the event, too advanced for us, didn't implement.
                new String[]{wMessage.toString()},  // String to be shown in log
                null                                // Reserved parameter, should be set to 0.
        );
        Kernel32.INSTANCE.CloseHandle(h);
    }

}

