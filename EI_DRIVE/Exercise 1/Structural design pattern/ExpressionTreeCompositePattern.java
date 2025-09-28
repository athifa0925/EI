import java.util.Scanner;

interface ExpressionComponent {
    double evaluate();
    String toString();
}

class Operand implements ExpressionComponent {
    private double value;

    public Operand(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

class Operator implements ExpressionComponent {
    private char operator;
    private ExpressionComponent left;
    private ExpressionComponent right;

    public Operator(char operator, ExpressionComponent left, ExpressionComponent right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        switch (operator) {
            case '+':
                return left.evaluate() + right.evaluate();
            case '-':
                return left.evaluate() - right.evaluate();
            case '*':
                return left.evaluate() * right.evaluate();
            case '/':
                double rightValue = right.evaluate();
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return left.evaluate() / rightValue;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}

public class ExpressionTreeCompositePattern {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Building expression tree for: (5 + 3) * 2");
        ExpressionComponent five = new Operand(5);
        ExpressionComponent three = new Operand(3);
        ExpressionComponent plus = new Operator('+', five, three);
        ExpressionComponent two = new Operand(2);
        ExpressionComponent multiply = new Operator('*', plus, two);

        System.out.println("Expression: " + multiply.toString());
        System.out.println("Result: " + multiply.evaluate());
        scanner.close();
    }
}