/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author lizihao
 */
public class DetectEnglish {

    private boolean DictionaryIsLoad = false;

    String[] LETTERS_AND_SPACE = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        " "
    };

    Set<String> ENGLISH_WORDS = new HashSet<String>();

    public DetectEnglish() {
        this.ENGLISH_WORDS = loadDictionary();
    }

    public boolean getFlagDictionaryIsLoad() {
        return DictionaryIsLoad;

    }

//**  参数：letterPercentage  设定待检测字符串里边字母和空格占整个字符串的比例，低于这个值则认为不是英语文本
//** 返回值：Dictset
//** 说明：读取外部词典文件（dictionary.txt）到set，dictionary.txt格式为一行一个英文单词。
    private Set<String> loadDictionary() {
        String filePath = "";
        File directory = new File("");//设定为当前文件夹 
        try {
            filePath = directory.getAbsolutePath();
            // System.out.println(directory.getCanonicalPath());//获取标准的路径 
            // System.out.println(directory.getAbsolutePath());//获取绝对路径 
        } catch (Exception e) {
        }

        if (!filePath.equals("")) {
            filePath += "/dictionary.txt";
            //System.out.println(filePath);
            Set<String> Dictset = new HashSet<String>();

            try {
                String encoding = "UTF-8";
                File file = new File(filePath);
                if (file.isFile() && file.exists()) { //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(file), encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        Dictset.add(lineTxt);
                        //System.out.println(lineTxt);
                    }
                    read.close();
                    DictionaryIsLoad = true;
                } else {
                    System.out.println("找不到指定的文件");
                    JOptionPane.showMessageDialog(null, "找不到指定的文件", "标题【出错啦】", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println("读取文件内容出错");
                e.printStackTrace();
            }
            return Dictset;

        } else {
            JOptionPane.showMessageDialog(null, "字典加载失败！", "标题【出错啦】", JOptionPane.PLAIN_MESSAGE);
        }

        return null;

    }

//**  参数：message 待检测字符串
//** 返回值：待检测字符串里边正常单词占整个字符串的比例
//** 说明：
    private float getEnglishCount(String message) {

        message = message.toUpperCase();//转换成大写字母的形式，eg: aBc -> ABC
        message = removeNonLetters(message);//移除message 里边的非英文大小写字母和空格的字符串
        String[] possibleWords = message.split(" ");//字符串转字符串数组。

        if (possibleWords.length == 0) {
            return 0;//  

        }

        int matches = 0;
        for (int i = 0; i < possibleWords.length; i++) {
            if (ENGLISH_WORDS.contains(possibleWords[i])) {
                matches += 1;
            }

        }
        return ((float) matches / (possibleWords.length));

    }
//** 说明：移除message 里边的非英文大小写字母和空格的字符串，并返回新的字符串。

    private String removeNonLetters(String message) {
        String lettersOnly = "";
        String[] tempmes = message.split("");
        for (int i = 0; i < tempmes.length; i++) {
            for (int j = 0; j < LETTERS_AND_SPACE.length; j++) {
                if (tempmes[i].equals(LETTERS_AND_SPACE[j])) {
                    lettersOnly += tempmes[i];

                }
            }

        }

        return lettersOnly;

    }

//**  参数：message 待检测字符串
//**  参数：wordPercentage 设定待检测字符串里边正常单词占整个字符串的比例，低于这个值则认为不是英语文本
//**  参数：letterPercentage  设定待检测字符串里边字母和空格占整个字符串的比例，低于这个值则认为不是英语文本
//** 返回值：布尔值
//** 说明：检测文本是否为英文文本，是返回true，否返回false
    public boolean isEnglish(String message, float wordPercentage, float letterPercentage) {

        // float getenglisg = getEnglishCount(message);
        boolean wordsMatch = getEnglishCount(message) * 100 >= wordPercentage;
        int numLetters = removeNonLetters(message).length();
        float messageLettersPercentage = ((float) numLetters / message.length() * 100);
        boolean lettersMatch = messageLettersPercentage >= letterPercentage;
        return (wordsMatch && lettersMatch);

    }
//n = a[].size

//    private int BinarySearch1(int a[], int value, int n) {
//        int low, high, mid;
//        low = 0;
//        high = n - 1;
//        while (low <= high) {
//            mid = (low + high) / 2;
//            if (a[mid] == value) {
//                return mid;
//            }
//            if (a[mid] > value) {
//                high = mid - 1;
//            }
//            if (a[mid] < value) {
//                low = mid + 1;
//            }
//        }
//        return -1;
//    }
    public static void main(String[] args) {
        DetectEnglish det = new DetectEnglish();
        // String[] loadDictionary = det.loadDictionary();
//        String haha = "There are moments in life when you miss someone so much that you just want to pick them from your dreams and hug them for real! Dream what you want to dream;go where you want to go;be what you want to be,because you have only one life and one chance to do all the things you want to do.";
//        boolean t = det.isEnglish(haha, 20, 75);
        String[][] ss = new String[6][2];
        ss[0][0] = "0";
        ss[0][1] = "1";
//        System.out.println(t);

    }

}
