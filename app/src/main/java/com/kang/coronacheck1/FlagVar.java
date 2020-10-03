package com.kang.coronacheck1;

public class FlagVar  {

    private static int state;

    public static int setState(int a){
        state = a;
        return state;
    }
    public static int getState(){
        return state;
    }
}