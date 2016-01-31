/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci476_lab1;

import java.io.FileInputStream;
import java.utilities.*;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Will
 */
public class CredInfo {
		private static String creditNumber;
		private static String primaryAccountNumber;
		private static String name;
		private static boolean foundAlpha;
		public static void CreditInfo(String[] dump) {
				   for (String bit : dump){
						   if(!bit.equals(".")){
								   int alphaIndex = lastIndexOfUCL(bit);
								   findingCardInfo(alphaIndex, bit);
						   }
				   }
		   }
		public static int lastIndexOfUCL(String str) {     
        		char B = 'B';   
				    for(int i=0; i<=str.length(); i++) {
				            if(Character.isUpperCase(str.charAt(i))) {
								if(str.charAt(i) == B)
										foundAlpha = true;
										return i;
				            }
				        }
				        return -1;
			    }
		private static void findingCardInfo(int alphaIndex, String bit) {
				String before = "";
				int countPrim = 0;
				bit = bit.substring(alphaIndex, bit.length());
				for(char now : bit.toCharArray()){
						if(foundAlpha && now == '^'){
								
						}
						else if(Character.isAlphabetic(now)){
								name = name + now;
						}								
						else if(foundAlpha && countPrim >19 && Character.isDigit(now)){
								creditNumber = creditNumber + now;
								primaryAccountNumber = primaryAccountNumber + now;
								countPrim++;
						}
						else
								before = now;
				}
		}
}
