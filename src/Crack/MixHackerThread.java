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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lizihao
 */
public class MixHackerThread extends Thread {

    private boolean allowAddCrackTextArea = true;

    private void addCrackTextArea(String s) {

        if (allowAddCrackTextArea) {
            mainUi.addCrackTextArea(s);
        }

    }

    private void setCrackTextArea(String s) {

        if (allowAddCrackTextArea) {
            mainUi.setCrackTextArea(s);
        }

    }

    private void setjLabelHacker(String s) {

        if (allowAddCrackTextArea) {
            mainUi.setjLabelHacker(s);
        }

    }

    public void setNotAllowAddCrackTextArea() {

        allowAddCrackTextArea = false;
    }

//matchTimes 匹配次数。当匹配成功了matchTimes次，就退出
    public String[] hackTransposition_New(int Start, int End, String myMessage, int matchTimes) throws UnsupportedEncodingException {
        String[] retrunArray = null;
        DetectEnglish det = new DetectEnglish();
        if (det.getFlagDictionaryIsLoad()) {
            int count = 0;
            //int i = Start;
            ArrayList<String> Decryptemp = new ArrayList<>();
            for (; Start < End; Start++) {
                String DecryptMessage = MixCipher.MixDecrypt(Start, myMessage, 1);

                //显示正在进行第i次破解
                String messagetemp = "正在进行第" + Start + "次破解\n";
                setjLabelHacker(messagetemp);
                if (det.isEnglish(DecryptMessage, 60, 70)) {
                    count++;
                    Decryptemp.add(DecryptMessage);
                    // mainUi.addCrackTextArea(DecryptMessage);
                    //System.out.println(DecryptMessage);
                    //System.out.println("success!");
                    if (count > matchTimes) {
                        // return null;
                        break;
                    }

                }

                if (Thread.currentThread().isInterrupted()) {
                    //处理中断逻辑
                    //return null;

                    System.out.println("Interrupted!");
                    break;
                }
            }

            retrunArray = Decryptemp.toArray(new String[Decryptemp.size()]);

            return retrunArray;

        }
        return retrunArray;

    }

    public void hackTranspositionWithUi(String myMessage) throws UnsupportedEncodingException {
///(myMessage.length() / 2 + 1)
        String[] hackTransposition_New = hackTransposition_New(1, 21000, myMessage, 0);
        int count = -1;
        if (hackTransposition_New != null) {
            count = hackTransposition_New.length;
        }

        if (count == 0) {

            setjLabelHacker("破解失败!");//
            setCrackTextArea(myMessage);
            System.out.println("Fail!");

        } else if (count > 0) {
            setjLabelHacker("破解成功！");//
            addCrackTextArea("破解成功！" + "\n" + myMessage + "\n\n");
            addCrackTextArea("共找到" + count + "个原文：" + "\n");
            for (int i = 0; i < count; i++) {
                String get = hackTransposition_New[i];
                addCrackTextArea("第" + (i + 1) + "个：\n");
                addCrackTextArea(get + "\n\n");
            }

            System.out.printf("success find out %d decryptmessage!", count);

        } else {
            setjLabelHacker("未知错误！");//清空显示
            setCrackTextArea(myMessage);
            System.out.println("unknow error!");
        }

    }

    @Override
    public void run() {
        super.run();
        String hackerTextArea = mainUi.getHackerTextArea();
        try {
            hackTranspositionWithUi(hackerTextArea);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MixHackerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainUi.setHackerButtonAllow();

//        while (true) {
//            if (Thread.currentThread().isInterrupted()) {
//                System.out.println("Yes,I am interruted,but I am still running");
//                //  return;
//
//            } else {
//                System.out.println("not yet interrupted");
//            }
//
//        }
    }

    public static void main(String[] args) throws InterruptedException {
        String myMessage = "123";//%!kopysry+|+yv}
//        MixHackerThread hack = new MixHackerThread();
//        hack.start();
//        Thread.sleep(200);
//        hack.interrupt();

    }

}
