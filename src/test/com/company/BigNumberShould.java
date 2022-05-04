package test.com.company;

import com.company.BigNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigNumberShould {

    @Test
    public void successfullyAddTwoBigIntegersWhenNoCarryIsNeeded() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("20");

        //execute
        BigNumber result = firstNumber.add(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("30");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyAddTwoBigIntegersWhenCarryIsNeeded() {

        //setup
        BigNumber firstNumber = new BigNumber("192");
        BigNumber secondNumber = new BigNumber("49");

        //execute
        BigNumber result = firstNumber.add(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("241");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullySubtractTwoBigIntegersWhenNoCarryIsNeeded() {

        //setup
        BigNumber firstNumber = new BigNumber("20");
        BigNumber secondNumber = new BigNumber("10");

        //execute
        BigNumber result = firstNumber.subtract(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("10");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullySubtractTwoBigIntegersWhenCarryIsNeeded() {

        //setup
        BigNumber firstNumber = new BigNumber("121");
        BigNumber secondNumber = new BigNumber("39");

        //execute
        BigNumber result = firstNumber.subtract(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("82");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void throwExceptionWhenSubtractTwoBigIntegersAndTheResultIsNegative() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("20");

        //verify
        Assertions.assertThrows(InvalidParameterException.class, () -> firstNumber.subtract(secondNumber));
    }

    @Test
    public void successfullyMultiplyTwoBigIntegersWhenOneHasOnlyOneDigit() {

        //setup
        BigNumber firstNumber = new BigNumber("12");
        BigNumber secondNumber = new BigNumber("4");

        //execute
        BigNumber result = firstNumber.multiply(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("48");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyMultiplyTwoBigIntegers() {

        //setup
        BigNumber firstNumber = new BigNumber("12");
        BigNumber secondNumber = new BigNumber("14");

        //execute
        BigNumber result = firstNumber.multiply(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("168");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyDivideTwoBigIntegersWhenOneHasOnlyOneDigit() {

        //setup
        BigNumber firstNumber = new BigNumber("12");
        BigNumber secondNumber = new BigNumber("3");

        //execute
        BigNumber result = firstNumber.division(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("4");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void throwExceptionWhenDivideABigNumberToZero() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("0");

        //verify
        Assertions.assertThrows(InvalidParameterException.class, () -> firstNumber.division(secondNumber));
    }

    @Test
    public void successfullyDivideTwoBigIntegersWhenTheDivisorIsOne() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("1");

        //execute
        BigNumber result = firstNumber.division(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("10");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyDivideTwoBigIntegersWhenTheNumbersAreEquals() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("10");

        //execute
        BigNumber result = firstNumber.division(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("1");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyDivideTwoBigIntegersWhenTheSecondNumberIsBigger() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("20");

        //execute
        BigNumber result = firstNumber.division(secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("0");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyCalculateThePowerOfABigIntegerWhenExponentIsZero() {

        //setup
        BigNumber firstNumber = new BigNumber("10");
        BigNumber secondNumber = new BigNumber("0");

        //execute
        BigNumber result = BigNumber.raisedTo(firstNumber, secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("1");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyCalculateThePowerOfABigInteger() {

        //setup
        BigNumber firstNumber = new BigNumber("12");
        BigNumber secondNumber = new BigNumber("2");

        //execute
        BigNumber result = BigNumber.raisedTo(firstNumber, secondNumber);

        //verify
        BigNumber expectedResult = new BigNumber("144");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyCalculateTheSquareRootOfABigIntegerWhenTheNumberIsASquareNumber() {

        //setup
        BigNumber number = new BigNumber("121");

        //execute
        BigNumber result = BigNumber.squareRoot(number);

        //verify
        BigNumber expectedResult = new BigNumber("11");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyCalculateTheSquareRootOfABigIntegerWhenTheNumberIsNotASquareNumber() {

        //setup
        BigNumber number = new BigNumber("134");

        //execute
        BigNumber result = BigNumber.squareRoot(number);

        //verify
        BigNumber expectedResult = new BigNumber("11");

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    public void successfullyCreateANewBigNumberWhenTheStringContainsOnlyDigits() {

        //setup
        BigNumber number = new BigNumber("134");

        //verify
        assertEquals("134", number.toString());
    }

}