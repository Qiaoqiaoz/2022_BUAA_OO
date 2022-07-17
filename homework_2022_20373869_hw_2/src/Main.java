import com.oocourse.spec2.ExprInput;
import com.oocourse.spec2.ExprInputMode;
import expr.Expr;
import expr.Function;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static HashMap<Integer,Function> examples = new HashMap<>();
    public static void main(String[] args) {
        ExprInput in = new ExprInput(ExprInputMode.NormalMode);
        int cnt = in.getCount();
        for (int i = 0; i < cnt; i++) {
            
            String func = in.readLine(); //把表达式展开方便带入
            func = func.replaceAll(" ", "");
            func = func.replaceAll("\t","");
            String powerFu = "\\*\\*\\+";
            Pattern powerFuMoudle = Pattern.compile(powerFu);
            Matcher matcherPowFu = powerFuMoudle.matcher(func);
            if (matcherPowFu.find()) {
                func = func.replaceAll("\\*\\*\\+", "\\*\\*");
            }
            String[] exprMy = func.split("=");
            Function reFerence = new Function();
            reFerence.setName(func.charAt(0));
            reFerence.setVariable(exprMy[0]);
            reFerence.setExpr(exprMy[1]);
            examples.put(i,reFerence);
            
        }
        
        String str = in.readLine();
        str = str.replaceAll(" ", "");
        str = str.replaceAll("\t", "");
        Pattern sumPattern = Pattern.compile("sum");
        Matcher matcherSum = sumPattern.matcher(str);
        if (matcherSum.find()) {
            str = str.replaceAll("sum", "tum");
        }
        String fu = "[+|-]{2,3}";
        Pattern checkFu = Pattern.compile(fu);
        Matcher matcherFu = checkFu.matcher(str);
        while (matcherFu.find()) {
            str = str.replaceAll("\\+-", "-");
            str = str.replaceAll("-\\+", "-");
            str = str.replaceAll("--", "\\+");
            str = str.replaceAll("\\+\\+", "\\+");
            matcherFu = checkFu.matcher(str);
        }
        int pos1;
        for (pos1 = 0; pos1 < str.length(); pos1++) {
            StringBuilder initialSum = new StringBuilder();
            StringBuilder replaceString = new StringBuilder();
            StringBuilder finalSumString = new StringBuilder();
            if ((str.charAt(pos1) == 't')) {
                int num1 = 0;
                int num2 = 0;
                for (int k = 0; k < pos1; k++) {
                    if (str.charAt(k) == '(') {
                        num1++;
                    }
                    else if (str.charAt(k) == ')') {
                        num2++;
                    }
                }
                while ((!Character.isDigit(str.charAt(pos1))) && (!(str.charAt(pos1) == '-'))) {
                    initialSum.append(str.charAt(pos1));
                    pos1++;
                }
                StringBuilder beginString = new StringBuilder();
                while (str.charAt(pos1) != ',') {
                    beginString.append(str.charAt(pos1));
                    initialSum.append(str.charAt(pos1));
                    pos1++;
                }
                BigInteger begin = BigInteger.valueOf(Long.parseLong(beginString.toString()));
                initialSum.append(str.charAt(pos1)); //过滤掉逗号
                pos1++;
                StringBuilder endString = new StringBuilder();
                while (str.charAt(pos1) != ',') {
                    endString.append(str.charAt(pos1));
                    initialSum.append(str.charAt(pos1));
                    pos1++;
                }
                BigInteger end = BigInteger.valueOf(Long.parseLong(endString.toString()));
                int flag = 0;
                //InitialSum.append(str.charAt(pos1));
                initialSum.append(str.charAt(pos1)); //过滤逗号
                pos1++;
                if (str.charAt(pos1) == '(') {
                    initialSum.append(str.charAt(pos1));
                    flag = 1;
                    pos1++;
                }
                int num11 = 0;
                int num22 = 0;
                
                while (num11 >= num22) {
                    if (str.charAt(pos1) == '(') {
                        num11++;
                    }
                    else if (str.charAt(pos1) == ')') {
                        num22++;
                    }
                    if (num22 > num11) {
                        break;
                    }
                    initialSum.append(str.charAt(pos1));
                    replaceString.append(str.charAt(pos1));
                    pos1++;
                }
                initialSum.append(str.charAt(pos1));
                if (flag == 1) {
                    initialSum.append(str.charAt(++pos1));
                }
                finalSumString.append("(");
                if (replaceString.toString().contains("i")) {
                    String sumHave = replaceString.toString().replace("i",String.valueOf(begin));
                    finalSumString.append(sumHave);
                    for (BigInteger i = begin.add(BigInteger.ONE);
                         i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
                        finalSumString.append("+");
                        sumHave = replaceString.toString().replace("i",String.valueOf(i));
                        finalSumString.append(sumHave);
                    }
                }
                else {
                    finalSumString.append(replaceString);
                    for (BigInteger i = begin.add(BigInteger.ONE);
                         i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
                        finalSumString.append("+");
                        finalSumString.append(replaceString);
                    }
                }
                finalSumString.append(")");
                str = str.replace(initialSum.toString(),finalSumString.toString());
            }
        }
        //把sum直接展开，可能存在的问题是符号问题,可以解决，即使在函数里面也直接展开
        //遍历递归替换函数中的参数
        Func func = new Func(examples,str);  //预处理函数
        str = func.checkFunc();
        
        advance advance1 = new advance();
        advance1.setStr(str);
        String str1 = advance1.preCheck();  //预处理
        Lexer lexer1 = new Lexer(str1);
        Parser parser = new Parser(lexer1);
        Expr expr1 = parser.parseExpr();  //解析
        caculate caculate1 = new caculate();
        caculate1.setExpr(expr1.toString());
        Expr temp = caculate1.cal();
        String finalString = caculate1.toString(temp);
        finalString = finalString.replaceAll("\\(\\+","\\(");
        if (finalString.charAt(0) == '+') {
            finalString = finalString.substring(1);
        }
        finalString = finalString.replaceAll("\\+-", "-");
        finalString = finalString.replaceAll("-\\+", "-");
        finalString = finalString.replaceAll("--", "\\+");
        finalString = finalString.replaceAll("\\+\\+", "\\+");
        finalString = finalString.replaceAll("\\^","**");
        System.out.println(finalString);
    }
    
}

//String SumPattern = "sum(i,([0-9]+),([0-9]+),([.+]))";








