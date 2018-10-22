package com.github.jakimli.pandaria.utils;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptUtil {
    public static Object eval(String script) throws ScriptException {
        return new ScriptEngineManager()
                .getEngineByName("nashorn")
                .eval(script);
    }
}
