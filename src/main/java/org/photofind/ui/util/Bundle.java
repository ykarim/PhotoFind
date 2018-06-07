package org.photofind.ui.util;

public class Bundle<T> {

    private T data;

    public Bundle(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
