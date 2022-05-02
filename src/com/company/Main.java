package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        Parser parser = new Parser();

        while (true) {

            System.out.println("Please enter the input mode: 1(console), 2(from file) or 3(exit)");

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

                XMLReader reader = new XMLReader();
                reader.start();

                System.out.println("Output file: ");
                FileWriter file = new FileWriter(scanner.next());

                file.write(parser.execute(reader.getInput().getExpression(), reader.getInput().getValues()).toString());

                file.close();

            } else if (userMode.equals("3")) {

                break;
            }
        }
    }
}
