package com.example.resortmanagement.model;

public class ListModel {
    private String string1;
    private String string2;
    private String string3;
    private String string4;
    private boolean cancellable;

    public ListModel(String string1, String string2, String string3, String string4, boolean cancellable) {
        this.string1 = string1;
        this.string2 = string2;
        this.string3 = string3;
        this.string4 = string4;
        this.cancellable = cancellable;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    public String getString3() {
        return string3;
    }

    public String getString4() {
        return string4;
    }

    public boolean isCancellable() {
        return cancellable;
    }
}
