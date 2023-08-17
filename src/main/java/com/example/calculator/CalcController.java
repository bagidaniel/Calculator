package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalcController {
    private final Calculator calc = new Calculator();
    private final Error error = new Error();
    private boolean isResult = false;
    private int parenIndex = 0;

    @FXML
    private TextField textField;
    @FXML
    private TextField calculationTextField;
    @FXML
    private Label openParenIndexLabel;

    @FXML
    public void getResult() {
        error.checkCalculation(textField.getText());
        textField.setText(error.correctString(textField.getText()));
        textField.setText(error.closeAllParen(textField.getText()));
        calculationTextField.setText(textField.getText() + "=");
        resetParenIndex();
        System.out.println(textField.getText());
        int res = calc.calculate(textField.getText());
        isResult = true;
        textField.setText(String.valueOf(res));
    }
    public void numberButtonClick(ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        if (isResult){
            textField.setText(value);
            isResult = false;
        }else{
            textField.setText(textField.getText() + value);
        }
    }
    public void opButtonClick(ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        isResult = false;
        if(textField.getText().length()>0 && calc.isOperator(textField.getText().substring(textField.getText().length()-1))){
            textField.setText(textField.getText(0, textField.getText().length()-1) + value);
        }else
        textField.setText(textField.getText() + value);
    }
    public void sqrtButtonClick(){
        textField.setText(calc.sqrt(textField.getText()));
    }
    public void negateButtonClick(){
        textField.setText(calc.negate(textField.getText()));
    }
    public void factorialButtonClick(){
        textField.setText(calc.factorial(textField.getText()));
    }
    public void parenButtonClick(ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        if (value.equals("(")){
            parenIndex++;
            textField.setText(handleParenAfterNumber(textField.getText()));
            handleParen(parenIndex, value);
        }
        if (value.equals(")") && parenIndex > 0){
            parenIndex--;
            handleParen(parenIndex, value);
            if (parenIndex==0){
                resetParenIndex();
            }
        }
    }
    public void handleParen(int index, String value){
        openParenIndexLabel.setText(String.valueOf(index));
        if (isResult){
            textField.setText(value);
            isResult = false;
        }else
        textField.setText(textField.getText() + value);
    }
    public String handleParenAfterNumber(String str){
        if (textField.getText().length() > 0 && calc.isDigit(str.substring(str.length()-1))){
            return str + "×";
        }
        return str;
    }
    public void resetParenIndex(){
        parenIndex = 0;
        openParenIndexLabel.setText("");
    }
    public void deleteButtonClick(){
        if (textField.getText().endsWith("(")){
            openParenIndexLabel.setText("" + --parenIndex);
            if (parenIndex == 0){
                resetParenIndex();
            }
        }
        if (textField.getText().length() == 0){
            clearButtonClick();
        }else
        textField.setText(textField.getText().substring(0, textField.getText().length()-1));
    }
    public void clearButtonClick(){
        textField.clear();
        calculationTextField.clear();
        resetParenIndex();
        isResult = false;
    }
}