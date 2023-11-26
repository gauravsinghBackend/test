package com.example.LoginPage.KidAddition;

public enum KidGender {
    BOY,
    GIRL,
    RATHERNOTSAY;


    public static KidGender fromString(String gender) {
        if (gender != null) {
            switch (gender.toUpperCase()) {
                case "BOY":
                    return BOY;
                case "GIRL":
                    return GIRL;
                case "RATHERNOTSAY":
                    return RATHERNOTSAY;
                // Add more cases if needed
                default:
                    throw new IllegalArgumentException("Invalid gender: " + gender);
            }
        }
        throw new IllegalArgumentException("Gender cannot be null");
    }
}
