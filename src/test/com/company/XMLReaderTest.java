package test.com.company;

import com.company.xml.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testng.annotations.Ignore;

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
    public void successfullyParsedWhenXmlIsValid() {

        when(xmlFileReader.read()).thenReturn(xmlContent);
        when(xmlParser.parse(xmlContent)).thenReturn(xmlInput);
        assertDoesNotThrow(() -> xmlReader.start(filename));

        verify(xmlFileReader).read();
        verify(xmlValidator).validate(xmlContent);
        verify(xmlParser).parse(xmlContent);
    }

    @Ignore
    public void throwsFileNotFoundWhenXmlFileReaderThrowsException() {

        when(xmlFileReader.read()).thenThrow(FileNotFoundException.class);

        assertThrows(FileNotFoundException.class, () -> xmlReader.start(wrongFilename));

        verifyNoInteractions(xmlValidator);
        verifyNoInteractions(xmlParser);
    }

    @Test
    public void parserIsNotCalledWhenXmlValidatorThrowsException() {

        when(xmlFileReader.read()).thenReturn(xmlContent);
        doThrow(InvalidParameterException.class).when(xmlValidator).validate(xmlContent);
        assertDoesNotThrow(() -> xmlReader.start(filename));

        verify(xmlFileReader).read();
        verify(xmlValidator).validate(xmlContent);
        verifyNoInteractions(xmlParser);
        assertNull(xmlReader.getInput());
    }

    @Test
    public void inputIsNullWhenXmlParserThrowsException() {

        when(xmlFileReader.read()).thenReturn(xmlContent);
        when(xmlParser.parse(xmlContent)).thenThrow(InvalidParameterException.class);
        assertDoesNotThrow(() -> xmlReader.start(filename));

        verify(xmlFileReader).read();
        verify(xmlValidator).validate(xmlContent);
        verify(xmlParser).parse(xmlContent);
        assertNull(xmlReader.getInput());
    }
}
