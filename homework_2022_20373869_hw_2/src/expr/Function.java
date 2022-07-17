package expr;

import java.util.HashMap;

public class Function {
    private char name;
    private HashMap<Integer,String> item = new HashMap<>();  //变量及对应位置
    private String expr;
    
    public void setExpr(String expr) {
        this.expr = expr;
    }
    
    public String getExpr() {
        return this.expr;
    }
    
    public char getName() {
        return this.name;
    }
    
    public void setName(char name) {
        this.name = name;
    }
    
    public HashMap<Integer,String> getItem() {
        return this.item;
    }
    
    public void setVariable(String variable) {
        this.name = variable.charAt(0);
        int pos = 2;
        int i = 0;
        while (pos < variable.length()) {
            this.item.put(i, String.valueOf(variable.charAt(pos)));
            pos++;
            if (variable.charAt(pos) == ',') {
                i++;
                pos++;    //对应第一位第二位与x y变量
                continue;
            } else {
                break;
            }
        }
    }
}





