package com.github.jakimli.pandaria.domain.wait;

public interface Waitable {
    void retry();

    Object result();
}
