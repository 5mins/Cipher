/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crack;

import Encryption.MixCipher;
import UI.mainUi;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author lizihao
 */
public class MixHacker {

    public void hackTransposition(String myMessage) throws UnsupportedEncodingException {
        DetectEnglish det = new DetectEnglish();
        int count = 0;
        for (int i = 1; i < (myMessage.length() / 2 + 1); i++) {
            String DecryptMessage = MixCipher.MixDecrypt(i, myMessage, 1);
            if (det.isEnglish(DecryptMessage, 60, 80)) {
                count++;
                System.out.println(DecryptMessage);
                //System.out.println("success!");
            }
        }
        if (count == 0) {
            System.out.println("Fail!");

        } else if (count > 0) {
            System.out.printf("success find out %d decryptmessage!", count);

        } else {
            System.out.println("unknow error!");
        }
    }
//matchTimes 匹配次数。当匹配成功了matchTimes次，就退出
    static public String[] hackTransposition_New(int Start, int End, String myMessage,int matchTimes) throws UnsupportedEncodingException {
        DetectEnglish det = new DetectEnglish();
         int count = 0;
        //int i = Start;
        ArrayList<String> Decryptemp = new ArrayList<>();
        for (; Start < End; Start++) {
            String DecryptMessage = MixCipher.MixDecrypt(Start, myMessage, 1);

            //显示正在进行第i次破解
            String messagetemp = "正在进行第" + Start + "次破解\n";
            mainUi.addCrackTextArea(messagetemp);
            if (det.isEnglish(DecryptMessage, 60, 70)) {
                  count++;
                Decryptemp.add(DecryptMessage);
                // mainUi.addCrackTextArea(DecryptMessage);
                //System.out.println(DecryptMessage);
                //System.out.println("success!");
                if(count>matchTimes){
                break;
                }
            }
        }

        String[] retrunArray = Decryptemp.toArray(new String[Decryptemp.size()]);

        return retrunArray;

    }

    static public void hackTranspositionWithUi(String myMessage) throws UnsupportedEncodingException {
///(myMessage.length() / 2 + 1)
        String[] hackTransposition_New = hackTransposition_New(1,21000, myMessage,0);
        int count = hackTransposition_New.length;
        if (count == 0) {

            mainUi.setCrackTextArea("");//清空显示
            mainUi.setCrackTextArea("破解失败！" + "\n" + myMessage);
            System.out.println("Fail!");

        } else if (count > 0) {
            mainUi.setCrackTextArea("");//清空显示
            mainUi.addCrackTextArea("破解成功！" + "\n" + myMessage + "\n\n");
            mainUi.addCrackTextArea("共找到" + count + "个原文：" + "\n");
            for (int i = 0; i < count; i++) {
                String get = hackTransposition_New[i];
                mainUi.addCrackTextArea("第" +(i+1)+ "个：\n");
                mainUi.addCrackTextArea(get + "\n\n");
            }

            System.out.printf("success find out %d decryptmessage!", count);

        } else {
            mainUi.setCrackTextArea("");//清空显示
            mainUi.setCrackTextArea("未知错误！" + "\n" + myMessage);
            System.out.println("unknow error!");
        }

    }

    public static void main(String[] args) {
        String myMessage = "%!kopysry+|+yv}";
        MixHacker hack = new MixHacker();
       // hack.hackTransposition(myMessage);

    }

}
