/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci476_lab1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import csci476_lab1.CredInfo;

/**
 *
 * @author darnesey, will
 */
public class CSCI476_Lab1 {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws FileNotFoundException, IOException {
	// write your code here

        DumpReader dump = new DumpReader(args[1]);
        String[] credit = new CredInfo.CreditInfo(dump);
        
//        FileInputStream read = new FileInputStream(args[0]);
//        FileWriter write = new FileWriter("output.txt");
//        int i = 0;
//        String s = "";
//        int nonsense = 0;
//        int[] code = new int[32];
//        while (read.available() > 0){
//            while (i < 32) {
//                code[i] = read.read();
//                if (code[i] < 127 && code[i] > 32) { //avoid special keys
//                    s += (char) code[i];
//                    nonsense = 0;
//                } else {
//                    nonsense++;
//                    if (nonsense == 4){
//                            System.out.print("..");
//                            write.write(".");
//                            nonsense = 0;
//                    }
//                }
//                code[i] = 0;
//                i++;
//            }
//            //System.out.print(s + "\n");
//            i = 0;
//            
//            while (i < 32) {
//                code[i] = read.read();
//                if (code[i] < 127 && code[i] > 32) { //avoid special keys
//                    s += (char) code[i];
//                    nonsense = 0;
//                } else {
//                    nonsense++;
//                    if (nonsense == 4) {
//                            System.out.print(".");
//                            write.write(".");
//                            nonsense = 0;
//                    }
//                }
//                code[i] = 0;
//                i++;
//            }
//            System.out.print(s + "\n");
//            write.write(s + "\n");
//            i = 0;
//            s = "";
//        }
        //reader.
        //String n = reader.next();
        
        
        
//        byte[] biting = s.getBytes();
//        
//        for (int j = 0; j < biting.length; j++) {
//            System.out.println(", " + biting[i]);
//       }
//        System.out.println(s);

    }
    
}
