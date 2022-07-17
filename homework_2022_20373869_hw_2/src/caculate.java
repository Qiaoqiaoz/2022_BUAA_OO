import expr.Expr;
import expr.Variable;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class caculate {
    private String expr;
    private int pos;
    public void setExpr(String expr) {
        this.expr = expr;
    }
    
    private Variable getNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < this.expr.length() && Character.isDigit(this.expr.charAt(pos))) {
            sb.append(this.expr.charAt(pos));
            ++pos;
        }
        //System.out.println(sb);
        BigInteger num = new BigInteger(sb.toString());
        return new Variable(num,0);
    }
    
    private String getSin() {
        StringBuilder sin = new StringBuilder();
        int num1 = 0;
        int num2 = 0;
        while (pos < this.expr.length()) {
            if (this.expr.charAt(pos) == '(') {
                num1++;
            }
            else if (this.expr.charAt(pos) == ')') {
                num2++;
                if ((num1 == num2) && (num1 > 0)) {
                    break;
                }
            }
            sin.append(this.expr.charAt(pos));
            ++pos;
        }
        sin.append(this.expr.charAt(pos));
        ++pos;
        sinCos sinPattern = new sinCos(sin.toString());
        String sinString = sinPattern.simPlify();
        return sinString;
    }
    
    private String getCos() {
        StringBuilder cos = new StringBuilder();
        int num1 = 0;
        int num2 = 0;
        while (pos < this.expr.length()) {
            if (this.expr.charAt(pos) == '(') {
                num1++;
            }
            else if (this.expr.charAt(pos) == ')') {
                num2++;
                if ((num1 == num2) && (num1 > 0)) {
                    break;
                }
            }
            cos.append(this.expr.charAt(pos));
            ++pos;
        }
        cos.append(this.expr.charAt(pos));
        ++pos;
        sinCos cosPattern = new sinCos(cos.toString());
        String cosString = cosPattern.simPlify();
        return cosString;
    }
    
    public Expr cal() {
        int num = 0;
        ArrayList<Expr> assExpr = new ArrayList<>();  //多项式数组
        for (pos = 0;pos < this.expr.length();) {
            if (Character.isDigit(this.expr.charAt(pos))) {
                Expr exprNum = new Expr();
                exprNum.addStandExpr(this.getNumber());
                assExpr.add(num,exprNum);
                num++;
            }
            else if (this.expr.charAt(pos) == ' ') {
                pos++;
                continue;
            }
            else if (this.expr.charAt(pos) == 'x') {
                Variable variableX = new Variable(BigInteger.ONE,1);
                Expr exprX = new Expr();
                exprX.addStandExpr(variableX);
                assExpr.add(num,exprX);
                pos++;
                num++;
            }
            else if (this.expr.charAt(pos) == 's') {
                Variable variableSin = new Variable(BigInteger.ONE,0);
                variableSin.addSin(this.getSin(),1);
                Expr exprSin = new Expr();
                exprSin.addStandExpr(variableSin);
                assExpr.add(num,exprSin);
                num++;
            }
            else if (this.expr.charAt(pos) == 'c') {
                Variable variableCos = new Variable(BigInteger.ONE,0);
                variableCos.addCos(this.getCos(),1);
                Expr exprCos = new Expr();
                exprCos.addStandExpr(variableCos);
                assExpr.add(num,exprCos);
                num++;
            }
            else if (this.expr.charAt(pos) == '+' || this.expr.charAt(pos) == '-') {
                Expr expr1 = assExpr.get(num - 2);
                Expr expr2 = assExpr.get(num - 1);
                if (this.expr.charAt(pos) == '+') {
                    expr1.getStandExpr().addAll(expr2.getStandExpr());
                }
                else {
                    expr2.fan();
                    expr1.copyExpr(expr2);
                }
                assExpr.set(num-2,expr1);
                assExpr.remove(num - 1);
                num = num - 1;
                pos++;
            }
            else if (this.expr.charAt(pos) == '*' && pos < this.expr.length() - 1 && this.expr.charAt(pos+1) == '*') {
                BigInteger mi = assExpr.get(num - 1).getStandExpr().get(0).getCoefficient();
                Expr expr1 = assExpr.get(num - 2);
                Expr exprNew = new Expr();
                exprNew.addStandExpr(new Variable(BigInteger.ONE,0));
                for (int i = 0;i < mi.longValue();i++) {
                    exprNew = muLit(exprNew,expr1);
                }
                assExpr.set(num - 2,exprNew);
                assExpr.remove(num - 1);
                num = num - 1;
                pos = pos + 2;
            }
            else {
                Expr expr1 = assExpr.get(num - 1);
                Expr expr2 = assExpr.get(num - 2);
                Expr exprNew = new Expr();
                exprNew = muLit(expr1,expr2);
                assExpr.set(num - 2,exprNew);
                assExpr.remove(num - 1 );
                num = num - 1;
                pos++;
            }
        }
        return assExpr.get(0);
    }
    
    public String toString(Expr expr) {
        StringBuilder assString = new StringBuilder();
        for (int i = 0;i < expr.getStandExpr().size();i++) {
            String finalStr;
            Variable item = expr.getStandExpr().get(i);
            
            if (item.getCoefficient().equals(BigInteger.valueOf(0))) {
                continue;
            }
            else {
                if (item.getNegitive1()) {
                    assString.append("+");
                } else {
                    assString.append("-");
                }
                if (item.getPower() == 0) {
                    finalStr = item.getCoefficient().toString();
                }
                else if (item.getCoefficient().equals(BigInteger.valueOf(1))) {
                    if (item.getPower() == 1) {
                        finalStr = "x";
                    } else {
                        finalStr = "x" + "**" + item.getPower();
                    }
                }
                else {
                    if (item.getPower() == 1) {
                        finalStr = item.getCoefficient().toString() + "*" + "x";
                    } else {
                        finalStr = item.getCoefficient().toString() + "*" + "x" + "**" + item.getPower();
                    }
                    
                }
                if (!item.getSin().isEmpty()) {
                    for (Map.Entry<String, Integer> entry : item.getSin().entrySet()) {
                        if (entry.getValue() == 1) {
                            finalStr = finalStr + "*" + "sin" + "(" + entry.getKey() + ")";
                        } else {
                            finalStr = finalStr + "*" + "sin(" + entry.getKey() + ")" + "**" + entry.getValue();
                        }
                    }
                }
                if (!item.getCos().isEmpty()) {
                    for (Map.Entry<String, Integer> entry : item.getCos().entrySet()) {
                        if (entry.getValue() == 1) {
                            finalStr = finalStr + "*" + "cos(" + entry.getKey() + ")";
                        } else {
                            finalStr = finalStr + "*" + "cos(" + entry.getKey() + ")" + "**" + entry.getValue();
                        }
                    }
                }
                assString.append(finalStr);
            }
        }
        if (assString.toString().equals("")) {
            assString.append("0");
        }
        return assString.toString();
    }
    
    public Expr muLit(Expr expr1,Expr expr2) {
        Expr exprNew = new Expr();
        for (int i = 0;i < expr1.getStandExpr().size();i++) {
            for (int j = 0;j < expr2.getStandExpr().size();j++) {
                int newPower = expr2.getStandExpr().get(j).getPower() + expr1.getStandExpr().get(i).getPower();
                BigInteger newCoffinine = expr2.getStandExpr().get(j).getCoefficient().multiply(expr1.getStandExpr().get(i).getCoefficient());
                HashMap<String,Integer> cosTemp = new HashMap<>();
                cosTemp.clear();
                cosTemp.putAll(expr1.getStandExpr().get(i).getCos());
                HashMap<String,Integer> sinTemp = new HashMap<>();
                sinTemp.clear();
                sinTemp.putAll(expr1.getStandExpr().get(i).getSin());
                if (! expr2.getStandExpr().get(j).getCos().isEmpty()) {  //需要加上新元素的cos项
                    for (Map.Entry<String,Integer> entry : expr2.getStandExpr().get(j).getCos().entrySet()) {
                        cosTemp.merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                    }
                }
                if (! expr2.getStandExpr().get(j).getSin().isEmpty()) {
                    for (Map.Entry<String,Integer> entry : expr2.getStandExpr().get(j).getSin().entrySet()) {
                        sinTemp.merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                    }
                }
                Variable addVariable = new Variable(newCoffinine, newPower);
                addVariable.copySin(sinTemp);
                addVariable.copyCos(cosTemp);
                if (expr2.getStandExpr().get(j).getNegitive1() == expr1.getStandExpr().get(i).getNegitive1()) {
                    addVariable.addNegitive1(true);
                }
                else {
                    addVariable.addNegitive1(false);
                }
                exprNew.getStandExpr().add(addVariable);
            }
        }
        return exprNew;
    }
}




