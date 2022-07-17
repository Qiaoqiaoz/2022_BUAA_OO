import expr.*;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final Lexer lexer;
    public  int time = 1;
    int fu = 0;
    
    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }
    
    public Expr parseExpr() {
        Expr expr = new Expr();
        expr.addTerm(parseTerm());
        while (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            if (lexer.peek().equals("+")) {
                lexer.next();
                Term finalTerm = parseTerm();
                finalTerm.addNegitive(true);
                expr.addTerm(finalTerm);
            }
            else {
                lexer.next();
                Term finalTerm = parseTerm();
                finalTerm.addNegitive(false);
                //finalTerm.caculate();
                //finalTerm.cal();
                // System.out.println("-{" + finalTerm.toString() + "}");
                expr.addTerm(finalTerm);
            }
        }
        return expr;
    }
    
    public Term parseTerm() {
        Term term = new Term();
        term.addFactor(parseFactor());
        while ((lexer.peek().equals("*")) || (fu == 1)) {
            fu = 0;
            lexer.next();
            term.addFactor(parseFactor());
        }
        return term;
    }
    
    public Factor parseFactor() {
        if (lexer.peek().equals("(")) {
            lexer.next();
            Expr expr = parseExpr();
            lexer.next();
            if (lexer.peek().equals("^")) {
                lexer.next();
                time = Integer.valueOf(lexer.peek());
                if (time == 0) {
                    lexer.next();
                    return new Variable(BigInteger.valueOf(1),0);
                }
                expr.setMi(time);
                lexer.next();
                time = 0;
                return expr;
            }
            else {
                expr.setMi(1);
                //lexer.next();
                return expr;
            }
        }
        else if (Character.isDigit(lexer.peek().charAt(0))) {     //number
            BigInteger num = new BigInteger(lexer.peek());
            //System.out.println(num);
            BigInteger finalNum = BigInteger.valueOf(1);
            lexer.next();
            int time1 = 1;
            int power = 0;
            if (lexer.peek().equals("^")) {
                lexer.next();
                time1 = Integer.valueOf(lexer.peek());
                lexer.next();
            }
            for (int i = 1; i <= time1;i++) {
                finalNum = finalNum.multiply(num);
            }
            //
            //System.out.println(num +"," +power);
            return new Variable(finalNum,power);
        }
        
        else if (lexer.peek().charAt(0) == 's') {
            String sinString = lexer.peek();
            Variable sin = new Variable(BigInteger.ONE,0);
            lexer.next();
            if (lexer.peek().equals("^")) {
                lexer.next();
                int time = Integer.valueOf(lexer.peek());
                sin.addSin(sinString,time);
                lexer.next();
            }
            else {
                sin.addSin(sinString,1);
            }
            return sin;
        }
        else if (lexer.peek().charAt(0) == '-') {
            fu = 1;
            BigInteger num = BigInteger.valueOf(-1);
            //System.out.println(num);
            int power = 0;
            
            //System.out.println(num +"," +power);
            return new Variable(num,power);
        }
        else if (lexer.peek().charAt(0) == 'c') {
            String cosString = lexer.peek();
            Variable cos = new Variable(BigInteger.ONE,0);
            lexer.next();
            if (lexer.peek().equals("^")) {
                lexer.next();
                int time = Integer.valueOf(lexer.peek());
                cos.addCos(cosString,time);
                lexer.next();
            }
            else {
                cos.addCos(cosString,1);
            }
            return cos;
        }
        else if (lexer.peek().charAt(0) == 'c') {
            String cos1 = lexer.peek();
            return new Cos(cos1);
        }
        else  {   //x**num  x
            String module = "x\\^(.+)";
            Pattern pattern = Pattern.compile(module);
            Matcher matcher = pattern.matcher(lexer.peek());
            String part = lexer.peek();
            int power = 0;
            if (matcher.find()) {
                part = matcher.group(1);
                power = Integer.valueOf(part);
            }
            //System.out.println(part);
            //System.out.println(part[1]);
            
            BigInteger num = new BigInteger("1");
            lexer.next();
            //System.out.println(num +"," +power);
            return new Variable(num, power);
        }
    }
}



