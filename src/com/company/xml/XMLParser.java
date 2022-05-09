package com.company.xml;

import com.company.BigNumber;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    private String xmlVariables;

    public XMLInput parse(String xml) {

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

        return new XMLInput(expression, values);
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
