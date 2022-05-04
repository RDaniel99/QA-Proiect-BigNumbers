package com.company;

import java.util.*;

import static com.company.BigNumber.ZERO;
import static com.company.OperatorsEnum.*;

public class Parser {

    private static BigNumber applyOp(String operator, BigNumber secondNumber, BigNumber firstNumber) {

        switch (operator) {
            case ADD:

                return firstNumber.add(secondNumber);
            case MINUS:

                return firstNumber.subtract(secondNumber);
            case MULTIPLY:

                return firstNumber.multiply(secondNumber);
            case DIVISION:

                return firstNumber.division(secondNumber);
            case POW:

                return BigNumber.raisedTo(firstNumber, secondNumber);
        }

        return ZERO;
    }

    private static boolean hasPrecedence(String firstOperator, String secondOperator) {

        if (Objects.equals(secondOperator, "(") || Objects.equals(secondOperator, ")"))

            return false;

        boolean highestPriorityFirst = Objects.equals(firstOperator, POW) || Objects.equals(firstOperator, SQRT);
        boolean mediumPriorityFirst = Objects.equals(firstOperator, MULTIPLY) || Objects.equals(firstOperator, DIVISION);

        boolean lowestPrioritySecond = Objects.equals(secondOperator, ADD) || Objects.equals(secondOperator, MINUS);
        boolean mediumPrioritySecond = Objects.equals(secondOperator, MULTIPLY) || Objects.equals(secondOperator, DIVISION);


        if (lowestPrioritySecond && mediumPriorityFirst)

            return false;

        if (lowestPrioritySecond && highestPriorityFirst)

            return false;

        if (mediumPrioritySecond && highestPriorityFirst)

            return false;

        return true;
    }

    public Set<Character> getVariables(String expression) {

        char[] tokens = expression.toCharArray();

        Set<Character> values = new TreeSet<>();

        if (isLiteral(tokens[0]) && !isLiteral(tokens[1])) {

            values.add(tokens[0]);
        }

        if (isLiteral(tokens[tokens.length - 1]) && !isLiteral(tokens[tokens.length - 2])) {

            values.add(tokens[tokens.length - 1]);
        }

        for (int i = 1; i < tokens.length - 1; i++) {

            if (isLiteral(tokens[i]) && (!isLiteral(tokens[i - 1]) && !isLiteral(tokens[i + 1]))) {

                values.add(tokens[i]);
            }
        }

        return values;
    }

    private boolean isLiteral(Character character) {

        return character >= 'a' && character <= 'z';
    }

    public BigNumber execute(String expression, Map<Character, BigNumber> values) {

        char[] tokens = expression.toCharArray();

        Stack<BigNumber> variables = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i] == ' ')
                continue;

            if (isLiteral(tokens[i])) {

                variables.push(values.get(tokens[i]));
            } else if (tokens[i] == '(') {

                operators.push(String.valueOf(tokens[i]));
            } else if (tokens[i] == ')') {

                while (!Objects.equals(operators.peek(), "(")) {

                    if (!Objects.equals(operators.peek(), SQRT)) {

                        variables.push(applyOp(operators.pop(), variables.pop(), variables.pop()));
                    } else {

                        operators.pop();
                        variables.push(applyOp(variables.pop()));
                    }

                }

                operators.pop();
            } else if (isOperator(String.valueOf(tokens[i]))) {

                while (!operators.empty() && hasPrecedence(String.valueOf(tokens[i]), operators.peek())) {

                    if (String.valueOf(operators.peek()).equals(SQRT)) {

                        operators.pop();
                        variables.push(applyOp(variables.pop()));
                    } else {

                        variables.push(applyOp(operators.pop(), variables.pop(), variables.pop()));
                    }
                }

                operators.push(String.valueOf(tokens[i]));
            }
        }

        while (!operators.empty())

            variables.push(applyOp(operators.pop(), variables.pop(), variables.pop()));

        return variables.pop();
    }

    private BigNumber applyOp(BigNumber number) {

        return BigNumber.squareRoot(number);
    }

    private boolean isOperator(String token) {

        return Objects.equals(token, ADD) ||
                Objects.equals(token, MINUS) ||
                Objects.equals(token, MULTIPLY) ||
                Objects.equals(token, DIVISION) ||
                Objects.equals(token, POW) ||
                Objects.equals(token, SQRT);
    }
}
