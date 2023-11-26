package com.example.LoginPage.Models;

public enum ParentRole {
    FATHER,
    MOTHER;
    public static ParentRole fromString(String parentrole)
    {
        if (parentrole!=null)
        {
            switch (parentrole.toUpperCase()) {
                case "FATHER":
                    return FATHER;
                case "MOTHER":
                    return MOTHER;
                default:
                    throw new IllegalArgumentException("Invalid parentRole: " + parentrole);
            }

        }
        throw new IllegalArgumentException("parentRole cannot be null");
    }
}
