package me.fred.fxdrat.checker.impl;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Opcode;
import me.fred.fxdrat.checker.Check;
import me.fred.fxdrat.utils.LogLevel;
import me.fred.fxdrat.utils.Logger;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class HttpCheck extends Check {
    public HttpCheck() {
        super("Http check");
    }

    @Override
    protected void check(JarFile jarFile, Enumeration<JarEntry> entries, ClassPool pool) {
        Logger.log("Starting http check", LogLevel.DEBUG);
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
                                                if (value.toLowerCase().startsWith("http")) {
                                                    if (value.contains("pastebin.com")) {
                                                        Logger.log("Found Pastebin HTTP request URL: " + value + " in method " + method.getName(), LogLevel.WARN);
                                                    } else if (value.contains("checkip.amazonaws.com")) {
                                                        Logger.log("Found IP Grabber: " + value + " in method " + method.getName(), LogLevel.FATAL);
                                                    } else {
                                                        if (value.equalsIgnoreCase("http://")) {
                                                            continue;
                                                        }
                                                        Logger.log("Found HTTP request URL: " + value + " in method " + method.getName(), LogLevel.WARN);
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
            }
        } catch (Exception ignored) {}
    }
}
