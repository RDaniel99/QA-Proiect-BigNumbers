package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class XMLReader {

    private String xmlVariables;
    private XMLInput input;

    public void start() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("File: ");

        String fileName = scanner.next();

        File file = new File(fileName);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {

            System.out.println("File not found");
        }

        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine()) {

            builder.append(scanner.nextLine());
        }

        String xml = builder.toString().replace(" ", "");

        try {
            validateXML(xml);
        } catch (InvalidParameterException e) {

            System.out.println(e.getMessage());
            return ;
        }

        try {
            parseXML(xml);
        } catch (InvalidParameterException e) {

            System.out.println(e.getMessage());
            return ;
        }

        System.out.println();
    }

    private void validateXML(String xml) {

        if(!xml.startsWith("<expression>")) {

            throw new InvalidParameterException("XML is not valid. It has to start with an expression");
        }

        if(!xml.contains("</expression>")) {

            throw new InvalidParameterException("XML is not valid. It does not have an end tag for expression");
        }
    }

    private void parseXML(String xml) {

        int endExpression = xml.indexOf("</expression>");

        String expression = xml.substring(12, endExpression);

        xmlVariables = xml.substring(endExpression + 13);

        Map<Character, BigNumber> values = new HashMap<>();

        while(!xmlVariables.isEmpty()) {

            if(!xmlVariables.startsWith("<variable>")) {

                throw new InvalidParameterException("Variables should be between <variable> and </variable> tags");
            }

            xmlVariables = xmlVariables.substring(10);

            Character variableName = subtractVariableName();
            String variableValueAsString = subtractVariableValue();

            if(values.containsKey(variableName)) {

                throw new InvalidParameterException("Variable " + variableName + " declared multiple times");
            }

            values.put(variableName, new BigNumber(variableValueAsString));

            if(!xmlVariables.startsWith("</variable>")) {

                throw new InvalidParameterException("Variables should be between <variable> and </variable> tags");
            }

            xmlVariables = xmlVariables.substring(11);
        }

        input = new XMLInput(expression, values);
    }

    public XMLInput getInput() {
        return input;
    }

    private Character subtractVariableName() {

        if(!xmlVariables.startsWith("<name>")) {

            throw new InvalidParameterException("Name of variables should be between <name> and </name> tags");
        }

        xmlVariables = xmlVariables.substring(6);

        String variableName = xmlVariables.substring(0, xmlVariables.indexOf("</name>"));

        xmlVariables = xmlVariables.substring(xmlVariables.indexOf("</name>") + 7);

        if(variableName.length() > 1) {

            throw new InvalidParameterException("Variable names should be formed only from one small letter");
        }

        if(variableName.length() == 0) {

            throw new InvalidParameterException("Variable name can not be empty");
        }

        return variableName.charAt(0);
    }

    private String subtractVariableValue() {

        if(!xmlVariables.startsWith("<value>")) {

            throw new InvalidParameterException("Name of variables should be between <value> and </value> tags");
        }

        xmlVariables = xmlVariables.substring(7);

        String variableName = xmlVariables.substring(0, xmlVariables.indexOf("</value>"));

        xmlVariables = xmlVariables.substring(xmlVariables.indexOf("</value>") + 8);

        return variableName;
    }
}
