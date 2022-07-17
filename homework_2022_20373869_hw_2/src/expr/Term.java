package expr;

import java.math.BigInteger;
import java.util.*;

public class Term implements Factor {
    private  final ArrayList<Factor> factors;
    private boolean negitive;
    
    public Term() {
        this.factors = new ArrayList<>();
    }
    
    public ArrayList<Factor> getFactors() {
        return this.factors;
    }
    
    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }
    
    public void addNegitive(boolean negitive) {
        this.negitive = negitive;
    }
    
    public boolean getNegitive() {
        return this.negitive;
    }
    
    public Variable caculate() {
        BigInteger coff = BigInteger.valueOf(1);
        int zhi = 0;
        int flag = 0;
        HashMap<String,Integer> cosString = new HashMap<>();
        HashMap<String,Integer> sinString = new HashMap<>();
        Iterator<Factor> item1 = this.factors.iterator();
        while (item1.hasNext()) {
            Factor item = item1.next();
            if (item instanceof Variable) {
                flag = 1;
                coff = coff.multiply(((Variable) item).getCoefficient());
                zhi = zhi + ((Variable) item).getPower();
                for (Map.Entry<String,Integer> entry : ((Variable) item).getCos().entrySet()) {
                    cosString.merge(entry.getKey(),entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);//把sin和cos也加进来
                }
                for (Map.Entry<String,Integer> entry : ((Variable) item).getSin().entrySet()) {
                    sinString.merge(entry.getKey(), entry.getValue(),(oldvalue,newvalue) -> oldvalue + newvalue);
                }
                item1.remove();
            }
        }
        if (flag == 1) {
            Variable item2 = new Variable(coff, zhi);
            item2.copyCos(cosString);
            item2.copySin(sinString);
            this.factors.add(item2);
            return item2;
        } else {
            Variable item2 = new Variable(BigInteger.valueOf(1), 0);
            return item2;
        }
    }
    
    public String toString() {
        Iterator<Factor> iter = factors.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(iter.next().toString());
        if (iter.hasNext()) {
            sb.append(" ");
            sb.append(iter.next().toString());
            sb.append(" *");
            while (iter.hasNext()) {
                sb.append(" ");
                sb.append(iter.next().toString());
                sb.append(" *");
            }
        }
        return sb.toString();
    }
}







