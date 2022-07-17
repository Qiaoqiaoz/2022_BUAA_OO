package expr;

import java.math.BigInteger;
import java.util.*;

public class Expr implements Factor {
    private final ArrayList<Term> terms;
    private ArrayList<Variable> standExpr = new ArrayList<>();
    private int Mi = 1;
    
    public void setMi(int n) {
        this.Mi = n;
    }
    
    public int getMi() {
        return this.Mi;
    }
    
    public Expr() {
        this.terms = new ArrayList<>();
    }
    
    public void addStandExpr(Variable variable) {
        this.standExpr.add(variable);
    }
    
    public void copyExpr(Expr expr) {
        this.standExpr.addAll(expr.getStandExpr());
    }
    
    public void fan() {
        for (int i = 0;i < this.standExpr.size();i++) {
            if (this.standExpr.get(i).getNegitive1() == true) {
                this.standExpr.get(i).addNegitive1(false);
            }
            else {
                this.standExpr.get(i).addNegitive1(true);
            }
        }
    }
    
    public ArrayList<Variable> getStandExpr() {
        return this.standExpr;
    }
    
    ArrayList<Term> getTerms() {
        ArrayList<Term> base = new ArrayList<>();
        base.addAll(this.terms);
        return base;
    }
    
    public ArrayList<Term> getTerm() {
        return this.terms;
        
    }
    
    public void addTerm(Term term) {
        this.terms.add(term);
    }
    
    public String toString() {
        Iterator<Term> iter = terms.iterator();
        Term now0 = iter.next();
        StringBuilder sb = new StringBuilder();
        sb.append(now0.toString());
        if (iter.hasNext()) {
            Term now = iter.next();
            sb.append(now.toString());
            if (now.getNegitive()) {
                sb.append("+");
            }
            else {
                sb.append("-");
            }
            while (iter.hasNext()) {
                Term now1 = iter.next();
                sb.append(now1.toString());
                if (now1.getNegitive()) {
                    sb.append("+");
                }
                else {
                    sb.append("-");
                }
            }
        }
        if (this.Mi != 1) {
            sb.append(this.Mi);
            sb.append("**");
        }
        return sb.toString();
    }
    
    
    
    public ArrayList<Variable> cal(Term term) {   //返回一个hashmap表征<Integer<Biginteger>,即一个Variable数组
        ArrayList<Variable> white = new ArrayList<>();
        Variable one = new Variable(BigInteger.valueOf(1),0);
        white.add(one);
        ArrayList<Variable> temp = new ArrayList<>();
        temp.add(one);  //表示前面乘出来的结果，初始化为0
        for (Factor item : term.getFactors()) {    //遍历term中的元素
            if (item instanceof Variable) {   //基本量
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getCoefficient().compareTo(BigInteger.valueOf(0)) != 0) {
                        int newPower = ((Variable) item).getPower() + temp.get(i).getPower();
                        BigInteger newCoffinine = ((Variable) item).getCoefficient().multiply(temp.get(i).getCoefficient());
                        if (!((Variable) item).getCos().isEmpty()) {  //需要加上新元素的cos项
                            for (Map.Entry<String,Integer> entry : ((Variable) item).getCos().entrySet()) {
                                temp.get(i).getCos().merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                            }
                        }
                        else if (!((Variable) item).getSin().isEmpty()) {
                            for (Map.Entry<String,Integer> entry : ((Variable) item).getSin().entrySet()) {
                                temp.get(i).getSin().merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                            }
                        }
                        Variable addVariable = new Variable(newCoffinine, newPower);
                        addVariable.copyCos(temp.get(i).getCos());
                        addVariable.copySin((temp.get(i).getSin()));
                        if (temp.get(i).getNegitive1() == ((Variable) item).getNegitive1()) {
                            addVariable.addNegitive1(true);
                        } else {
                            addVariable.addNegitive1(false);
                        }
                        temp.set(i,addVariable);
                    }
                }
                white.addAll(temp);    //arraylist的addall
            } else {  //在该函数中的全局乘数temp
                ArrayList<Variable> part = new ArrayList<>();
                ArrayList<Variable> partTemp = new ArrayList<>();
                for (int t = 0;t < ((Expr)item).getTerms().size();t++) {//遍历表达式中的项
                    partTemp = cal(((Expr) item).getTerms().get(t));
                    part.addAll(partTemp);
                }
                for (int z = 0; z < ((Expr) item).getMi(); z++) {
                    //temp.putAll(white);   //保存被乘数，即被乘的hashmap数组,temp不可以变
                    ArrayList<Variable> assTemp = new ArrayList<>();
                    //清空
                    for (int j = 0; j < ((Expr) item).getTerms().size(); j++) {   //拆分要乘的表达式
                        Variable itemExpr;
                        itemExpr = ((Expr) item).getTerms().get(j).caculate();
                        itemExpr.addNegitive1(((Expr) item).getTerms().get(j).getNegitive());
                        for (int i = 0; i < temp.size(); i++) {
                            if (!temp.get(i).getCoefficient().equals(BigInteger.valueOf(0))) {
                                HashMap<String,Integer> cosTemp = new HashMap<>();
                                cosTemp.clear();
                                cosTemp.putAll(temp.get(i).getCos());
                                HashMap<String,Integer> sinTemp = new HashMap<>();
                                sinTemp.clear();
                                sinTemp.putAll(temp.get(i).getSin());
                                if (! itemExpr.getCos().isEmpty()) {  //需要加上新元素的cos项
                                    for (Map.Entry<String,Integer> entry : (itemExpr).getCos().entrySet()) {
                                        cosTemp.merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                                    }
                                }
                                if (! itemExpr.getSin().isEmpty()) {
                                    for (Map.Entry<String,Integer> entry : (itemExpr).getSin().entrySet()) {
                                        sinTemp.merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                                    }
                                }
                                int newPower = itemExpr.getPower() + temp.get(i).getPower();
                                BigInteger newCoffinine = itemExpr.getCoefficient().multiply(temp.get(i).getCoefficient());
                                Variable addVariable = new Variable(newCoffinine, newPower);
                                addVariable.copySin(sinTemp);
                                addVariable.copyCos(cosTemp);
                                if (temp.get(i).getNegitive1() == itemExpr.getNegitive1()) {
                                    addVariable.addNegitive1(true);
                                }
                                else {
                                    addVariable.addNegitive1(false);
                                }
                                assTemp.add(addVariable);
                                
                            }
                        }
                    }
                    temp.clear();
                    temp.addAll(assTemp);
                    
                }
            }
        }
        return temp;
    }
}





