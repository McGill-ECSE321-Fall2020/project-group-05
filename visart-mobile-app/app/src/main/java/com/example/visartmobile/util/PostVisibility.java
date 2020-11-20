package com.example.visartmobile.util;

enum PostVisibility {

    Public, Private, Unlisted, Draft;

    public static PostVisibility fromString(String name) {
        try {
            return Enum.valueOf(PostVisibility.class, name);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
