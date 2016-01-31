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

/**
 *
 * @author darnesey
 */
public class DumpReader {

    static FileInputStream read;
    static FileWriter write;
    static String file;
    public DumpReader(String file) throws FileNotFoundException, IOException {
        this.read = new FileInputStream(file);
        this.file = file;
        write = new FileWriter("outputAC.txt");
        dumpDecrypt();
    }
    
    public static void dumpDecrypt() throws IOException{
        int i = 0;
        String s = "";
        int nonsense = 0;
        int[] code = new int[32];
        while (read.available() > 0){
            while (i < 32) {
                code[i] = read.read();
                if (code[i] < 127 && code[i] > 32) { //avoid special keys
                    s += (char) code[i];
                    nonsense = 0;
                } else {
                    nonsense++;
                    if (nonsense == 4){
                            System.out.print("..");
                            write.write(".");
                            nonsense = 0;
                    }
                }
                code[i] = 0;
                i++;
            }
            //System.out.print(s + "\n");
            i = 0;
            
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
            System.out.print(s + "\n");
            write.write(s + "\n");
            i = 0;
            s = "";
        }
        write.close();
    }
    
}
