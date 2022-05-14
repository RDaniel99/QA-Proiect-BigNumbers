package test.com.company;

import com.company.xml.XMLValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XMLValidatorTest {
    private XMLValidator xmlValidator;


    @BeforeEach
    private void setUp() {

        xmlValidator = new XMLValidator();
    }

    @Test
    public void throwsExceptionWhenExpressionStartInvalid(){

        String invalidXml = "(a+b)</expression><variable><name>a</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlValidator.validate(invalidXml));
    }

    @Test
    public void throwsExceptionWhenExpressionContentInvalid(){

        String invalidXml = "<expression>(a+b)<variable><name>a</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertThrows(InvalidParameterException.class, () -> xmlValidator.validate(invalidXml));
    }

    @Test
    public void successfullyWhenExpressionValid(){

        String validXml = "<expression>(a+b)</expression><variable><name>a</name><value>2</value></variable><variable><name>b</name><value>3</value></variable>";

        assertDoesNotThrow(() -> xmlValidator.validate(validXml));
    }
}
