import expr.Expr;

public class sinCos {
    private String sinCos1;
    
    sinCos(String sinCos) {
        this.sinCos1 = sinCos;
    }
    
    public String simPlify() {
        StringBuilder content = new StringBuilder();
        StringBuilder initialString = new StringBuilder();
        String finalString;
        int num1 = 0;
        int num2 = 0;
        int i;
        for (i = 0; i < this.sinCos1.length(); i++) {
            if (this.sinCos1.charAt(i) == '(') {
                int pos = i + 1;
                for (int j = pos;j < this.sinCos1.length();j++) {
                    if (this.sinCos1.charAt(j) == '('){
                        num1++;
                    }
                    else if (this.sinCos1.charAt(j) == ')') {
                        num2++;
                        if (num2 > num1) {
                            break;
                        }
                    }
                    content.append(this.sinCos1.charAt(j));
                    initialString.append(this.sinCos1.charAt(j));
                }
                break;
            }
        }
        
        if (content.toString().contains("sin") || content.toString().contains("cos")) {
            String content1 = content.toString();
            sinCos contentMore = new sinCos(content1);
            finalString = contentMore.simPlify();
        }
        else {
            String content1 = content.toString();
            advance advance1 = new advance();
            advance1.setStr(content1);
            String str1 = advance1.preCheck();  //预处理
            Lexer lexer1 = new Lexer(str1);
            Parser parser = new Parser(lexer1);
            Expr expr1 = parser.parseExpr();  //解析
            caculate caculate1 = new caculate();
            caculate1.setExpr(expr1.toString());
            Expr temp = caculate1.cal();
            int flag = 0;
            if (temp.getStandExpr().size() > 1) {
                flag = 1;
            }
            finalString = caculate1.toString(temp);
            finalString = finalString.replaceAll("\\(\\+","\\(");
            if (finalString.charAt(0) == '+') {
                finalString = finalString.substring(1);
            }
        }
        String Simplified = this.sinCos1.replace(initialString.toString(),finalString);
        return Simplified;
    }
    
}





