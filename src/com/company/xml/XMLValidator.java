package com.company.xml;

import java.security.InvalidParameterException;

public class XMLValidator {

    public void validate(String xml) {

        if(!xml.startsWith("<expression>")) {

            throw new InvalidParameterException("XML is not valid. It has to start with an expression");
        }

        if(!xml.contains("</expression>")) {

            throw new InvalidParameterException("XML is not valid. It does not have an end tag for expression");
        }
    }
}
