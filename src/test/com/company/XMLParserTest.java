package test.com.company;

import com.company.BigNumber;
import com.company.xml.XMLInput;
import com.company.xml.XMLParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XMLParserTest {
    private XMLParser xmlParser;

    @BeforeEach
    private void setUp() {
        xmlParser = new XMLParser();
    }

    @Test
    public void successfullyCreatesXmlInputWhenXmlIsValid(){

        String validXml = "<expression>(a+b)</expression><variable><name>a</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";
        Map<Character, BigNumber> expectedValues = new HashMap<>();
        expectedValues.put('a', new BigNumber("2"));
        expectedValues.put('b', new BigNumber("3"));

        XMLInput result = xmlParser.parse(validXml);

        assertEquals("(a+b)", result.getExpression());
        assertEquals(expectedValues.toString(), result.getValues().toString());

    }

    @Test
    public void throwsExceptionWhenXmlVariablesStartIsInvalid(){

        String invalidXml = "<expression>(a+b)</expression><name>a</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }

    @Test
    public void throwsExceptionWhenXmlVariablesEndIsInvalid(){

        String invalidXml = "<expression>(a+b)</expression><variable><name>a</name><value>2</value><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }

    @Test
    public void throwsExceptionWhenVariablesMultipleTimesDeclared(){

        String invalidXml = "<expression>(a+b)</expression><variable><name>a</name><value>2</value></variable><variable><name>a</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }

    @Test
    public void throwsExceptionWhenVariablesNameEmpty(){

        String invalidXml = "<expression>(a+b)</expression><variable><name>a</name><value>2</value></variable><variable><name></name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }

    @Test
    public void throwsExceptionWhenVariableNameTooLong(){

        String invalidXml = "<expression>(a+b)</expression><variable><name>abvf</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }

    @Test
    public void throwsExceptionWhenXmlVariablesNameInvalid(){

        String invalidXml = "<expression>(a+b)</expression><variable>a</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }

    @Test
    public void throwsExceptionWhenXmlVariablesValueInvalid(){

        String invalidXml = "<expression>(a+b)</expression><variable><name>a</name>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlParser.parse(invalidXml));
    }
}
