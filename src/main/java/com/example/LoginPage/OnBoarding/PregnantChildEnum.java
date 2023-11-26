package com.example.LoginPage.OnBoarding;

public enum PregnantChildEnum {
    AREPREGNANT,
    HAVECHILD;

    public static PregnantChildEnum fromPregnantChild(boolean arePregnant) {
        return arePregnant ? AREPREGNANT : HAVECHILD;
    }
}
