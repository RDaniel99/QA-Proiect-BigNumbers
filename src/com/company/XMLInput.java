package com.company;

import java.util.Map;

public class XMLInput {

    private final String expression;
    private final Map<Character, BigNumber> values;

    public XMLInput(String expression, Map<Character, BigNumber> values) {

        this.expression = expression;
        this.values = values;
    }

    public String getExpression() {
        return expression;
    }

    public Map<Character, BigNumber> getValues() {
        return values;
    }
}
