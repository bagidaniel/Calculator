package com.example.calculator;

public class Error {
    Calculator calc = new Calculator();

    public void checkCalculation(String str){
        checkAllChar(str);
    }
    public String closeAllParen(String str){
        int beginParenCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i)=='('){
                beginParenCount++;
            }
            if(str.charAt(i)==')'){
                beginParenCount--;
            }
        }
        for (int i = 1; i <= beginParenCount; i++) {
            str += ")";
        }
        return str;
    }
    public String correctString(String str){
        if (!calc.isDigit(str.substring(0, 1)) && !str.startsWith("-") && !str.startsWith("(")){
            return correctString(str.substring(1));
        }
        if (!calc.isDigit(str.substring(str.length()-1))){
            return correctString(str.substring(0, str.length()-1));
        }
        return str;
    }
    private void checkAllChar(String str){
        for (int i = 0; i < str.length(); i++) {
            if (!isAllowedChar(str.substring(i,i+1))){
                System.err.println("Bad char: "  + str.charAt(i));
                System.exit(0);
            }
        }
    }
    private boolean isAllowedChar(String x){
        String[] chars = {"0","1","2","3","4","5","6","7","8","9","+","-","*","×","/","÷","^","%","(",")"};
        for (String ch : chars) {
            if (ch.equals(x)) {
                return true;
            }
        }
        return false;
    }
}
