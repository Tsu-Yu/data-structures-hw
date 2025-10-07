package ds;

import java.util.NoSuchElementException;

public class ExprEvaluator {
    public int eval(String expr){
        if (expr == null) throw new IllegalArgumentException("null expression");
        
        Stack<Long> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        int i = 0;
        final int n = expr.length();

        while(i < n){
            char c = expr.charAt(i);

            // ignore whitespace
            if(Character.isWhitespace(c)){ i++; continue; }

            if(c == '('){
                ops.push('(');
                i++;
                continue;
            }

            if(c == ')'){
                while(!ops.isEmpty() && ops.peek() != '('){
                    applyTopOperator(values, ops);
                }
                if(ops.isEmpty()){
                    throw new IllegalArgumentException("Mismatched parenthesis");
                }
                ops.pop();
                i++;
                continue;
            }

            if(isNumberStart(expr, i)){
                i = readNumberPush(expr, i, values);
                continue;
            }

            if(isOperator(c)){
                if(c == '-' && looksLikeUnaryMinusBeforeParen(expr, i)){
                    values.push(0L);
                }

                while(!ops.isEmpty() && ops.peek() != '(' && precedence(ops.peek()) >= precedence(c)){
                    applyTopOperator(values, ops);
                }

                ops.push(c);
                i++;
                continue;
            }
            throw new IllegalArgumentException("Invalid token at index " + i + ": '" + c + "'");
        }

         while (!ops.isEmpty()) {
            char op = ops.peek();
            if (op == '(') throw new IllegalArgumentException("Mismatched parenthesis");
            applyTopOperator(values, ops);
        }

        if (values.size() != 1) {
            throw new IllegalArgumentException("Invalid expression in index: " + i);
        }

        long ans = values.pop();
        if (ans < Integer.MIN_VALUE || ans > Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow");
        }

        return (int) ans;
    }
    


    
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op){
        if(op == '*' || op == '/') return 2;
        if(op == '+' || op == '-') return 1;
        return 0;
    }

    private void applyTopOperator(Stack<Long> values, Stack<Character> ops){
        try{
            char op = ops.pop();
            long b = values.pop();
            long a = values.pop();
            if(op == '/' && b ==0) throw new ArithmeticException("Division by zero");

            values.push(compute(a, op, b));
        } catch (NoSuchElementException e){
            throw new IllegalArgumentException("Invalid expression (insufficient operands)");
        }
    }

    private Long compute(long a, char op, long b){
        return switch(op){
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        };
    }

    private boolean isNumberStart(String s, int i) {
        char c = s.charAt(i);
        if(Character.isDigit(c)) return true;

        if(c == '-'){
            int k = i + 1;
            while (k < s.length() && Character.isWhitespace(s.charAt(k))) k++;
            if(k >= s.length() || !Character.isDigit(s.charAt(k))) return false;

            int j = i - 1;
            while(j >= 0 && Character.isWhitespace(s.charAt(j))) j--;
            if(j < 0) return true;
            char prev = s.charAt(j);
            if (prev == '(' || isOperator(prev)) return true;
        }
        return false;
    }

    private boolean looksLikeUnaryMinusBeforeParen(String s, int i){
         int n = s.length();

        int k = i + 1;
        while (k < n && Character.isWhitespace(s.charAt(k))) k++;
        if (k >= n || s.charAt(k) != '(') return false;

        int j = i - 1;
        while (j >= 0 && Character.isWhitespace(s.charAt(j))) j--;
        if (j < 0) return true; 
        char prev = s.charAt(j);
        return prev == '(' || isOperator(prev);
    }

    private int readNumberPush(String s, int i, Stack<Long> values){
        int n = s.length();
        int sign = 1;

        if(s.charAt(i) == '-'){
            sign = -1;
            i++;
        } else if (s.charAt(i) == '+'){
            i++;
        }

        if(i >= n || !Character.isDigit(s.charAt(i))) {
            throw new IllegalArgumentException("Expected digit after sign at index:" + i);
        }

        long val = 0;
        while(i < n && Character.isDigit(s.charAt(i))){
            val = val * 10 + (s.charAt(i) - '0');
            i++;
        }

        values.push(val * sign);
        return i;
    }


}
