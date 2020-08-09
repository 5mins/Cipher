/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crack;

import Encryption.TransCipher;

/**
 *
 * @author lizihao
 */
public class transpositionHacker {

    public void hackTransposition(String myMessage) {
        DetectEnglish det = new DetectEnglish();
        for (int i = 1; i < (myMessage.length()/2+1); i++) {
            String DecryptMessage = TransCipher.DecryptMessage(i, myMessage);
            if (det.isEnglish(DecryptMessage, 60, 80)) {
                System.out.println(DecryptMessage);
                System.out.println("success!");
            }
        }
    }

    public static void main(String[] args) {
        String myMessage = "We f tf  e u nri eoiv oo oaeooekl  eeemsI uns ogh lnps   uuewowrntice bP Pdi ansnfcepn hn fkn   ftc epitatiaawease osogfns afii hrhrtorariseoaec hne halb   bwnaoeapro  hvlcldti isifooetin ftiesa hoinec xrrnew  rwadyrlutoioi i  enit elke  nmnso or  ntaetn tpoekddjeatdyt heashs  iopholtlrvdis-sruryndtaga enadtmb   .";
        transpositionHacker hack = new transpositionHacker();
        hack.hackTransposition(myMessage);

    }

}
