package me.fred.fxdrat.checker.impl;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Opcode;
import me.fred.fxdrat.checker.Check;
import me.fred.fxdrat.utils.LogLevel;
import me.fred.fxdrat.utils.Logger;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class BrowserCheck extends Check {
    public BrowserCheck() {
        super("Browser Check");
    }

    @Override
    protected void check(JarFile jarFile, Enumeration<JarEntry> entries, ClassPool pool) {
        Logger.log("Starting browser check", LogLevel.DEBUG);
        try {
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.getName().endsWith(".class")) {
                    CtClass cc = pool.makeClass(jarFile.getInputStream(entry));
                    for (CtMethod method : cc.getDeclaredMethods()) {
                        if (method.getMethodInfo().getCodeAttribute() != null) {
                            CodeIterator ci = method.getMethodInfo().getCodeAttribute().iterator();
                            ConstPool constPool = method.getMethodInfo().getConstPool();

                            while (ci.hasNext()) {
                                int index = ci.next();
                                int op = ci.byteAt(index);

                                if (op == Opcode.LDC || op == Opcode.LDC_W) {
                                    int constIndex = ci.u16bitAt(index + 1);
                                    if (constIndex < constPool.getSize()) {
                                        if (constPool.getTag(constIndex) == ConstPool.CONST_String) {
                                            String value = constPool.getStringInfo(constIndex);
                                            if (value != null) {
                                                if (value.contains("Chrome") && value.contains("User Data")) {
                                                    Logger.log("The file tries to access google chrome user data.", LogLevel.FATAL);
                                                } else if (value.contains("Opera Software") && value.contains("Opera Stable")) {
                                                    Logger.log("The file tries to access opera user data.", LogLevel.FATAL);
                                                } else if (value.contains("BraveSoftware") && value.contains("User Data")) {
                                                    Logger.log("The file tries to access brave user data.", LogLevel.FATAL);
                                                } else if (value.contains("Yandex") && value.contains("User Data")) {
                                                    Logger.log("The file tries to access yandex user data.", LogLevel.FATAL);
                                                } else if (value.contains("Edge") && value.contains("User Data")) {
                                                    Logger.log("The file tries to access edge user data.", LogLevel.FATAL);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
