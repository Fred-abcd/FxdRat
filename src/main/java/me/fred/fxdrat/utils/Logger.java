package me.fred.fxdrat.utils;

import me.fred.fxdrat.FxdRat;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String RED_BOLD = "\033[1;31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_GRAY = "\u001B[36m";

    private static boolean debug = false;

    public static void log(String msg, LogLevel level) {
        switch (level) {
            case INFO:
                System.out.println(ANSI_WHITE + "[INFO] " + ANSI_RESET + msg);
                break;
            case DEBUG:
                if (debug)
                    System.out.println(ANSI_GRAY + "[DEBUG] " + ANSI_RESET + msg);
                break;
            case ERROR:
                System.out.println(ANSI_RED + "[ERROR] " + ANSI_RESET + msg);
                break;
            case FATAL:
                System.out.println(RED_BOLD + "[FATAL] " + ANSI_RESET + msg);
                FxdRat.fatalcount++;
                break;
            case WARN:
                System.out.println(ANSI_YELLOW + "[WARN] " + ANSI_RESET + msg);
                break;
        }
    }

    public static void setDebug(boolean b) {
        debug = b;
    }
}

