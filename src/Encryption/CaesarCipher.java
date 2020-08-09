/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lizihao
 */
public class CaesarCipher {

    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();

    //  System.out.println (encodedText);
//解码
    //  System.out.println (new String(decoder.decode(encodedText), "UTF-8"));
    private static String LETTERS = "! &+28=\\0EJNSX]bglqv{\",4'9>BFKOTY^chmrw|#(-5:?CGLPUZ_dinsx}$).6;@DHMQV[`ejoty~%*/7<A1I3RWafkpuz";//! &+28=\\0EJNSX]bglqv{\",4\'9>BFKOTY^chmrw|#(-5:?CGLPUZ_dinsx}$).6;@DHMQV[`ejoty~%*/7<A1I3RWafkpuz   \\! \"#$%&\'()*+,-./2456789:;<=>?@A0BCD1EFGHIJKLM3NOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~

    //flag 0加密，1解密  /key的大小为 0 到message的长度之间。 
 
    static public String EncryptMessage(int key, String Message, int flag) {

        String[] Message_ARR = new String[0];

        String OutputMessage = "";

        if (flag == 0) {
            try {
                byte[] textByteMessage = Message.getBytes("UTF-16");
                String encodedTextMessage = encoder.encodeToString(textByteMessage);//base64编码
                Message_ARR = encodedTextMessage.split("");//encodedTextMessage 转为字符串数组
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(CaesarCipher.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (flag == 1 || flag == 3 || flag == 2) {
            Message_ARR = Message.split("");//Message 转为字符串数组
        }

        if (flag == 2) {
            flag = 0;//2为 vigenerePro 调用，已经在上级调用base64 编码，故此处跳过
        }
        for (int i = 0; i < Message_ARR.length; i++) {
            // int indexOfLETTERS1 = LETTERS.indexOf(Message_ARR[i]);//用下面那句替代
            int indexOfLETTERS = getLETTERSpos(Message_ARR[i]);
            if (indexOfLETTERS == -1) {
                OutputMessage += Message_ARR[i];
                System.out.println("不支持加密此字符" + i);
            } else {

                if (flag == 0) {//加密
                    indexOfLETTERS = indexOfLETTERS + key;
                } else if (flag == 1 || flag == 3) {//解密
                    indexOfLETTERS = indexOfLETTERS - key;
                }

                if (indexOfLETTERS >= LETTERS.length()) {
                    indexOfLETTERS = indexOfLETTERS - LETTERS.length();
                } else if (indexOfLETTERS < 0) {
                    indexOfLETTERS = indexOfLETTERS + LETTERS.length();
                }

                OutputMessage += LETTERS.substring(indexOfLETTERS, indexOfLETTERS + 1);
            }

        }
        // System.out.println(OutputMessage);
        if (flag == 1) {

            if (isBase64(OutputMessage)) {

                try {
                    OutputMessage = new String(decoder.decode(OutputMessage), "UTF-16");
                } catch (UnsupportedEncodingException ex) {
                    // Logger.getLogger(CaesarCipher.class.getName()).log(Level.SEVERE, null, ex);
                    OutputMessage = "1234";

                }
            }

        }

        //System.out.println(LETTERS);
        return OutputMessage;
    }

    public static int getLETTERSlen() {
        //int i= LETTERS.length();
        return LETTERS.length();

    }

    public static int getLETTERSpos(String s) {

        return LETTERS.indexOf(s);

    }

    public static String getLETTERS() {

        return LETTERS;

    }

    private static boolean isBase64(String str) {
        if (str == null || str.length() == 0) {
            return false;
        } else {
            if (str.length() % 4 != 0) {
                return false;
            }

            char[] strChars = str.toCharArray();
            for (char c : strChars) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
                        || c == '+' || c == '/' || c == '=') {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        // caesarCipher CH = new caesarCipher();
        //EncryptMessage(2, "abcdef", 0);
        //  EncryptMessage(2, EncryptMessage(2, "abcdefg", 0), 1);

    }
}
