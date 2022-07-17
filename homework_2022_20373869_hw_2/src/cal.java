import expr.Expr;
import expr.Term;
import expr.Variable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

public class cal {
    private Expr expr;
    
    public void setExpr(Expr expr) {
        this.expr = expr;
    }
    
    public String cal1() {
        StringBuilder finalString = new StringBuilder();
        Iterator<Term> iterator = this.expr.getTerm().iterator();
        while (iterator.hasNext()) {
            Term now = iterator.next();
            ArrayList<Variable> ass = new ArrayList<>();
            for (int j =0; j < now.getFactors().size();j++) {
                if (now.getFactors().get(j) instanceof Expr) {
                
                }
            }
            ass.addAll(expr.cal(now));
            for (int i = 0;i < ass.size();i++) {
                if (now.getNegitive()) {
                    if (!ass.get(i).getCoefficient().equals(BigInteger.valueOf(0))) {
                        if (ass.get(i).getNegitive1()) {
                            finalString.append("+");
                            finalString.append(ass.get(i));
                        }
                        else {
                            finalString.append("-");
                            finalString.append(ass.get(i));
                        }
                    }
                }
                else {
                    if (!ass.get(i).getCoefficient().equals(BigInteger.valueOf(0))) {
                        if (ass.get(i).getNegitive1() == true) {
                            finalString.append("-");
                            finalString.append(ass.get(i));
                        }
                        else {
                            finalString.append("+");
                            finalString.append(ass.get(i));
                        }
                    }
                }
            }
        }
        return finalString.toString();
    }
}

