import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class advance {
    private String str;
    
    public void setStr(String str) {
        this.str = str;
    }
    
    public String preCheck() {
        if (this.str.charAt(0) == '-') {
            this.str = "0" + this.str;
        }
        if (this.str.charAt(0) == '+') {
            this.str = this.str.substring(1);
        }
        this.str = this.str.replaceAll(" ", "");
        this.str = this.str.replaceAll("\t", "");
        
        String fu = "[+|-]{2,3}";
        Pattern checkFu = Pattern.compile(fu);
        Matcher matcherFu = checkFu.matcher(this.str);
        while (matcherFu.find()) {
            this.str = this.str.replaceAll("\\+-", "-");
            this.str = this.str.replaceAll("-\\+", "-");
            this.str = this.str.replaceAll("--", "\\+");
            this.str = this.str.replaceAll("\\+\\+", "\\+");
            matcherFu = checkFu.matcher(this.str);
        }
        this.str = this.str.replaceAll("\\(-","\\(0-");
        this.str = this.str.replaceAll("\\(\\+","\\(0+");
        String powerFu = "\\*\\*\\+";
        Pattern powerFuMoudle = Pattern.compile(powerFu);
        Matcher matcherPowFu = powerFuMoudle.matcher(this.str);
        if (matcherPowFu.find()) {
            this.str = this.str.replaceAll("\\*\\*\\+", "^");
        }
        String powerFu2 = "\\*\\*";
        Pattern powerFuMoudle2 = Pattern.compile(powerFu2);
        Matcher matcherPowFu2 = powerFuMoudle2.matcher(this.str);
        if (matcherPowFu2.find()) {
            this.str = this.str.replaceAll("\\*\\*", "^");
        }
        
        String doubleZhi = "[x)]\\^([0-9])\\^([0-9])";
        String doubleReplace = "([0-9])\\^([0-9])";
        Pattern patternZhi1 = Pattern.compile(doubleReplace);
        Matcher matcherZhi1 = patternZhi1.matcher(this.str);
        Pattern patternZhi = Pattern.compile(doubleZhi);
        Matcher matcherZhi = patternZhi.matcher(this.str);
        if (matcherZhi.find()) {
            String part1 = matcherZhi.group(1);
            String part2 = matcherZhi.group(2);
            int zhi = Integer.valueOf(part1) * Integer.valueOf(part2);
            matcherZhi1.find();
            str = str.replace(matcherZhi1.group(0),String.valueOf(zhi));
        }
        
        
        Pattern powerFuMoudle1 = Pattern.compile(powerFu2);
        Matcher matcherPowFu1 = powerFuMoudle1.matcher(str);
        if (matcherPowFu1.find()) {
            this.str = this.str.replaceAll("\\*\\*", "^");
        }
        Pattern chengjia = Pattern.compile("\\*\\+");
        Matcher matcherCheng = chengjia.matcher(str);
        if (matcherCheng.find()) {
            this.str = this.str.replaceAll("\\*\\+","\\*");
        }
        return this.str;
    }
}



