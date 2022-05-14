package com.company.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class XMLReader {

    private XMLInput input;
    private XMLValidator xmlValidator;
    private XMLParser xmlParser;
    private XMLFileReader xmlFileReader;

    public XMLReader() {
        this.xmlValidator = new XMLValidator();
        this.xmlParser = new XMLParser();
    }

    public XMLReader(XMLValidator xmlValidator, XMLParser xmlParser, XMLFileReader xmlFileReader) {
        this.xmlValidator = xmlValidator;
        this.xmlParser = xmlParser;
        this.xmlFileReader = xmlFileReader;
    }

    public void start(String fileName) throws FileNotFoundException {
        if (this.xmlFileReader == null)
            this.xmlFileReader = new XMLFileReader(fileName);

        String xml = xmlFileReader.read();

        try {
            xmlValidator.validate(xml);
        } catch (InvalidParameterException e) {

            System.out.println(e.getMessage());
            return ;
        }

        try {
            input = xmlParser.parse(xml);
        } catch (InvalidParameterException e) {

            System.out.println(e.getMessage());
            return ;
        }

        System.out.println();
    }

    public XMLInput getInput() {
        return input;
    }
}
