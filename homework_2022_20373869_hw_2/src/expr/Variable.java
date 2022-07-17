package expr;

import jdk.nashorn.internal.parser.Lexer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Variable implements Factor {
    private BigInteger coefficient;
    private int power;
    private boolean negitive1 = true;
    private HashMap<String,Integer> sin = new HashMap<>();
    private HashMap<String,Integer> cos = new HashMap<>();
    
    public HashMap<String,Integer> getSin() {
        return this.sin;
    }
    
    public HashMap<String,Integer> getCos() {
        return this.cos;
    }
    
    public void addNegitive1(boolean negitive) {
        this.negitive1 = negitive;
    }
    
    public boolean getNegitive1() {
        return this.negitive1;
    }
    
    public Variable(BigInteger coefficient,int power) {
        this.coefficient = coefficient;
        this.power = power;
    }
    
    public BigInteger getCoefficient() {
        return this.coefficient;
    }
    
    public int getPower() {
        return this.power;
    }
    
    public void addCofficient(BigInteger m) {
        this.coefficient = m;
    }
    
    public String toString() {
        String finalStr;
        if (this.coefficient.equals(BigInteger.valueOf(0))) {
            return " 0 ";
        }
        else if (this.power == 0) {
            finalStr = " " + this.coefficient.toString() + " ";
        }
        else if (this.coefficient.equals(BigInteger.valueOf(1))) {
            if (this.power == 1) {
                finalStr = "x";
            }
            else {
                finalStr = "x" + this.power + "**";
            }
        }
        else {
            if (this.power == 1) {
                finalStr = "x" + this.coefficient.toString() + "*";
            }
            else {
                finalStr = "x" + this.power + "**" + "*" + this.coefficient.toString();
            }
            
        }
        if (!this.getSin().isEmpty()) {
            for (Map.Entry<String,Integer> entry : this.getSin().entrySet()) {
                if (entry.getValue() == 1) {
                    finalStr =  "sin" + "(" + entry.getKey() + ")";
                }
                else {
                    finalStr =  "sin(" + entry.getKey() + ")" + entry.getValue() + "**";
                }
            }
        }
        if (!this.getCos().isEmpty()) {
            for (Map.Entry<String,Integer> entry : this.getCos().entrySet()) {
                if (entry.getValue() == 1) {
                    finalStr ="cos(" + entry.getKey() + ")" + finalStr + "*";
                }
                else {
                    finalStr = "cos(" + entry.getKey() + ")" + entry.getValue() + "**" + finalStr + "*";
                }
            }
        }
        //System.out.println("[" +finalStr+"]");
        return finalStr;   //有点问题
    }
    
    public void addSin(String sin,int power) {
        int pos = 0;
        StringBuilder sinString =  new StringBuilder();
        while (sin.charAt(pos) != '(') {
            pos++;
        }
        pos++;
        int num1 = 0;
        int num2 = 0;
        while (num1 >= num2) {
            if (sin.charAt(pos) == '(') {
                num1++;
            }
            else if (sin.charAt(pos) == ')') {
                num2++;
            }
            if (num2 > num1) {
                break;
            }
            sinString.append(sin.charAt(pos));
            pos++;
        }
        this.sin.put(sinString.toString(),power);
    }
    
    public void addCos(String cos, int time) {
        int pos = 0;
        StringBuilder cosString = new StringBuilder();
        while (cos.charAt(pos) != '(') {
            pos++;
        }
        pos++;
        int num1 = 0;
        int num2 = 0;
        while (num1 >= num2) {
            if (cos.charAt(pos) == '(') {
                num1++;
            }
            else if (cos.charAt(pos) == ')') {
                num2++;
            }
            if (num2 > num1) {
                break;
            }
            cosString.append(cos.charAt(pos));
            pos++;
        }
        this.cos.put(cosString.toString(),time);
    }
    
    public void copyCos(HashMap<String,Integer> cos) {
        this.cos = cos;
    }
    
    public void copySin(HashMap<String,Integer> sin) {
        this.sin = sin;
    }
}




