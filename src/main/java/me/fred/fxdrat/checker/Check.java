package me.fred.fxdrat.checker;

import javassist.ClassPool;

import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class Check {
    String checkName;
    public Check(String checkName) {
        this.checkName = checkName;
    }

    protected abstract void check(JarFile jarFile, Enumeration<JarEntry> entries, ClassPool pool);
}
