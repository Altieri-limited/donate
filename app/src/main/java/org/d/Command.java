package org.d;

public interface Command<T> {
    void exec(T value);
}
