/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

import static Encryption.CaesarCipher.getLETTERSpos;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lizihao
 */
public class MixCipher {

    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();
    static String VigenereLETTERS = CaesarCipher.getLETTERS();

    //flag 0加密，1解密
    static public String MixDecrypt(int key, String message, int flag) throws UnsupportedEncodingException {
        String OutputMessage = "";
        //加密
        if (flag == 0 || flag == 2) {

            String EncryptMessage = TransCipher.EncryptMessage(key, message);
            int keyCaesar = (key % getVigenereLETTERSlen());//自定义凯撒密码的KEY2,这个key可以改成别的数字。
            OutputMessage = CaesarCipher.EncryptMessage(keyCaesar, EncryptMessage, flag);

//解密
        } else if (flag == 1 || flag == 3) {
            int keyCaesar = (key % getVigenereLETTERSlen());//自定义凯撒密码的KEY2,这个key可以改成别的数字。
            String DecryptMessage = CaesarCipher.EncryptMessage(keyCaesar, message, flag);
            OutputMessage = TransCipher.DecryptMessage(key, DecryptMessage);

        }

        return OutputMessage;

    }
    //flag 2加密，3解密

    static public String VigenerePro(String keyStr, String message, int flag) throws UnsupportedEncodingException {
        String OutputMessage = "";
        int keylndex = 0;
        String[] Key_ARR;
        Key_ARR = keyStr.split("");//Message转为字符串数组
        int[] keyStrToSpecialNum = new int[Key_ARR.length];//key 中每个字符在“LETTERS”对应的位置存在数组里边。//例如key是 "apple" 对应的 keyToSpecialNum  就是89 92 92 17 72
        for (int i = 0; i < Key_ARR.length; i++) {
            // int indexOfLETTERS1 = LETTERS.indexOf(Message_ARR[i]);//用下面那句替代
            keyStrToSpecialNum[i] = getVigenereLETTERSpos(Key_ARR[i]);

        }
//用keyToSpecialNum 里边的数字作为key 对原文进行多次MixDecrypt加密

        if (flag == 2) {//增加对汉字加密的支持，在加密前先进行base64编码
            byte[] textByteMessage = message.getBytes("UTF-16");
            message = encoder.encodeToString(textByteMessage);//base64编码
        } else if (flag == 3) {
            message = TransCipher.DecryptMessage(keyStrToSpecialNum[0], message);//先对密文进行移位解密。密钥key取第一个字符对应的数字 keyStrToSpecialNum[0] 可以更改为别的
        }

        for (int i = 0; i < message.length(); i++) {

            OutputMessage += CaesarCipher.EncryptMessage(keyStrToSpecialNum[keylndex], message.substring(i, i + 1), flag);//维吉尼亚加密
            keylndex += 1;
            if (keylndex == keyStr.length()) {
                keylndex = 0;
            }

        }

        if (flag == 2) {
            OutputMessage = TransCipher.EncryptMessage(keyStrToSpecialNum[0], OutputMessage);//最后对维吉尼亚进行移位加密，防止频率破解。密钥key取第一个字符对应的数字 keyStrToSpecialNum[0]可以更改为别的
        } else if (flag == 3) {
            int utfDecodeSuccessed = 1;//utf解密失败标志
            try {
                OutputMessage = new String(decoder.decode(OutputMessage), "UTF-16");//增加对汉字加密的支持，在解密后对密文进行base64解码
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MixCipher.class.getName()).log(Level.SEVERE, null, ex);
                utfDecodeSuccessed = 0;
            } finally {
                if (utfDecodeSuccessed == 0) {
                    OutputMessage = "解密失败，不支持解密此文本！";
                }
                return OutputMessage;
            }

        }

        return OutputMessage;

    }

    public static void reverse(int[] array) {

        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    private static int getVigenereLETTERSlen() {
        //int i= LETTERS.length();
        return VigenereLETTERS.length();

    }

    private static int getVigenereLETTERSpos(String s) {

        return VigenereLETTERS.indexOf(s);

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // caesarCipher CH = new caesarCipher();
        String encr = "you are a foolish !";
        String decry = "";
        //encr = MixDecrypt(2, encr, 0);
        //decry = MixDecrypt(2, encr, 1);
        encr = VigenerePro("apple", encr, 2);
        decry = VigenerePro("apple", encr, 3);
        System.out.println("加密后:" + encr);
        System.out.println("解密后:" + decry);

    }

}
