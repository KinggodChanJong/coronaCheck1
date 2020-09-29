package com.kang.coronacheck1;

public class FlagVar  {

    private static int state,fontvar;

    public void var() {
        //전역 변수 초기화
        state = 0;
        fontvar = 0;
    }


    public static int setState(int a){
        state = a;
        return state;
    }
    public static int getState(){
        return state;
    }

    public static int  setFontvar(int a){
        fontvar = a;
        return  fontvar;
    }
    public static int getFontvar(){
        return fontvar;
    }


}