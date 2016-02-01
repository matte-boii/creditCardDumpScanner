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
		private static String[] creditCardNumber;
		private static String primaryAccountNumber;
		private static String name;
		private static String CountryCode;
		private static String expirationDate;
		private static String discretionaryData;
		private static boolean foundAlphaA;
		private static boolean foundAlphaB;
		private static int alphaIndex = -1;
		private static int foundNum = 0;
		
		public static String CreditInfo(String[] dump) {
				String end;
				   for (String bit : dump){
						   if(!bit.equals(".")){
								if(alphaIndex == -1){
										alphaIndex = lastIndexOfUCL(bit, 'B');
										foundAlphaA = true;}
								else if(alphaIndex == -1){
										alphaIndex = lastIndexOfUCL(bit, '%');
										foundAlphaB = true;}
								if(alphaIndex != -1 && end != "?"){
										if(foundAlphaA == true)
												end = findingCardInfoTrack1B(alphaIndex, bit);
										else if(foundAlphaB == true){
												end =findingCardInfoTrack2(alphaIndex, bit);
										}
								}
								else if(end == "?"){
    									if(foundAlphaA == true){
    										   creditCardNumber[foundNum ] = "%B"+primaryAccountNumber+"^"+name+"^"+expirationDate+CountryCode+discretionaryData;
    										   foundNum++;
    										   foundAlphaA = false;
    										   end = null;
    								   }
    								   if(foundAlphaB == true){
    										   creditCardNumber[foundNum ] = "%"+primaryAccountNumber+"^"+name+"^"+expirationDate+CountryCode+discretionaryData;
    										   foundNum++;
    										   foundAlphaB = false;
    										   end = null;
    								   }
								   }
						   }								  
				   }
		   }
		private static String findingCardInfoTrack2(int alphaIndex, String bit) {
			char before;
			int primary = 0;
			if(bit.substring(alphaIndex, alphaIndex) == "B")
				bit = bit.substring(alphaIndex, bit.length());
    		for(char now : bit.toCharArray()){
    				if(foundAlphaB && now == '^' && !Character.isDigit(now)){
    						if(primary == 19 && now != '?'){
    								discretionaryData = discretionaryData + now;
    						}
    						else if(now != '?')
    								return "?";
    						else
    								primary = 19;
    				}
    				else if(Character.isAlphabetic(now)){
    						name = name + now;
    				}								
    				else if(foundAlphaB && primary <19 && Character.isDigit(now)){
    						primaryAccountNumber = primaryAccountNumber + now;
    						primary++;
    				}
    				else if(foundAlphaB && Character.isDigit(now)){
    						CountryCode = CountryCode + now;
    				}
    				else
    						before = now;
    		}	
		}
		public static int lastIndexOfUCL(String str, char looking) {     
				    for(int i=0; i<=str.length(); i++) {
				            if(Character.isUpperCase(str.charAt(i))) {
								if(str.charAt(i) == looking)
										return i;
				            }
				        }
				        return -1;
			    }
		private static String findingCardInfoTrack1B(int alphaIndex, String bit) {
				char before;
				int primary = 0;
				if(bit.substring(alphaIndex, alphaIndex) == "B")
						bit = bit.substring(alphaIndex, bit.length());
				for(char now : bit.toCharArray()){
						if(foundAlphaA && now == '^' && !Character.isDigit(now)){
								if(primary == 19 && now != '?'){
	    								discretionaryData = discretionaryData + now;
	    						}
	    						else if(now != '?')
	    								return "?";
	    						else
	    								primary = 19;
						}
						else if(Character.isAlphabetic(now)){
								name = name + now;
						}								
						else if(foundAlphaA && primary <19 && Character.isDigit(now)){
								primaryAccountNumber = primaryAccountNumber + now;
								primary++;
						}
						else if(foundAlphaA && Character.isDigit(now)){
								CountryCode = CountryCode + now;
						}
						else
								before = now;
				}
		}
}
