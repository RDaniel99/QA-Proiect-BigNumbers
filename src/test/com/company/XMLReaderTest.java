package test.com.company;

import com.company.xml.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class XMLReaderTest {
    private XMLReader xmlReader;
    private String filename = "Test";
    private String wrongFilename = "Test_Error";
    private String xmlContent = "xml content";

    @Mock
    private XMLInput xmlInput;
    @Mock
    private XMLValidator xmlValidator;
    @Mock
    private XMLParser xmlParser;
    @Mock
    private XMLFileReader xmlFileReader;

    @BeforeEach
    private void setUp() {

        xmlValidator = mock(XMLValidator.class);
        xmlFileReader = mock(XMLFileReader.class);
        xmlParser = mock(XMLParser.class);
        xmlInput = mock(XMLInput.class);
        xmlReader = new XMLReader(xmlValidator, xmlParser, xmlFileReader);
    }

    @Test
    public void successfullyParsedWhenXmlIsValid() throws FileNotFoundException {

        when(xmlFileReader.read(filename)).thenReturn(xmlContent);
        when(xmlParser.parse(xmlContent)).thenReturn(xmlInput);
        assertDoesNotThrow(() -> xmlReader.start(filename));

        verify(xmlFileReader).read(filename);
        verify(xmlValidator).validate(xmlContent);
        verify(xmlParser).parse(xmlContent);
    }

    @Test
    public void throwsFileNotFoundWhenXmlFileReaderThrowsException() throws FileNotFoundException {

        when(xmlFileReader.read(wrongFilename)).thenThrow(FileNotFoundException.class);

        assertThrows(FileNotFoundException.class, () -> xmlReader.start(wrongFilename));

        verifyZeroInteractions(xmlValidator);
        verifyZeroInteractions(xmlParser);
    }

    @Test
    public void parserIsNotCalledWhenXmlValidatorThrowsException() throws FileNotFoundException {

        when(xmlFileReader.read(filename)).thenReturn(xmlContent);
        doThrow(InvalidParameterException.class).when(xmlValidator).validate(xmlContent);
        assertDoesNotThrow(() -> xmlReader.start(filename));

        verify(xmlFileReader).read(filename);
        verify(xmlValidator).validate(xmlContent);
        verifyZeroInteractions(xmlParser);
        assertNull(xmlReader.getInput());
    }

    @Test
    public void inputIsNullWhenXmlParserThrowsException() throws FileNotFoundException {

        when(xmlFileReader.read(filename)).thenReturn(xmlContent);
        when(xmlParser.parse(xmlContent)).thenThrow(InvalidParameterException.class);
        assertDoesNotThrow(() -> xmlReader.start(filename));

        verify(xmlFileReader).read(filename);
        verify(xmlValidator).validate(xmlContent);
        verify(xmlParser).parse(xmlContent);
        assertNull(xmlReader.getInput());
    }
}
