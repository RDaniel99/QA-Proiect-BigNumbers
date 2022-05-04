package test.com.company;

import com.company.BigNumber;
import com.company.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    public Parser parser;

    @BeforeEach
    private void setUp() {

        parser = new Parser();
    }

    @Test
    public void successfullyExtractVariablesFromABasicExpression() {

        //setup
        String expression = "a + b";

        //execute
        Set<Character> characters = parser.getVariables(expression);

        //verify
        Set<Character> expectedCharacters = new HashSet<>();
        expectedCharacters.add('a');
        expectedCharacters.add('b');

        assertEquals(expectedCharacters.size(), characters.size());
        assertTrue(characters.containsAll(expectedCharacters));
    }

    @Test
    public void successfullyExtractVariablesFromAnExpressionWithSquareRoot() {

        //setup
        String expression = "Sa + (b)";

        //execute
        Set<Character> characters = parser.getVariables(expression);

        //verify
        Set<Character> expectedCharacters = new HashSet<>();
        expectedCharacters.add('a');
        expectedCharacters.add('b');

        assertEquals(expectedCharacters.size(), characters.size());
        assertTrue(characters.containsAll(expectedCharacters));
    }

    @Test
    public void successfullyExtractVariablesFromAnExpressionWithDuplicates() {

        //setup
        String expression = "Sa + (Sa + b)^c";

        //execute
        Set<Character> characters = parser.getVariables(expression);

        //verify
        Set<Character> expectedCharacters = new HashSet<>();
        expectedCharacters.add('a');
        expectedCharacters.add('b');
        expectedCharacters.add('c');

        assertEquals(expectedCharacters.size(), characters.size());
        assertTrue(characters.containsAll(expectedCharacters));
    }

    @Test
    public void successfullyEvaluateABasicAddExpression() {

        //setup
        String expression = "a + b";
        Map<Character, BigNumber> values = new HashMap<>();
        values.put('a', new BigNumber("10"));
        values.put('b', new BigNumber("20"));

        //execute
        BigNumber result = parser.execute(expression, values);

        //verify
        BigNumber expected = new BigNumber("30");
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void successfullyEvaluateABasicExpressionWithSquareRoot() {

        //setup
        String expression = "(Sa)";
        Map<Character, BigNumber> values = new HashMap<>();
        values.put('a', new BigNumber("9"));

        //execute
        BigNumber result = parser.execute(expression, values);

        //verify
        BigNumber expected = new BigNumber("3");
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void successfullyEvaluateAComplexExpressionWithAllOperations() {

        //setup
        String expression = "(Sa + b) ^ c * Sc - (a - c/b + b - Sa )";
        Map<Character, BigNumber> values = new HashMap<>();
        values.put('a', new BigNumber("9"));
        values.put('b', new BigNumber("3"));
        values.put('c', new BigNumber("2"));

        //execute
        BigNumber result = parser.execute(expression, values);

        //verify
        BigNumber expected = new BigNumber("27");
        assertEquals(expected.toString(), result.toString());
    }
}