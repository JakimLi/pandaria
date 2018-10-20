package com.github.jakimli.pandaria.domain.wait;

public abstract class Repeatable implements Waitable<Object> {

    @Override
    public abstract void retry();

    @Override
    public Object result() {
        return null;
    }
}
