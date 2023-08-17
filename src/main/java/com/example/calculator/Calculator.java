package com.example.calculator;

public class Calculator {
    public int calculate(String str){
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i)==')'){
                return calculate(calcParen(str, i));
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i)=='^'){
                return calculate(makeOperation(str, i));
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i)=='%'){
                return calculate(makeOperation(str, i));
            }
        }
        for (int i = 0; i < str.length()-1; i++) {
            if(str.charAt(i)=='×' || str.charAt(i)=='*' || str.charAt(i)=='÷' || str.charAt(i)=='/'){
                return calculate(makeOperation(str, i));
            }
        }
        for (int i = 1; i < str.length()-1; i++) {
            if(str.charAt(i)=='+' || str.charAt(i)=='-'){
                return calculate(makeOperation(str, i));
            }
        }
        return Integer.parseInt(str);
    }
    private String calcParen(String str, int pos){
        int beginParen = 0;
        for (int i = pos-1; i >= 0; i--) {
            if(str.charAt(i)=='('){
                beginParen = i;
                break;
            }
        }
        String res = str.substring(beginParen+1, pos);
        return str.substring(0, beginParen) + calculate(res) + str.substring(pos+1);
    }
    private String makeOperation(String str, int pos){
        int beforeOpPos = 0;
        int afterOpPos = str.length();
        String aNum = "";
        String bNum = "";
        for (int i = pos+1; i < str.length(); i++) {
            if (i==pos+1 && str.charAt(i)=='-'){
                bNum = str.substring(i, i+1);
                i++;
            }
            if(isDigit(str.substring(i, i+1))){
                bNum += str.substring(i, i+1);
            }else{
                afterOpPos = i;
                break;
            }
        }
        for (int i = pos-1; i >= 0; i--) {
            if(isDigit(str.substring(i, i+1))){
                aNum = str.charAt(i) + aNum;
                if(i==0){
                    beforeOpPos = -1;
                }
            }else{
                if(str.charAt(i)=='-'){
                    aNum = str.charAt(i) + aNum;
                    beforeOpPos = i-1;
                    break;
                }
                beforeOpPos = i;
                break;
            }
        }
        int res = operation(Integer.parseInt(aNum), str.substring(pos, pos+1), Integer.parseInt(bNum));
        return str.substring(0, beforeOpPos+1) + res + str.substring(afterOpPos);
    }
    private int operation(int x, String op, int y){
        return switch (op) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*", "×" -> x * y;
            case "/", "÷" -> x / y;
            case "^" -> (int)Math.pow(x, y);
            case "%" -> x % y;
            default -> 0;
        };
    }
    public String negate(String str){
        if (isOperator(getLastNumber(str))){
            return str;
        }
        if (getLastNumber(str).startsWith("-")){
            return getAllExceptLastNumber(str) + getLastNumber(str).substring(1);
        }
        return getAllExceptLastNumber(str) + "-" + getLastNumber(str);
    }
    public String sqrt(String str){
        double num = Double.parseDouble(getLastNumber(str));
        return getAllExceptLastNumber(str) + Math.sqrt(num);
    }
    public String factorial(String str){
        String num = getLastNumber(str);
        int res = 1;
        if (num.startsWith("-")){
            return "Error!";
        }
        for (int i = Integer.parseInt(num); i > 1; i--) {
            res = res * i;
        }
        return getAllExceptLastNumber(str) + res;
    }
    private String getLastNumber(String str){
        if(str.length()==0){
            return "";
        }
        if (isOperator(str.substring(str.length()-1))){
            return str.substring(str.length()-1);
        }
        for (int i = str.length()-1; i >= 1; i--) {
            if (isOperator(str.substring(i,i+1))){
                if (isOperator(str.substring(i-1, i))){
                    return str.substring(i);
                }
                return str.substring(i+1);
            }
        }
        return str;
    }
    private String getAllExceptLastNumber(String str){
        return str.substring(0, str.length()-getLastNumber(str).length());
    }
    public boolean isDigit(String x){
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        for (String number : numbers) {
            if (number.equals(x)) {
                return true;
            }
        }
        return false;
    }
    public boolean isOperator(String x){
        String[] operators = {"+","-","*","×","/","÷","^","%"};
        for (String operator : operators) {
            if (operator.equals(x)) {
                return true;
            }
        }
        return false;
    }
}
