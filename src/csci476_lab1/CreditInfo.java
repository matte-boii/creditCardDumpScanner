/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci476_lab1;

import java.util.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Will
 */
public class CreditInfo {

    private static ArrayList<String> creditCardNumber = new ArrayList<>();
    private static String primaryAccountNumber;
    private static String name = "";
    private static String CountryCode;
    private static String expirationDate;
    private static String discretionaryData;
    private static boolean foundAlphaA;
    private static boolean foundAlphaB;
    private static int alphaIndex = -1;
    private static String rawdata = "";

	public static ArrayList<String> scanCredit(ArrayList<String> input) {
        String end = null;
        String[] dump = new String[input.size()];
        int integer = 0;
        for(String value : input)
        {
        	dump[integer] = value;
        	integer++;
        }
        
        for (String bit : dump) {
            if (!bit.equals(".")) {
                if (alphaIndex == -1) {
                    alphaIndex = lastIndexOfUCL(bit, 'B');
                    foundAlphaA = true;
                } else if (alphaIndex == -1) {
                    alphaIndex = lastIndexOfUCL(bit, ';');
                    foundAlphaB = true;
                }
                if (alphaIndex != -1 && !"?".equals(end)) {
                    if (foundAlphaA == true) {
                        end = findingCardInfo(alphaIndex, bit, "B");
                    } else if (foundAlphaB == true) {
                        end = findingCardInfo(alphaIndex, bit, ";");
                    }
                } else if ("?".equals(end)) {
                    if (foundAlphaA == true) {
                        hashingOutCreditInfo("track1");
                        creditCardNumber.add("%B" + primaryAccountNumber + "^" + name + "^" + expirationDate + CountryCode + discretionaryData);
                        foundAlphaA = false;
                        alphaIndex = -1;
                        end = "";
                    }
                    if (foundAlphaB == true) {
                        hashingOutCreditInfo("track2");
                        creditCardNumber.add(";" + primaryAccountNumber + "^" + expirationDate + discretionaryData);
                        foundAlphaB = false;
                        alphaIndex = -1;
                        end = "";
                    }
                }
            }
        }
        return creditCardNumber;
    }

    private static String hashingOutCreditInfo(String track) {
        String before = "";
        if ("track1".equals(track)) {
            for (char now : rawdata.toCharArray()) {
                if (!Character.isDigit(now) && name.length() < 1) {
                    primaryAccountNumber = primaryAccountNumber + now;
                } else if ((Character.isAlphabetic(now)) && primaryAccountNumber.length() > 0) {
                    name = name + now;
                } else if (Character.isDigit(now) && before.length() < 4) {
                    before = before + now;
                }
                if (before.length() == 4) {
                    if (DateParser(before, "MMYY")) {
                        expirationDate = before;
                    }
                    else 
                        before = before.substring(1, before.length());
                    
                } if (Character.isDigit(now)) {
                    discretionaryData = discretionaryData + now;
                } else if (now == '?') {
                    return "?";
                }

            }
            
        }
        else if ("track2".equals(track))
            for (char now : rawdata.toCharArray()) {
                if (!Character.isDigit(now) && name == null) {
                    primaryAccountNumber = primaryAccountNumber + now;
                } else if (Character.isDigit(now) && before.length() < 4) {
                    before = before + now;
                }
                if (before.length() == 4) {
                    if (DateParser(before, "MMYY")) {
                        expirationDate = before;
                    } 
                } else if (Character.isDigit(now)) {
                    discretionaryData = discretionaryData + now;
                } else if (now == '?') {
                    return "?";
                }

            }
        return null;

    }

    private static boolean DateParser(String value, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                return false;
            }
        } catch (ParseException ex) {
        }
        return true;
    }
    
    

    private static String findingCardInfo(int alphaIndex, String bit, String track) {
        
        if (bit.length() > 10 && alphaIndex < bit.length()) {
            bit = bit.substring(alphaIndex, bit.length() - 1);
        }
        
        for (char now : bit.toCharArray()) {
            if (now == '?') {
                rawdata = rawdata + now;
                return "?";
            } else {
                rawdata = rawdata + now;
            }
        }
        return null;
    }

    public static int lastIndexOfUCL(String str, char looking) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                if (str.charAt(i) == looking) {
                    return i;
                }
            }
        }
        return -1;
    }
}
