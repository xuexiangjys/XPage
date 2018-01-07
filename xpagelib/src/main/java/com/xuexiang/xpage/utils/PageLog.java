package com.xuexiang.xpage.utils;

import android.util.Log;

/**
 * This class can replace android.util.Log.
 * @description And you can turn off the log by set gDebugLevel = Log.ASSERT.
 */
public final class PageLog {

    /**
     * Master switch.To catch error info you need set this value below Log.WARN
     */
    public static int gDebugLevel = 10;

    /**
     * 'System.out' switch.When it is true, you can see the 'System.out' log.
     * Otherwise, you cannot.
     */
    public static boolean DEBUG_SYSOUT = false;

    public static String sLogTag = "PageLog";

    public static void setDebugMode(boolean isDebug) {
        if (isDebug) {
            gDebugLevel = 0;
        } else {
            gDebugLevel = 10;
        }
    }

    public static void setLogTag(String tag) {
        sLogTag = tag;
    }

    public static void setDebugLevel(int level) {
        gDebugLevel = level;
    }

    public static void setIsDebugSysout(boolean isDebugSysout) {
        DEBUG_SYSOUT = isDebugSysout;
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v( String msg) {
        if (Log.VERBOSE > gDebugLevel) {
            Log.v(sLogTag, msg);
        }
    }

    /**
     * Send a {@link #gDebugLevel} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String msg) {
        if (Log.DEBUG > gDebugLevel) {
            Log.d(sLogTag, msg);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        if (Log.INFO > gDebugLevel) {
            Log.i(sLogTag, msg);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        if (Log.WARN > gDebugLevel) {
            Log.w(sLogTag, msg);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e( String msg) {
        if (Log.ERROR > gDebugLevel) {
            Log.e(sLogTag, msg);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e( String msg, Throwable throwable) {
        if (Log.ERROR > gDebugLevel) {
            Log.e(sLogTag, msg, throwable);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param msg The message you would like logged.
     */
    public static void wtf(String msg) {
        if (Log.ASSERT > gDebugLevel) {
            Log.wtf(sLogTag, msg);
        }
    }

    /**
     * Send a {@link Log#VERBOSE} log message. And just print method name and
     * position in black.
     */
    public static void print() {
        if (Log.VERBOSE > gDebugLevel) {
            String method = callMethodAndLine();
            Log.v(sLogTag, method);
            if (DEBUG_SYSOUT) {
                System.out.println(sLogTag + "  " + method);
            }
        }
    }

    /**
     * Send a {@link #gDebugLevel} log message.
     *
     * @param object The object to print.
     */
    public static void print(Object object) {
        if (Log.DEBUG > gDebugLevel) {
            String method = callMethodAndLine();
            String content = "";
            if (object != null) {
                content = object.toString() + "                    ----    "
                        + method;
            } else {
                content = " ## " + "                ----    " + method;
            }
            Log.d(sLogTag, content);
            if (DEBUG_SYSOUT) {
                System.out.println(sLogTag + "  " + content + "  " + method);
            }
        }
    }

    /**
     * Realization of double click jump events.
     *
     * @return
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

}
