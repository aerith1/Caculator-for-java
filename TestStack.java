import java.util.*;

public class TestStack {
    public static void main(String[] args){
        String[] a = new String[20];
        a = getExpression("50 + (-2)");
        for(String i: a){
            System.out.println(i);
        }
        List<String> b = new ArrayList<String>();
        b = caculatorExpression(a);
        parseExpression(b);
    }

    public static List<String> caculatorExpression(String[] expression){
        List<String> operandList = new ArrayList<String>();//存放数字
        Stack<String> operatorStack = new Stack<>(); //存放表达式
        // String[] expression = expression.split(" ");
        for(int i = 0; i < expression.length; i++){
            System.out.println(expression[i]);
            if(expression[i].matches("\\d+"))
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
            else if(expression[i].charAt(0) == '-'&& Character.isDigit(expression[i].charAt(1))){
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
        System.out.println(operandList);
        return operandList;
    }

    public static int parseExpression(List<String> exp){
        Integer result = 0;
        // System.out.println(exp);
        Stack<String> st = new Stack<String>();
        for(int i = 0; i < exp.size(); i++){
            if(exp.get(i).equals("+") || exp.get(i).equals("-") || exp.get(i).equals("*") || exp.get(i).equals("/")){
                Integer n1, n2;
                n1 = Integer.valueOf(st.pop());
                n2 = Integer.valueOf(st.pop());
                st.push(String.valueOf(switchOperation(n1, n2, exp.get(i))));
            }
            else
                st.push(exp.get(i));
        }
        result = Integer.valueOf(st.pop());
        System.out.println(result);
        return result;
    }

    public static String[] getExpression(String expression){
        System.out.println("getExpression is used");
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
        }else{
                exp.add(String.valueOf(String.valueOf(expression.charAt(i))));
                i++;
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

    public static int switchOperation(int n1, int n2, String op){
        int res = 0;
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