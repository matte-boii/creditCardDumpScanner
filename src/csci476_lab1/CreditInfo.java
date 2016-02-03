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
        
            for (int i = 0; i < dump.length; i++) {
                if (dump[i].charAt(0) == '%' && dump[i].length() > 30) { //Test for Track 1 Data
                    int j = 2;
                    boolean test1 = false;
                    boolean test2 = false;
                    while (!test1 || !test2){
                        if (dump[i].charAt(j) == '^')
                            test1 = true;
                        j++;
                        if (dump[i].charAt(j) == '?' && j < dump[i].length())
                            test2 = true;
                        if (j == dump[i].length() - 1)
                            break;
                    }
                    if (test1 && test2)
                        if (dump[i].charAt(1) == 'B')
                        track1(dump[i]);
                }
                if (dump[i].charAt(0) == ';') { //Test for Track 2 Data
                    int j = 2;
                    boolean test1 = false;
                    boolean test2 = false;
                    while (test1 && test2){
                        if (dump[i].charAt(j) == '=')
                            test1 = true;
                        j++;
                        if (dump[i].charAt(i) == '?')
                            test2 = true;
                        if (j == dump[i].length() && dump[i].length() > 30)
                            break;
                    }
                    if (test1 && test2)
                        
                            track2(dump[i]);
                }
            }
            
          
            
        
        
//        for (String bit : dump) {
//            if (!bit.equals(".")) {
//                if (alphaIndex == -1) {
//                    alphaIndex = lastIndexOfUCL(bit, 'B');
//                    foundAlphaA = true;
//                } else if (alphaIndex == -1) {
//                    alphaIndex = lastIndexOfUCL(bit, ';');
//                    foundAlphaB = true;
//                }
//                if (alphaIndex != -1 && !"?".equals(end)) {
//                    if (foundAlphaA == true) {
//                        end = findingCardInfo(alphaIndex, bit, "B");
//                    } else if (foundAlphaB == true) {
//                        end = findingCardInfo(alphaIndex, bit, ";");
//                    }
//                } else if ("?".equals(end)) {
//                    if (foundAlphaA == true) {
//                        hashingOutCreditInfo("track1");
//                        creditCardNumber.add("%B" + primaryAccountNumber + "^" + name + "^" + expirationDate + CountryCode + discretionaryData);
//                        foundAlphaA = false;
//                        alphaIndex = -1;
//                        end = "";
//                    }
//                    if (foundAlphaB == true) {
//                        hashingOutCreditInfo("track2");
//                        creditCardNumber.add(";" + primaryAccountNumber + "^" + expirationDate + discretionaryData);
//                        foundAlphaB = false;
//                        alphaIndex = -1;
//                        end = "";
//                    }
//                }
//            }
//        }
        return creditCardNumber;
    }
        
        
     private static void track1(String string) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String account = "";
        String split = "" + (char) 94;
        if (string.charAt(1) == 'B') {
             String[] first = string.split("[^/\\s\\w]"); //[^/\\s\\w]
             if (first.length > 3) {
                 name = first[2];
                 //name = name.replace('/', ' ');
                 account = first[3];
             } else {
                 for (int i = 0; i < first[2].length(); i++) {
                     if ((int) first[2].charAt(i) > 65 || first[2].charAt(i)=='/') { //look for when the characters become numbers
                         name = first[2].substring(0, i+1);
                         //name = name.replace('/', ' ');
                         account = first[2].substring(i+1); //additional account information
                     }
                 }
             }
             primaryAccountNumber = first[1].substring(1); //grab card number information
             //build data from account variable
             expirationDate = account.substring(0, 4); //Date
             CountryCode = account.substring(4, 7); //CVC
             discretionaryData = account.substring(7, 11); //Pin
             String card = "Card #: " + primaryAccountNumber + "; Name: " + name + "; ExpDate: " + expirationDate + "; CVC: " + CountryCode + "; Pin: " + discretionaryData;
             creditCardNumber.add(card);
         }
        
            
        }

    

        private static void track2(String string) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            System.out.println("Will work on later...");
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
            
                if (str.charAt(i) == looking) {
                    return i;
                }
            
        }
        return -1;
    }


    
}
