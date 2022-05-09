package com.company.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class XMLFileReader {

    public String read(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner =  new Scanner(file);
        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine()) {

            builder.append(scanner.nextLine());
        }

        return builder.toString().replace(" ", "");
    }
}
