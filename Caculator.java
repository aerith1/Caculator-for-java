import java.util.*;
import java.util.Scanner;
public class Caculator {
    public static void main(String[] args)throws java.io.IOException{
        java.io.File Testfile = new java.io.File("./Test.txt");
        java.io.PrintWriter output = new java.io.PrintWriter("./Result.txt");
        Scanner input = new Scanner(Testfile);
        while(input.hasNext()){
            String expression = input.nextLine();
            String[] a = new String[expression.length()];
            if(a.length == 0){
                continue;
            }
            try{
                a = getExpression(expression);
                List<String> b = new ArrayList<String>();
                b = caculatorExpression(a);
                output.println(parseExpression(b));
            }catch(IllegalArgumentException ex){
                output.println("Invalid Operator");
            }
        }
        System.out.println("finish.");
        output.close();
        input.close();
    }

    public static List<String> caculatorExpression(String[] expression){
        List<String> operandList = new ArrayList<String>();//存放数字
        Stack<String> operatorStack = new Stack<>(); //存放表达式
        for(int i = 0; i < expression.length; i++){
            //System.out.println(expression[i]);
            if(Character.isDigit(expression[i].charAt(0)))
            {
                operandList.add(expression[i]);
            }
            else if(expression[i].equals("(")){
                operatorStack.push(expression[i]);
            }
            else if(expression[i].equals(")")){
                while(!operatorStack.peek().equals("(")){
                    operandList.add(operatorStack.pop());
                }
                operatorStack.pop();
            }
            else if(expression[i].charAt(0) == '-' && Character.isDigit(expression[i].charAt(1))){
                operandList.add(expression[i]);
            }
            else {
                while(operatorStack.size() != 0 && (priority(operatorStack.peek()) >= priority(expression[i]))){
                    operandList.add(operatorStack.pop());
                }
                operatorStack.push(expression[i]);
            }
        }
        while(operatorStack.size() != 0){
            operandList.add(operatorStack.pop());
        }
        // System.out.println(operandList);
        return operandList;
    }

    public static String parseExpression(List<String> exp){
        // System.out.println(exp);
        String result;
        Stack<String> st = new Stack<String>();
        try{
            for(int i = 0; i < exp.size(); i++){
                if(exp.get(i).equals("+") || exp.get(i).equals("-") || exp.get(i).equals("*") || exp.get(i).equals("/")){
                    String n1 = st.pop();
                    String n2 = st.pop();
                    if(isInt(n1) && isInt(n2)){
                        Integer Intn1, Intn2;
                        Intn1 = Integer.valueOf(n1);
                        Intn2 = Integer.valueOf(n2);
                        st.push(String.valueOf(switchOperation(Intn1, Intn2, exp.get(i))));
                    }
                    else if(!isInt(n1) && !isInt(n2)){
                        Double Doublen1,Doublen2;
                        Doublen1 = Double.valueOf(n1);
                        Doublen2 = Double.valueOf(n2);
                        st.push(String.valueOf(switchOperation(Doublen1, Doublen2, exp.get(i))));
                    }
                    else if(isInt(n2) == false){
                        Integer Intn1;
                        Double Doublen2;
                        Intn1 = Integer.valueOf(n1);
                        Doublen2 = Double.valueOf(n2);
                        st.push(String.valueOf(switchOperation(Intn1, Doublen2, exp.get(i))));
                    }
                    else if(isInt(n1) == false){
                        Double Doublen1;
                        Integer Intn2;
                        Doublen1 = Double.valueOf(n1);
                        Intn2 = Integer.valueOf(n2);
                        st.push(String.valueOf(switchOperation(Doublen1, Intn2, exp.get(i))));
                    }
                }
                else
                    st.push(exp.get(i));
            }
            result = st.pop();
            // System.out.println(result);
            return result;
        }catch(ArithmeticException ex){
            return "Invalid Expression";
        }
    }

    public static String[] getExpression(String expression){
        expression = expression.replace(" ", "");
        List<String> exp = new ArrayList<String>();
        int i = 0;
        while(true){
            if( i >= expression.length())
                break;
            char c1 = expression.charAt(i);
            if(Character.isDigit(c1)){
                StringBuilder s = new StringBuilder();
                for(int j = i; j < expression.length(); j++){
                    char c2 = expression.charAt(j);
                    if(Character.isDigit(c2) || c2 == '.'){
                        s.append(c2);
                        i++;
                    }else{
                        break;
                    }
                }
                exp.add(s.toString());
            }

            if( i >= expression.length())
                break;

            c1 = expression.charAt(i);
            if(c1 == '-'){
                if( i > 0 && (Character.isDigit(expression.charAt(i - 1)) || expression.charAt( i - 1 ) == ')')){
                    exp.add(String.valueOf(c1));
                    i++;
            }else{
                StringBuilder s = new StringBuilder();
                s.append(c1);
                i++;
                for(int j = i; j < expression.length(); j++){
                    char c2 =expression.charAt(j);
                    if(Character.isDigit(c2) || c2 == '.'){
                        s.append(c2);
                        i++;
                    }else{
                        break;
                    }
                }
                exp.add(s.toString());
            }
        }else if(c1 == '(' || c1 == ')' || c1 == '+' || c1 == '*' || c1 == '/'){
            exp.add(String.valueOf(String.valueOf(expression.charAt(i))));
            i++;
        }else{
            throw new IllegalArgumentException();
        }
        }
        return exp.toArray(new String[exp.size()]);
    }

    public static int priority(String s){
        if(s.equals("*") || s.equals("/"))
            return 1;
        else if(s.equals("+") || s.equals("-"))
            return 0;
        else
            return -1;
    }

    public static boolean isInt(String n){
        for(int i = 0; i < n.length(); i++){
            if(n.charAt(i) == '.')
                return false;
        }
        return true;
    }

    public static int switchOperation(int n1, int n2, String op){
        int res = 0;
        if(n1 == 0)
            throw new ArithmeticException();
        if(op.equals("+"))
            res = n2 + n1;
        else if(op.equals("-"))
            res = n2 - n1;
        else if(op.equals("*"))
            res = n2 * n1;
        else if(op.equals("/"))
            return n2 / n1;
        return res;
    }

    public static double switchOperation(int n1, double n2, String op){
        double res = 0;
        if(n1 == 0)
            throw new ArithmeticException();
        if(op.equals("+"))
            res = n2 + n1;
        else if(op.equals("-"))
            res = n2 - n1;
        else if(op.equals("*"))
            res = n2 * n1;
        else if(op.equals("/"))
            return n2 / n1;
        return res;
    }

    public static double switchOperation(double n1, int n2, String op){
        double res = 0;
        if(n1 == 0.0)
            throw new ArithmeticException();
        if(op.equals("+"))
            res = n2 + n1;
        else if(op.equals("-"))
            res = n2 - n1;
        else if(op.equals("*"))
            res = n2 * n1;
        else if(op.equals("/"))
            return n2 / n1;
        return res;
    }

    public static double switchOperation(double n1, double n2, String op){
        double res = 0;
        if(n1 == 0.0)
            throw new ArithmeticException();
        if(op.equals("+"))
            res = n2 + n1;
        else if(op.equals("-"))
            res = n2 - n1;
        else if(op.equals("*"))
            res = n2 * n1;
        else if(op.equals("/"))
            return n2 / n1;
        return res;
    }
}