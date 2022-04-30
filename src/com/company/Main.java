package com.company;

public class Main {

    public static void main(String[] args) {


        BigNumber number = new BigNumber("1234141588");
        BigNumber number2 = new BigNumber("100");

        System.out.println(BigNumber.raisedTo(number, number2));
    }
}
