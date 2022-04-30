package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser();

        while (true) {

            System.out.println("Please enter the input mode: 1(console) or 2(from file)");

            Scanner scanner = new Scanner(System.in);

            String userMode = scanner.nextLine();

            if (userMode.equals("1")) {

                System.out.println("Please enter the expression:");

                String expression = scanner.nextLine();

                Set<Character> characterList = parser.getVariables(expression);

                Map<Character, BigNumber> values = new HashMap<>();

                for (Character character : characterList) {

                    System.out.println(String.format("Please enter value for %s:", character));

                    String number = scanner.nextLine();

                    values.put(character, new BigNumber(number));
                }

                System.out.println(parser.execute(expression, values));

            } else if (userMode.equals("2")) {

                //read from file
            } else {

            }
        }
    }
}
