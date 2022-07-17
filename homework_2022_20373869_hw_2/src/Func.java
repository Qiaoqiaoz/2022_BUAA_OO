import expr.Function;

import java.util.HashMap;

public class Func {
    private String funString;
    private HashMap<Integer, Function> examples = new HashMap<>();
    
    Func(HashMap<Integer, Function> examples, String funString) {
        this.examples = examples;
        this.funString = funString;
    }
    
    public String checkFunc() {
        int pos = 0;
        int pos2;
        int flag1 = 0;
        String str = this.funString;
        for (pos2 = 0;pos2 < str.length();pos2++) {
            StringBuilder initialString = new StringBuilder();   //在str里面要替换的带入实参之后的形参
            if ((str.charAt(pos2) == 'f') || (str.charAt(pos2) == 'g') ||
                    (str.charAt(pos2) == 'h')) {
                pos = pos2;
                initialString.append(str.charAt(pos));
                initialString.append(str.charAt(pos + 1));  //左括号
                char name = str.charAt(pos);
                pos = pos + 2;
                for (int j = 0; j < examples.size(); j++) {
                    if (examples.get(j).getName() == name) {  //找到匹配表达式
                        StringBuilder inputString =
                                new StringBuilder(examples.get(j).getExpr());  //形参表达式
                        HashMap<Integer,String> fitem = examples.get(j).getItem();
                        for (int t = 0; t < fitem.size();t++) {
                            int num1 = 0;
                            int num2 = 0;
                            StringBuilder item = new StringBuilder();
                            while (str.charAt(pos) != ',') {  //若有多个可以用逗号分割
                                if (str.charAt(pos) == '(')
                                {
                                    num1++;
                                }
                                else if (str.charAt(pos) == ')') {
                                    num2++;
                                }
                                if (num1 < num2) {
                                    break;   //处理最后一个和只有一个的情况
                                }
                                else {
                                    initialString.append(str.charAt(pos));
                                    item.append(str.charAt(pos));  //找到匹配替换项
                                    ++pos;
                                }
                            }
                            initialString.append(str.charAt(pos));
                            pos++;  //过滤掉最后一个括号或者，
                            if ((item.toString().indexOf('h') != -1) ||
                                    (item.toString().indexOf('g') != -1) ||
                                    (item.toString().indexOf('f') != -1)) {
                                Func func1 = new Func(this.examples,item.toString());
                                item = new StringBuilder(func1.checkFunc());
                            }
                            StringBuilder itemnew = new StringBuilder();
                            itemnew = item;
                            int change = 0;
                            for (int i = 0; i < inputString.length(); ) {
                                if (String.valueOf(inputString.charAt(i)).equals(fitem.get(t))) {
                                    if (i <= inputString.length() - 3) {
                                        if ((inputString.charAt(i + 1) == '*') &&
                                                inputString.charAt(i + 2) == '*') {
                                            StringBuilder num = new StringBuilder();
                                            for (int j1 = i + 3;j1 < inputString.length();j1++) {
                                                if (Character.isDigit(inputString.charAt(j1))) {
                                                    num.append(inputString.charAt(j1));
                                                }
                                                else {
                                                    break;
                                                }
                                            }
                                            if (Integer.valueOf(num.toString()) % 2 == 0) {
                                                if (item.charAt(0) == '-') {
                                                    itemnew = new
                                                            StringBuilder(
                                                            item.substring(1)); //若换上去在之后指数偶数，底数负数
                                                    inputString.replace(i,i + 1,itemnew.toString());
                                                    change = 1;
                                                    i = i + itemnew.length();
                                                }
                                            }
                                        }
                                    }
                                    if (change == 1) {
                                        change = 0;
                                        continue;
                                    }
                                    else {
                                        inputString.replace(i,i + 1,item.toString());
                                        i = i + item.length();
                                        continue;
                                    }
                                }
                                i++;
                            }
                        }
                        String finalString1;
                        finalString1 = "(" + inputString + ")";
                        str = str.replace(initialString.toString(),finalString1);
                        str = str.replaceAll("\\(\\+","\\(");
                        //System.out.println(str);
                    }
                }
            }
        }
        return str;
    }
}




