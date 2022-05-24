package com.company;

import jdk.internal.util.xml.impl.Parser;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class that represents a big integer positive number as an array and the all the basic operations that can be done
 * with it
 */
public class BigNumber {

    public static final BigNumber ZERO = new BigNumber("0");
    private static final BigNumber ONE = new BigNumber("1");
    private static final BigNumber TWO = new BigNumber("2");

    private Integer totalDigits;
    private final List<Integer> digits;

    public BigNumber(String number) {

        digits = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(number);
        builder.reverse();
        number = builder.toString();

        try {
            digits.addAll(number.chars()
                    .mapToObj(c -> Integer.parseInt(String.valueOf((char) c)))
                    .collect(Collectors.toList()));

        } catch (NumberFormatException e) {

            System.out.println("The argument " + builder.reverse() + " is not a positive number");
        }

        totalDigits = digits.size();
    }

    /**
     * Raise a big number to another big number and returns the result
     *
     * @param base     the number that will be raised to a given power
     * @param exponent the power used to raise the base
     * @return the result of base raised to the exponent as a BigNumber
     */
    public static BigNumber raisedTo(BigNumber base, BigNumber exponent) {

        assertTrue(base.compare(ZERO) >= 0);
        assertTrue(exponent.compare(ZERO) >= 0 );

        BigNumber result;
        if (exponent.compare(ZERO) == 0) {

            result = ONE;
            assertEquals(result.compare(ONE), 0);

            return ONE;
        }

        result = raisedTo(base, exponent.division(TWO));

        if (exponent.isEven()) {

            result = result.multiply(result);

            return result;
        }

        return result.multiply(result).multiply(base);
    }

    /**
     * Calculated the square root of a given {@link BigNumber}
     *
     * @param number The number fot which we want to calculate the square root
     * @return The square root of the number if the square is an integer, otherwise the square root rounded down
     */
    public static BigNumber squareRoot(BigNumber number) {

        assertTrue(number.compare(ZERO) >= 0);

        BigNumber left = ZERO;
        BigNumber right = number;

        while (left.compare(right) <= 0) {

            assertTrue(left.compare(right) <= 0);

            BigNumber middle = left.add(right).division(TWO);

            assertTrue(left.compare(middle) <= 0);
            assertTrue(middle.compare(right) <= 0);

            if (raisedTo(middle, TWO).compare(number) <= 0) {

                left = middle.add(ONE);
                assert left.compare(middle) > 0;
            } else {

                right = middle.subtract(ONE);
                assert right.compare(middle) < 0;
            }
        }

        assertTrue(right.compare(number) <= 0);
        assertTrue(left.compare(right) >= 0);
        return right;
    }

    /**
     * Adds two BigIntegers and returns the result as a new value
     *
     * @param number the number we want to add to the current value
     * @return the adding result
     */
    public BigNumber add(BigNumber number) {

        assertTrue(number.compare(ZERO) >= 0);

        List<Integer> numberDigits = number.digits;

        StringBuilder resultBuilder = new StringBuilder();

        int carry = 0;
        for (int i = 0; i < numberDigits.size() || i < digits.size() || carry != 0; i++) {


            int resultDigit = carry;

            if (digits.size() > i) resultDigit += digits.get(i);
            if (numberDigits.size() > i) resultDigit += numberDigits.get(i);

            carry = resultDigit / 10;
            resultDigit %= 10;

            assert carry >= 0 && carry <= 1;
            assert resultDigit >= 0  && resultDigit <= 9;
            resultBuilder.append(resultDigit);
        }

        BigNumber result = new BigNumber(resultBuilder.reverse().toString());

        assertTrue(number.compare(result) <= 0);
        assertTrue(this.compare(result) <= 0);

        return result;
    }

    /**
     * Calculates the difference between to BigNumbers
     *
     * @param number The number we want to subtract from the current value
     * @return The subtraction result
     */
    public BigNumber subtract(BigNumber number) {

        assertTrue(number.compare(ZERO) >= 0);
        assertTrue(this.compare(ZERO) >= 0);

        if (compare(number) < 0) {

            throw new InvalidParameterException("First number smaller than second at subtract");
        }

        List<Integer> numberDigits = number.digits;

        StringBuilder resultBuilder = new StringBuilder();

        int carry = 0;
        for (int i = 0; i < digits.size(); i++) {

            int resultDigit = digits.get(i) - carry;

            if (numberDigits.size() > i) resultDigit -= numberDigits.get(i);

            if (resultDigit < 0) {

                assert resultDigit >= -10;
                carry = 1;
                resultDigit += 10;
            } else {

                carry = 0;
            }

            assert resultDigit <= 9;
            assert i >= 0 && i < digits.size();

            resultBuilder.append(resultDigit);
        }

        BigNumber result = new BigNumber(resultBuilder.reverse().toString()).removeTrailingZeros();

        assertTrue(result.compare(ZERO) >= 0);
        assertTrue(this.compare(result) >= 0);

        return result;
    }

