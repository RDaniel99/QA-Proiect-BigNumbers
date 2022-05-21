package com.company.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class XMLFileReader {
    private Scanner scanner;
    private String fileName;

    public XMLFileReader(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        File file = new File(this.fileName);
        scanner = new Scanner(file);
    }

    public XMLFileReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String read() {
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {

            builder.append(scanner.nextLine());
        }

        return builder.toString().replace(" ", "");
    }
}
