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
		private static String CountryCode;
		private static String expirationDate;
		private static String discretionaryData;
		private static boolean foundAlpha;
		private static int alphaIndex = -1;
		
		public static String CreditInfo(String[] dump) {
				   for (String bit : dump){
						   if(!bit.equals(".")){
								if(alphaIndex == -1)
										alphaIndex = lastIndexOfUCL(bit);
								if(alphaIndex != -1)
										findingCardInfo(alphaIndex, bit);
						   }
						   else
								   return null;
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
				char before;
				int primary = 0;
				if(bit.substring(alphaIndex, alphaIndex) == "B")
						bit = bit.substring(alphaIndex, bit.length());
				for(char now : bit.toCharArray()){
						if(foundAlpha && now == '^' && !Character.isDigit(now)){
								if(primary == 19){
										discretionaryData = discretionaryData + now;
								}
								else
										primary = 19;
						}
						else if(Character.isAlphabetic(now)){
								name = name + now;
						}								
						else if(foundAlpha && primary <19 && Character.isDigit(now)){
								creditNumber = creditNumber + now;
								primaryAccountNumber = primaryAccountNumber + now;
								primary++;
						}
						else if(foundAlpha && Character.isDigit(now)){
								CountryCode = CountryCode + now;
						}
						else
								before = now;
				}
		}
}
