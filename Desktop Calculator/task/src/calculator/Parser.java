package calculator;

import java.util.*;

public abstract class Parser {

    enum operators {SUBTRACTION, ADDITION, MULTIPLICATION, DIVISION, SQRT, POWER}


    public static Queue<String> parseInput(String input) {
        Stack<String> stackOperations = new Stack<>();
        ArrayDeque<String> postfixExpression = new ArrayDeque<>();
        List<String> expression = new ArrayList<>();

        input = input.replace("(-", "(0-");
        if (input.charAt(0) == '-') {
            input = "0" + input;
        }

        StringBuilder termNumber = new StringBuilder();
        char termTemp;

        for (int i = 0; i < input.length(); ++i) {
            termTemp = input.charAt(i);
            if (Character.isDigit(termTemp) || termTemp == '.') {
                termNumber.append(termTemp);
            } else {
                if (termNumber.length() > 0) {
                    if (termNumber.toString().matches("[.][\\d+]")) {
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
        if (termNumber.length() > 0) {
            expression.add(termNumber.toString());
        }

        //Shunting-yard

        for (int i = 0; i < expression.size(); ++i) {
            String term = expression.get(i);
            if(isNumber(term)) {
                postfixExpression.add(term);
            }else if (isOperator(term)) {
                while (!stackOperations.isEmpty()
                        && isOperator(stackOperations.lastElement())
                        && getPrecedence(stackOperations.lastElement()) >= getPrecedence(term)) {
                    postfixExpression.add(stackOperations.pop());
                }
                if (i < expression.size() - 2 && expression.get(i + 1).equals(")")) {
                    postfixExpression.add(expression.remove(i + 2));
                }
                if (expression.size() > 4 && expression.get(i -1 ).equals("(")) {
                    postfixExpression.add(expression.remove(i - 4));
                    stackOperations.push(expression.remove(i - 3));
                    postfixExpression.add(expression.remove(i - 2));
                }
                stackOperations.push(term);
            }
            else if (isFunction(term)) {
                stackOperations.push(term);
            }
            else if (isOpenBracket(term)) {
                stackOperations.push(term);
            } else if (isCloseBracket(term)) {

                while (!stackOperations.empty()
                        && !isOpenBracket(stackOperations.lastElement())) {
                    postfixExpression.add(stackOperations.pop());
                }
                stackOperations.pop();
                if (!stackOperations.empty()
                        && isFunction(stackOperations.lastElement())) {
                    postfixExpression.add(stackOperations.pop());
                }
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
            case ("^") : {
                operation = operators.POWER;
                break;
            }
            case ("\u221A") : {
                operation = operators.SQRT;
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
        return token.matches("[\u002B\u00D7\u00F7^-]");
    }

    private static boolean isFunction(String token) {
        if (token.matches("[\\u221A^]")) {
            return true;
        }
        return false;
    }

    private static boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    private static boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    private static byte getPrecedence(String token) {
        if (token.equals("\u002B") || token.equals("-")) {
            return 1;
        }
        return 2;
    }


}
