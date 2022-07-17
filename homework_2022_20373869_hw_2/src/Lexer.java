public class Lexer {
    private final String input;
    private int pos = 0;
    private String curToken;
    
    public Lexer(String input) {
        this.input = input;
        this.next();
    }
    
    private String getNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            ++pos;
        }
        //System.out.println(sb);
        return sb.toString();
    }
    
    private String getPower() {
        StringBuilder pow = new StringBuilder();
        while (pos < input.length() && ((Character.isDigit(input.charAt(pos)) ||
                (input.charAt(pos) == '^') || (input.charAt(pos) == 'x') ||
                (input.charAt(pos) == 'y') || (input.charAt(pos) == 'z')))) {
            pow.append(input.charAt(pos));
            ++pos;
        }
        // 读入x^n,y^n,z^n(用于自定义函数表达式展开)，i^n(用于sum中expr的展开)
        return pow.toString();
    }
    
    private String getSin() {
        StringBuilder sin = new StringBuilder();
        int num1 = 0;
        int num2 = 0;
        while (pos < input.length()) {
            if (input.charAt(pos) == '(') {
                num1++;
            }
            else if (input.charAt(pos) == ')') {
                num2++;
                if ((num1 == num2) && (num1 > 0)) {
                    break;
                }
            }
            sin.append(input.charAt(pos));
            ++pos;
        }
        sin.append(input.charAt(pos));
        ++pos;
        return sin.toString();
    }
    
    private String getCos() {
        StringBuilder cos = new StringBuilder();
        int num1 = 0;
        int num2 = 0;
        while (pos < input.length()) {
            if (input.charAt(pos) == '(') {
                num1++;
            }
            else if (input.charAt(pos) == ')') {
                num2++;
                if ((num1 == num2) && (num1 > 0)) {
                    break;
                }
            }
            cos.append(input.charAt(pos));
            ++pos;
        }
        cos.append(input.charAt(pos));
        ++pos;
        return cos.toString();
    }
    
    public void next() {
        if (pos == input.length()) {
            return;
        }
        
        char c = input.charAt(pos);
        if (Character.isDigit(c)) {
            curToken = getNumber();
            
        }
        else if (c == 'x') {
            if (pos < input.length() - 1) {
                if (input.charAt(pos + 1) == '^') {
                    curToken = getPower();
                } else {
                    curToken = "x^1";
                    ++pos;
                }
            }
            else {
                curToken = "x^1";
                ++pos;
            }
        }
        
        else if (c == 'c') {
            curToken = getCos();
        }
        else if (c == 's') {
            curToken = getSin();
        }
        
        else if ("()+-^*".indexOf(c) != -1) {
            pos += 1;
            curToken = String.valueOf(c);
        }
        
    }
    public String peek() {
        return this.curToken;
    }
}






