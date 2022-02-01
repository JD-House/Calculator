package calculator;

import org.w3c.dom.events.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Calculator extends JFrame {
    final static int BUTTON_WIDTH = 75;
    final static int BUTTON_HEIGHT = 50;

    private static final int MAIN_FRAME_WIDTH = 350;
    private static final int MAIN_FRAME_HEIGHT = 600;

    private static final int DELIMITER_X = MAIN_FRAME_HEIGHT / 160 + BUTTON_WIDTH;
    private static final int DELIMITER_Y = MAIN_FRAME_HEIGHT / 160 + BUTTON_HEIGHT;


    private static final int FIRST_ROW =  175;
    private static final int SECOND_ROW =  FIRST_ROW + DELIMITER_Y;
    private static final int THIRD_ROW =  SECOND_ROW + DELIMITER_Y;
    private static final int FOURTH_ROW =  THIRD_ROW + DELIMITER_Y;
    private static final int FIFTH_ROW =  FOURTH_ROW + DELIMITER_Y;
    private static final int SIXTH_ROW =  FIFTH_ROW + DELIMITER_Y;

    private static final int FIRST_COL = 10;
    private static final int SECOND_COL = FIRST_COL + DELIMITER_X;
    private static final int THIRD_COL =  SECOND_COL + DELIMITER_X;
    private static final int FOURTH_COL = THIRD_COL + DELIMITER_X;


    enum buttonWhite {One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Zero, Dot, PlusMinus}

    private String baseCondition = null;
    private boolean isNegated = false;

    public Calculator() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setLayout(null);

        //Text fields

        //Result
        JLabel ResultLabel = new JLabel();
        ResultLabel.setName("ResultLabel");
        ResultLabel.setBounds(20,20, 300,45);
        ResultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ResultLabel.setVerticalAlignment(SwingConstants.CENTER);
        ResultLabel.setFont(new Font(ResultLabel.getFont().getName(), Font.BOLD, 40));
        add(ResultLabel);

        //Equation
        JLabel EquationLabel = new JLabel();
        EquationLabel.setName("EquationLabel");
        EquationLabel.setBounds(20,75, 300,45);
        EquationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        EquationLabel.setVerticalAlignment(SwingConstants.CENTER);
        EquationLabel.setFont(new Font(EquationLabel.getFont().getName(), Font.BOLD, 15));
        EquationLabel.setForeground(Color.green.darker());
        add(EquationLabel);

                                                    //Buttons

                                                    //Operators
        //Delete
        JButton deleteButton = addButton("Del", "Delete", FOURTH_COL, FIRST_ROW);
        deleteButton.addActionListener(e -> EquationLabel
                .setText(EquationLabel
                        .getText()
                        .substring(0, EquationLabel
                                .getText()
                                .length() - (EquationLabel
                                .getText()
                                .length() > 0 ? 1 : 0))));


        //Clear
        JButton clearButton = addButton("C", "Clear", THIRD_COL, FIRST_ROW);
        clearButton.addActionListener (new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                EquationLabel.setText("");
                ResultLabel.setText("");
                isNegated = false;
                baseCondition = null;
            }
        });

        //CE
        JButton ceButton = addButton("CE", "CE", SECOND_COL, FIRST_ROW);
        ceButton.addActionListener(e -> EquationLabel
                .setText(EquationLabel
                        .getText()
                        .substring(0, EquationLabel
                                .getText()
                                .length() - (EquationLabel
                                .getText()
                                .length() > 0 ? 1 : 0))));




        //Division
        JButton divideButton = addButton("\u00F7", "Divide", FOURTH_COL, SECOND_ROW);
        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(EquationLabel.getText().length() == 0) {
                    return;
                } else {
                    EquationLabel.setText(EquationLabel.getText() + divideButton.getText());
                    EquationLabel.setText(styleChecker(EquationLabel.getText()));
                }
            }
        });

        //Multiply
        JButton multiplyButton = addButton("\u00D7", "Multiply", FOURTH_COL, THIRD_ROW);
        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(EquationLabel.getText().length() == 0) {
                    return;
                } else {
                    EquationLabel.setText(EquationLabel.getText() + multiplyButton.getText());
                    EquationLabel.setText(styleChecker(EquationLabel.getText()));
                }
            }
        });

        //Addition
        JButton addButton = addButton("\u002B", "Add", FOURTH_COL, FIFTH_ROW);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(EquationLabel.getText().length() == 0) {
                    return;
                } else {
                    EquationLabel.setText(EquationLabel.getText() + addButton.getText());
                    EquationLabel.setText(styleChecker(EquationLabel.getText()));
                }
            }
        });

        //Subtraction
        JButton subtractButton = addButton("-", "Subtract", FOURTH_COL, FOURTH_ROW);
        subtractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(EquationLabel.getText().length() == 0) {
                    return;
                } else {
                    EquationLabel.setText(EquationLabel.getText() + subtractButton.getText());
                    EquationLabel.setText(styleChecker(EquationLabel.getText()));
                }
            }
        });

        //Sqrt
        JButton squareRootButton = addButton("\u221A", "SquareRoot", THIRD_COL, SECOND_ROW);
        squareRootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    EquationLabel.setText(EquationLabel.getText() + "\u221A(");
            }
        });

        //Parentheses
        JButton parenthesesButton = addButton("( )", "Parentheses", FIRST_COL, FIRST_ROW);
        parenthesesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long leftBracketCounter = EquationLabel.getText().chars().filter(ch -> ch == '(').count();
                long rightBracketCounter = EquationLabel.getText().chars().filter(ch -> ch == ')').count();

                if (leftBracketCounter <= rightBracketCounter ||
                        EquationLabel.getText().length() > 0 &&
                                EquationLabel.getText().charAt(EquationLabel.getText().length() - 1) == '(') {
                    if (EquationLabel.getText().length() > 0) {
                        if (Parser.isNumber(EquationLabel.getText().substring(EquationLabel.getText().length() - 1))) {
                            return;
                        }
                    }
                    EquationLabel.setText(EquationLabel.getText() + "(");
                } else {
                    EquationLabel.setText(EquationLabel.getText() + ")");
                }
            }
        });

        //PowerTwo
        JButton powerTwoButton = addButton("X\u00B2", "PowerTwo", FIRST_COL, SECOND_ROW);
        powerTwoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(EquationLabel.getText().length() == 0) {
                    return;
                } else {
                    EquationLabel.setText(EquationLabel.getText() + "^(2)");
                    EquationLabel.setText(styleChecker(EquationLabel.getText()));
                }
            }
        });

        //PowerY
        JButton powerYButton = addButton("X\u02B8", "PowerY", SECOND_COL, SECOND_ROW);
        powerYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(EquationLabel.getText().length() == 0) {
                    return;
                } else {
                    EquationLabel.setText(EquationLabel.getText() + "^(");
                }
            }
        });

        //PlusMinus
        JButton plusMinusButton = addButton("\u00B1", "PlusMinus", FIRST_COL, SIXTH_ROW);
        plusMinusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNegated) {
                    EquationLabel.setText(baseCondition);
                    baseCondition = null;
                    isNegated = false;
                    return;
                }
                baseCondition = EquationLabel.getText();
                isNegated = true;

                StringBuilder equation = new StringBuilder(EquationLabel.getText());
                if (equation.length() == 0) {
                    EquationLabel.setText("(-");
                } else {
                    if (Character.isDigit(equation.charAt(equation.length() - 1))) {
                        equation.insert(equation.length() - 1, "(-");
                    } else if (equation.charAt(equation.length() - 1) == (')')){
                        int rightParenthesesCounter = 1;
                        int leftParenthesesCounter = 0;
                        int pointer = equation.length() - 2;
                        while (leftParenthesesCounter < rightParenthesesCounter) {
                            rightParenthesesCounter += equation.charAt(pointer) == (')') ? 1 : 0;
                            leftParenthesesCounter += equation.charAt(pointer) == ('(') ? 1 : 0;
                            --pointer;
                        }
                        if (pointer < 0) {
                            equation.insert(0, "(-");
                            equation.append(")");
                        } else {
                            equation.insert(pointer + 1, "(-");
                        }
                    }
                    EquationLabel.setText(equation.toString());
                }
            }
        });

        //Equals
        JButton equalsButton = addButton("=", "Equals", FOURTH_COL, SIXTH_ROW);
        equalsButton.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                long leftBracketCounter = EquationLabel.getText().chars().filter(ch -> ch == '(').count();
                long rightBracketCounter = EquationLabel.getText().chars().filter(ch -> ch == ')').count();

                String answer = null;
                if (EquationLabel.getText().length() > 0) {
                    try {
                        if(leftBracketCounter != rightBracketCounter) {
                            EquationLabel.setForeground(Color.red.darker());
                            return;
                        }
                        answer = solving(Parser.parseInput(EquationLabel.getText()));

                        if (answer.equals("\u221e") ||
                                Parser.isOperator(String.valueOf(EquationLabel.getText()
                                        .charAt(EquationLabel.getText().length() - 1)))) {
                            EquationLabel.setForeground(Color.red.darker());
                            return;
                        }
                    } catch (ArithmeticException e) {
                        EquationLabel.setForeground(Color.red.darker());
                    }
                }
                EquationLabel.setForeground(Color.green.darker());
                ResultLabel.setText(answer);

            }
        });




                                                    //Numbers

        //One
        JButton buttonOne = addButton("1", "One", FIRST_COL, FIFTH_ROW);
        buttonOne.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonOne.getText()));

        //Two
        JButton buttonTwo = addButton("2", "Two", SECOND_COL, FIFTH_ROW);
        buttonTwo.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonTwo.getText()));

        //Three
        JButton buttonThree = addButton("3", "Three", THIRD_COL, FIFTH_ROW);
        buttonThree.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonThree.getText()));

        //Four
        JButton buttonFour = addButton("4", "Four", FIRST_COL, FOURTH_ROW);
        buttonFour.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonFour.getText()));

        //Five
        JButton buttonFive = addButton("5", "Five", SECOND_COL, FOURTH_ROW);
        buttonFive.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonFive.getText()));

        //Six
        JButton buttonSix = addButton("6", "Six", THIRD_COL, FOURTH_ROW);
        buttonSix.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonSix.getText()));

        //Seven
        JButton buttonSeven = addButton("7", "Seven", FIRST_COL, THIRD_ROW);
        buttonSeven.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonSeven.getText()));

        //Eight
        JButton buttonEight = addButton("8", "Eight", SECOND_COL, THIRD_ROW);
        buttonEight.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonEight.getText()));

        //Nine
        JButton buttonNine = addButton("9", "Nine", THIRD_COL, THIRD_ROW);
        buttonNine.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonNine.getText()));

        //Zero
        JButton buttonZero = addButton("0", "Zero", SECOND_COL, SIXTH_ROW);
        buttonZero.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + buttonZero.getText()));

        //Dot
        JButton dotButton = addButton(".", "Dot", THIRD_COL, SIXTH_ROW);
        dotButton.addActionListener(e -> EquationLabel.setText(EquationLabel.getText() + dotButton.getText()));

        setVisible(true);

    }

    private String styleChecker(String input) {

        input = input.replaceAll("\\u002B\\.","\u002B0." );
        input = input.replaceAll("\\u00D7\\.","\u00D70." );
        input = input.replaceAll("\\u00F7\\.","\u00F70." );
        input = input.replaceAll("-\\.","-0." );

        if (input.charAt(0) == '.') {
            input = input.replace(".","0.");
        }


        input = input.replaceAll("\\.\\u002B",".0\u002B" );
        input = input.replaceAll("\\.\\u00D7",".0\u00D7" );
        input = input.replaceAll("\\.\\u00F7",".0\u00F7" );
        input = input.replaceAll("\\.-",".0-" );

        StringBuilder inputBuilder = new StringBuilder(input);

        if (inputBuilder.length() > 2) {
            String firstOperator = String.valueOf(inputBuilder.charAt(inputBuilder.length() - 2));
            if (Parser.isOperator(firstOperator)) {
                inputBuilder.deleteCharAt(inputBuilder.length() - 2);
            }
        }

        return inputBuilder.toString();
    }

    private JButton addButton(String text, String name, int col, int row) {
        JButton button = new JButton(text);
        final Color buttonColor = Color.WHITE;
        final Color operatorColor = Color.gray.brighter();

        button.setName(name);
        button.setBounds(col, row, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setFocusPainted(false);

        button.setBackground(operatorColor);

        for (buttonWhite buttonName : buttonWhite.values()) {
            if (buttonName.name().equals(name)) {
                button.setBackground(buttonColor);
                break;
            }
        }
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, 15));

        add(button);

        return button;
    }

    static String solving(Queue<String> expression) {

        ArrayDeque<Double> result = new ArrayDeque<>();
        double a;
        double b;
        while (!expression.isEmpty()) {
            for (String term : expression) {
                expression.poll();
                if(Parser.isNumber(term)) {
                    result.push(Double.parseDouble(term));
                } else if(Parser.operatorParser(term) == Parser.operators.SQRT) {
                    a = result.pop();
                    result.push(Math.sqrt(a));
                } else if (result.size() > 1){
                    b = result.pop();
                    a = result.pop();
                    switch (Parser.operatorParser(term)){
                        case MULTIPLICATION : { result.push(a * b); break; }
                        case DIVISION : { result.push(a / b); break; }
                        case SUBTRACTION : { result.push (a - b); break; }
                        case ADDITION : { result.push(a + b); break; }
                        case POWER : { result.push(Math.pow(a, b)); break; }
                    };
                }
            }
        }
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat out = new DecimalFormat("#.###########", otherSymbols);
        return out.format(result.getLast());
    }
}
