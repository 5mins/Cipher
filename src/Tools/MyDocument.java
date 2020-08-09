/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author lizihao
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MyDocument {

    /**
     * 能输入小数的文本
     */
    public static class DoubleOnlyDocument extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attrSet) throws BadLocationException {
            //获得文本框中的值  
            if (str == null) {
                return;
            }
            
           
            char[] upper = str.toCharArray();
            char[] temp = new char[upper.length];
//            System.out.println(upper);
//            System.out.println(attrSet);
            for (int i = 0; i < upper.length; i++) {
                if ((upper[i] >= '0' && upper[i] <= '9'||upper[i]=='.')&&offset<4) {
                    temp[i] = upper[i];
                   
                }
            }
            super.insertString(offset, new String(temp).trim(), attrSet);

        }

    }

    public static class DoubleOnlyDocument12 extends PlainDocument {

        @Override
        public void insertString(int offset, String s, AttributeSet attrSet) throws BadLocationException {
            //获得文本框中的值  
            String text = this.getText(0, offset);
            String reg = "\\.";
            Pattern pat = Pattern.compile(reg);
            Matcher mat = pat.matcher(text);

            //判断文本框中是否含有小数点  
            boolean point = false;
            if (mat.find()) {
                point = true;
            }

            //如果偏移量不为0，并且无小数点时，如果当前输入的为小数点，则添加进文本框并返回，不进行之后的操作  
            if (offset != 0 && !point) {
                if (s.equals(".")) {
                    //调用父类方法将字符插入文本框中  
                    super.insertString(offset, s, attrSet);
                    return;
                }
            }

            //将字符串解析成数字是否会抛出异常，抛出异常则说明该字符不为数字，返回，否则插入文本框中  
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                return;
            }
            super.insertString(offset, s, attrSet);
        }
    }

    /**
     * 只能输入数字
     */
    public static class NumOnlyDocument1 extends PlainDocument {

        @Override
        public void insertString(int offset, String s, AttributeSet attrSet) throws BadLocationException {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                return;
            }
            super.insertString(offset, s, attrSet);
        }
    }

}
