package com.company;

public class Main {

    public static void main(String[] args) {


        BigNumber number = new BigNumber("2");
        BigNumber number2 = new BigNumber("4");

        System.out.println(BigNumber.raisedTo(number, number2));
    }
}
