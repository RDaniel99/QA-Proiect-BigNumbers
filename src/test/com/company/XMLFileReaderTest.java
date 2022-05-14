package test.com.company;

import com.company.xml.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class XMLFileReaderTest {
    private XMLFileReader xmlFileReader;
    @Mock
    private Scanner scanner;

    @BeforeEach
    private void setUp() {
        scanner = mock(Scanner.class);
        xmlFileReader = new XMLFileReader(scanner);
    }

//    @Test
//    public void successfullyWhenReadScannerInputValid(){
//
//        assertDoesNotThrow(() -> xmlFileReader.read());
//    }
}
