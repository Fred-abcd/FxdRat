package me.fred.fxdrat;

import me.fred.fxdrat.checker.CheckManager;
import me.fred.fxdrat.utils.LogLevel;
import me.fred.fxdrat.utils.Logger;
import me.fred.fxdrat.utils.UpdateUtil;

import java.io.File;

public class FxdRat {
    public static final String version = "1.0.5", author = "FxD";
    public static int fatalcount = 0;

    private static CheckManager cm;

    public static void main(String[] args) {
        Logger.log("FxdRat v" + version + " by " + author, LogLevel.INFO);

        UpdateUtil.checkForUpdates();

        if (args.length == 0) {
            Logger.log("Usage: java -jar fxdrat.jar <filepath>", LogLevel.ERROR);
            return;
        }
        if (args.length == 2)
            if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
                Logger.setDebug(args[1].equalsIgnoreCase("true"));
            }

        Logger.log("Loading checks...", LogLevel.DEBUG);
        cm = new CheckManager();
        Logger.log("Loaded " + cm.getChecks().size() + " checks.", LogLevel.DEBUG);
        Logger.log("Starting checks on " + args[0] + ".", LogLevel.INFO);

        cm.executeChecks(args[0]);

        Logger.log("Finished checks on " + args[0], LogLevel.INFO);
        System.out.println("");

        if (fatalcount > 5) {
            Logger.log("This file is a RAT! Fatal score: " + fatalcount, LogLevel.FATAL);
        } else if (fatalcount > 0) {
            Logger.log("The scan has found " + fatalcount + " Fatal evidence that the specified file could be a Rat.", LogLevel.FATAL);
        } else {
            Logger.log("The file seems to be safe...", LogLevel.INFO);
        }
    }
}

//TODO gui
//TODO check selector