    /**
     * Multiplies two BigNumbers
     *
     * @param number The number that will multiply the current number
     * @return The multiplication result
     */
    public BigNumber multiply(BigNumber number) {

        assertTrue(number.compare(ZERO) >= 0);
        assertTrue(this.compare(ZERO) >= 0);

        int[] result = new int[number.totalDigits + totalDigits];

        for (int index1 = 0; index1 < totalDigits; index1++) {
            for (int index2 = 0; index2 < number.totalDigits; index2++) {

                result[index1 + index2] += digits.get(index1) * number.digits.get(index2);
            }
        }

        int carry = 0;
        StringBuilder resultBuilder = new StringBuilder();
        for (int index = 0; index < totalDigits + number.totalDigits - 1 || carry != 0; index++) {

            int resultDigit = carry;

            if (index < totalDigits + number.totalDigits - 1) resultDigit += result[index];

            assert index < totalDigits + number.totalDigits - 1 || carry != 0;

            carry = resultDigit / 10;
            resultDigit %= 10;

            assert resultDigit >= 0 && resultDigit <=9;

            resultBuilder.append(resultDigit);
        }

        BigNumber resultNumber = new BigNumber(resultBuilder.reverse().toString());

        assertTrue(resultNumber.compare(number) >= 0);
        assertTrue(resultNumber.compare(this) >= 0);

        return resultNumber;
    }

    /**
     * Divides two {@link BigNumber}
     *
     * @param divisor The number that will divide the current value
     * @return the division result
     */
    public BigNumber division(BigNumber divisor) {

        assertTrue(divisor.compare(ZERO) >= 0);

        BigNumber result;

        if (compare(divisor) < 0) {

            result = ZERO;
            assertEquals(0, result.compare(ZERO));

            return result;
        }

        if (compare(divisor) == 0) {

            result = ONE;
            assertEquals(0, result.compare(ONE));
            return result;
        }

        if (divisor.compare(ZERO) == 0) {

            throw new InvalidParameterException("You can not divide by zero");
        }

        StringBuilder numberString = new StringBuilder();
        BigNumber number = ZERO;
        int index = totalDigits - 1;

        StringBuilder resultBuilder = new StringBuilder();

        while (index >= 0) {

            if (divisor.compare(number) >= 0) {

                numberString.append(digits.get(index));
                number = new BigNumber(numberString.toString());
            }

            int resultDigit = 0;
            while (divisor.compare(number) <= 0) {

                number = number.subtract(divisor);
                resultDigit++;
            }

            assert resultDigit >= 0 && resultDigit <= 9;
            resultBuilder.append(resultDigit);

            numberString = new StringBuilder();
            numberString.append(number);

            index--;
        }

        result = new BigNumber(resultBuilder.toString()).removeTrailingZeros();

        if (number.compare(ZERO) == 0) {

            assertEquals(0, this.compare(divisor.multiply(result)));
        }

        return result;
    }

    private boolean isEven() {

        assert(digits.size() > 0 && totalDigits > 0);

        return digits.get(0) % 2 == 0;
    }

    private int compare(BigNumber number) {

        assert(number.digits.size() == number.totalDigits);
        assert(digits.size() == totalDigits);

        number = number.removeTrailingZeros();

        assert(number.digits.get(number.digits.size() - 1) != 0 || number.digits.size() == 1);

        if (!number.totalDigits.equals(totalDigits)) {

            return (totalDigits < number.totalDigits ? -1 : 1);
        }

        for (int index = totalDigits - 1; index >= 0; index--) {

            if (!digits.get(index).equals(number.digits.get(index))) {

                return digits.get(index).compareTo(number.digits.get(index));
            }
        }

        return 0;
    }

    private BigNumber removeTrailingZeros() {

        assert(digits.size() == totalDigits);

        while (digits.size() > 1 && digits.get(digits.size() - 1) == 0) {

            digits.remove(digits.size() - 1);
            totalDigits--;
        }

        assert(digits.size() == totalDigits);
        return new BigNumber(toString());
    }

    @Override
    public String toString() {

        assert(digits.size() == totalDigits);

        StringBuilder builder = new StringBuilder();

        for (int index = digits.size() - 1; index >= 0; index--) {

            builder.append(digits.get(index));
        }

        return builder.toString();
    }
}
