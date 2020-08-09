/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

/**
 *
 * @author lizihao
 */
public class TransCipher {

    static public String DecryptMessage(int key, String message) {
        //建立一个二维数组用来存放密文。
        int numOfColumns = 0;
        int numOfRows = 0;
        int numOfShadedBoxes = 0;
        int messagelen = message.length();

        numOfColumns = (int) Math.ceil((float) messagelen / key);//计算存放密文的二维数组所需的列数。
        numOfRows = key;
//    
        numOfShadedBoxes = (numOfColumns * numOfRows) - messagelen;//存放密文的二维数组最后一行有几个位置是多余的，这几个多余的位置要排除。

        String[][] plaintext;//存放密文的二维数组
        plaintext = new String[numOfColumns][numOfRows];

        int col = 0;
        int row = 0;
        String[] messageArr;
        messageArr = message.split("");//转为字符串数组

        for (int i = 0; i < messagelen; i++) {
            plaintext[col][row] = messageArr[i];
            col += 1;
//
            if ((col == numOfColumns) || (col == numOfColumns - 1 && row >= numOfRows - numOfShadedBoxes)) {
                col = 0;
                row += 1;
            }

        }
        //
        String result = null;
        for (int i = 0; i < numOfColumns; i++) {
            for (int j = 0; j < numOfRows; j++) {
                result += plaintext[i][j];
                //  System.out.print(plaintext[i][j] + " ");
            }
            //  System.out.println();
        }
        result = result.replaceAll("null", "");//删除空字符串
        //System.out.println(result);
        return result;

    }
//** 古典加密，换位加密。将message的字符顺序打乱，以达到加密的目的。 

    static public String EncryptMessage(int key, String message) {

        String ciphertext = "";//存储加密后的文本。
        String[] messageArr;
        messageArr = message.split("");//message转为字符串数组
        for (int i = 0; i < key; i++) {
            int pointer = i;
            //每隔key个字符取出一个，添加到ciphertext末尾，形成新密文。
            while (pointer < message.length()) {

                ciphertext += messageArr[pointer];
                pointer += key;
            }
        }

        ciphertext = ciphertext.replaceAll("null", "");
        //System.out.println(ciphertext);

        return ciphertext;

    }

    public static void main(String[] args) {
        // String Dec = "you are a foolish";//
        String Enc = "apple";
        for (int i = 1; i < 10; i++) {
            String EncryptMessage = EncryptMessage(i, Enc);
            System.out.println(EncryptMessage);

        }

//        String DecryptMessage = DecryptMessage(5, EncryptMessage);
//        System.out.println(DecryptMessage);
    }

}
