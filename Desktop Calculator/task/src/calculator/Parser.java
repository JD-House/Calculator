package calculator;

import java.util.*;

public abstract class Parser {

    enum operators {SUBTRACTION, ADDITION, MULTIPLICATION, DIVISION}


    public static Queue<String> parseInput(String input) {
        Stack<String> stackOperations = new Stack<>();
        ArrayDeque<String> postfixExpression = new ArrayDeque<>();
        List<String> expression = new ArrayList<>();

        input.replace("(-", "(0-");
        if (input.charAt(0) == '-') {
            input = "0" + input;
        }

        StringBuilder termNumber = new StringBuilder();
        char termTemp;

        for(int i = 0; i < input.length(); ++i) {
            termTemp = input.charAt(i);
            if (Character.isDigit(termTemp) || termTemp == '.') {
                termNumber.append(termTemp);
            } else {
                if(termNumber.length() > 0) {
                    if(termNumber.toString().matches("[.][\\d+]")) {
                        termNumber = new StringBuilder("0" + termNumber.toString());
                    }
                    if (termNumber.toString().matches("[\\d=][.]")) {
                        termNumber.append("0");
                    }
                    expression.add(termNumber.toString());
                    termNumber.setLength(0);
                }
                expression.add(Character.toString(termTemp));
            }
        }
        expression.add(termNumber.toString());

        //Shunting-yard

        for (String term : expression) {
            if(isNumber(term)) {
                postfixExpression.add(term);
            }else if (isOperator(term)) {
                while (!stackOperations.isEmpty()
                        && isOperator(stackOperations.lastElement())
                        && getPrecedence(stackOperations.lastElement()) >= getPrecedence(term)) {
                    postfixExpression.add(stackOperations.pop());
                }
                stackOperations.push(term);
            }
        }
        while (!stackOperations.isEmpty()) {
            postfixExpression.add(stackOperations.pop());
        }

        return postfixExpression;
    }

    public static operators operatorParser(String operator) {
        operators operation;
        switch (operator) {
            case ("-"): {
                operation = operators.SUBTRACTION;
                break;
            }
            case ("\u002B"): {
                operation = operators.ADDITION;
                break;
            }
            case ("\u00D7"): {
                operation = operators.MULTIPLICATION;
                break;
            }
            case ("\u00F7"): {
                operation = operators.DIVISION;
                break;
            }
            default: operation = null;
        }
        return operation;
    }

    public static boolean isNumber(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(String token) {
        return token.matches("[\u002B\u00D7\u00F7-]");
    }

    private static byte getPrecedence(String token) {
        if (token.equals("\u002B") || token.equals("-")) {
            return 1;
        }
        return 2;
    }


}
