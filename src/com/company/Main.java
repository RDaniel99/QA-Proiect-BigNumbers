package com.company;

public class Main {

    public static void main(String[] args) {


        BigNumber number = new BigNumber("15198819581758758158728715696138967169186791767159951");
        BigNumber number2 = new BigNumber("119285282875982752716786719867");

        System.out.println(number.division(number2));
    }
}
