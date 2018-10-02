package com.github.jakimli.pandaria.domain.wait;

public interface Waitable<T> {
    void retry();

    T result();
}
