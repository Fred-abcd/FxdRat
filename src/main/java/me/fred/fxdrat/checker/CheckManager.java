package me.fred.fxdrat.checker;

import javassist.ClassPool;
import me.fred.fxdrat.checker.impl.DiscordCheck;
import me.fred.fxdrat.checker.impl.HttpCheck;
import me.fred.fxdrat.checker.impl.MinecraftCheck;
import me.fred.fxdrat.checker.impl.StringCheck;
import me.fred.fxdrat.utils.LogLevel;
import me.fred.fxdrat.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CheckManager {
    private static ArrayList<Check> checks = new ArrayList<Check>();

    private static void addCheck(Check check) {
        checks.add(check);
    }

    public ArrayList<Check> getChecks() {
        return checks;
    }


    public CheckManager() {
        addCheck(new HttpCheck());
        addCheck(new DiscordCheck());
        addCheck(new MinecraftCheck());
        addCheck(new StringCheck());
    }

    public void executeChecks(String filePath) {
        for (Check check : getChecks()) {
            Logger.log("check: " + check.checkName, LogLevel.DEBUG);
            startCheck(check, filePath);
        }
    }

    private void startCheck(Check check, String filePath) {
        try (JarFile jarFile = new JarFile(new File(filePath))) {
            Enumeration<JarEntry> entries = jarFile.entries();
            ClassPool pool = ClassPool.getDefault();
            check.check(jarFile, entries, pool);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
