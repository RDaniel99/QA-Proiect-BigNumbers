package com.company;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BigNumber {

    public static final BigNumber ZERO = new BigNumber("0");
    private static final BigNumber ONE = new BigNumber("1");
    private static final BigNumber TWO = new BigNumber("2");

    private final Integer totalDigits;
    private final List<Integer> digits;

    public BigNumber(String number) {

        digits = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(number);
        builder.reverse();
        number = builder.toString();

        digits.addAll(number.chars()
                .mapToObj(c -> Integer.parseInt(String.valueOf((char) c)))
                .collect(Collectors.toList()));

        totalDigits = digits.size();
    }

    public BigNumber add(BigNumber number) {

        List<Integer> numberDigits = number.digits;

        StringBuilder resultBuilder = new StringBuilder();

        int carry = 0;
        for(int i = 0; i < numberDigits.size() || i < digits.size() || carry != 0; i++) {

            int resultDigit = carry;

            if(digits.size() > i) resultDigit += digits.get(i);
            if(numberDigits.size() > i) resultDigit += numberDigits.get(i);

            carry = resultDigit / 10;
            resultDigit %= 10;

            resultBuilder.append(resultDigit);
        }

        return new BigNumber(resultBuilder.reverse().toString());
    }

    public BigNumber subtract(BigNumber number) {

        if(compare(number) < 0) {

            throw new InvalidParameterException("First number smaller than second at subtract");
        }

        List<Integer> numberDigits = number.digits;

        StringBuilder resultBuilder = new StringBuilder();

        int carry = 0;
        for(int i = 0; i < digits.size(); i++) {

            int resultDigit = digits.get(i)  - carry;

            if(numberDigits.size() > i) resultDigit -= numberDigits.get(i);

            if(resultDigit < 0) {

                carry = 1;
                resultDigit += 10;
            }
            else {

                carry = 0;
            }

            resultBuilder.append(resultDigit);
        }

        return new BigNumber(resultBuilder.reverse().toString()).removeTrailingZeros();
    }

    public BigNumber multiply(BigNumber number) {

        int[] result = new int[number.totalDigits + totalDigits];

        for(int index1 = 0; index1 < totalDigits; index1++) {
            for(int index2 = 0; index2 < number.totalDigits; index2++) {

                result[index1 + index2] += digits.get(index1) * number.digits.get(index2);
            }
        }

        int carry = 0;
        StringBuilder resultBuilder = new StringBuilder();
        for(int index = 0; index < totalDigits + number.totalDigits - 1 || carry != 0; index++) {

            int resultDigit = carry;

            if(index < totalDigits + number.totalDigits - 1) resultDigit += result[index];

            carry = resultDigit / 10;
            resultDigit %= 10;

            resultBuilder.append(resultDigit);
        }

        return new BigNumber(resultBuilder.reverse().toString());
    }

    public BigNumber division(BigNumber divisor) {

        if(compare(divisor) < 0) {

            return ZERO;
        }

        if(compare(divisor) == 0) {

            return ONE;
        }

        StringBuilder numberString = new StringBuilder();
        BigNumber number = ZERO;
        int index = totalDigits - 1;

        StringBuilder resultBuilder = new StringBuilder();

        while(index >= 0) {

            if(divisor.compare(number) >= 0) {

                numberString.append(digits.get(index));
                number = new BigNumber(numberString.toString());
            }

            int resultDigit = 0;
            while (divisor.compare(number) <= 0) {

                number = number.subtract(divisor);
                resultDigit++;
            }

            resultBuilder.append(resultDigit);

            numberString = new StringBuilder();
            numberString.append(number);

            index--;
        }

        return new BigNumber(resultBuilder.toString()).removeTrailingZeros();
    }

    public static BigNumber raisedTo(BigNumber base, BigNumber exponent) {

        if(exponent.compare(ZERO) == 0) {

            return ONE;
        }

        BigNumber result = raisedTo(base, exponent.division(TWO));

        if(exponent.isEven()) {

            return result.multiply(result);
        }

        return result.multiply(result).multiply(base);
    }

    public static BigNumber squareRoot(BigNumber number) {

        BigNumber left = ZERO;
        BigNumber right = number;

        while (left.compare(right) <= 0) {


            BigNumber middle = left.add(right).division(TWO);

            if(raisedTo(middle, TWO).compare(number) <= 0) {

                left = middle.add(ONE);
            }
            else {

                right = middle.subtract(ONE);
            }
        }

        return right;
    }

    private boolean isEven() {

        return digits.get(0) % 2 == 0;
    }

    private int compare(BigNumber number) {

        number = number.removeTrailingZeros();

        if(!number.totalDigits.equals(totalDigits)) {

            return (totalDigits < number.totalDigits ? -1 : 1);
        }

        for(int index = totalDigits - 1; index >= 0; index--) {

            if(!digits.get(index).equals(number.digits.get(index))) {

                return digits.get(index).compareTo(number.digits.get(index));
            }
        }

        return 0;
    }

    private BigNumber removeTrailingZeros() {

        while(digits.size() > 1 && digits.get(digits.size() - 1) == 0) {

            digits.remove(digits.size() - 1);
        }

        return new BigNumber(toString());
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for(int index = digits.size() - 1; index >= 0; index--) {

            builder.append(digits.get(index));
        }

        return builder.toString();
    }
}
