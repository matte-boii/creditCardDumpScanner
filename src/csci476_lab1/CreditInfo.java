/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci476_lab1;

import java.util.*;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Will
 */
public class CreditInfo {

    private static String[] creditCardNumber = null;
    private static String primaryAccountNumber = null;
    private static String name = null;
    private static String CountryCode = null;
    private static String expirationDate = null;
    private static String discretionaryData = null;
    private static boolean foundAlphaA;
    private static boolean foundAlphaB;
    private static int alphaIndex = -1;
    private static int foundNum = 0;
    private static String rawdata = null;

	public static String[] scanCredit(ArrayList<String> input) {
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
                if (alphaIndex != -1 && end != "?") {
                    if (foundAlphaA == true) {
                        end = findingCardInfo(alphaIndex, bit, "B");
                    } else if (foundAlphaB == true) {
                        end = findingCardInfo(alphaIndex, bit, ";");
                    }
                } else if ("?".equals(end)) {
                    if (foundAlphaA == true) {
                        hashingOutCreditInfo("track1");
                        creditCardNumber[foundNum] = "%B" + primaryAccountNumber + "^" + name + "^" + expirationDate + CountryCode + discretionaryData;
                        foundNum++;
                        foundAlphaA = false;
                        end = null;
                    }
                    if (foundAlphaB == true) {
                        hashingOutCreditInfo("track2");
                        creditCardNumber[foundNum] = "%" + primaryAccountNumber + "^" + name + "^" + expirationDate + CountryCode + discretionaryData;
                        foundNum++;
                        foundAlphaB = false;
                        end = null;
                    }
                }
            }
        }
        return creditCardNumber;
    }

    private static String hashingOutCreditInfo(String track) {
        String before = null;
        if (track == "track1") {
            for (char now : rawdata.toCharArray()) {
                if (!Character.isDigit(now) && name == null) {
                    primaryAccountNumber = primaryAccountNumber + now;
                } else if ((Character.isAlphabetic(now) || now == '\'') && primaryAccountNumber != null) {
                    name = name + now;
                } else if (Character.isDigit(now) && before.length() < 4) {
                    before = before + now;
                }
                if (before.length() == 4) {
                    if (DateParser(before, "MMYY")) {
                        expirationDate = before;
                    } else {
                        continue;
                    }
                } else if (Character.isDigit(now)) {
                    discretionaryData = discretionaryData + now;
                } else if (now == '?') {
                    return "?";
                }

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
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private static String findingCardInfo(int alphaIndex, String bit, String track) {
        if (bit.substring(alphaIndex, alphaIndex) == track) {
            bit = bit.substring(alphaIndex, bit.length());
        }
        for (char now : bit.toCharArray()) {
            if (now == 'y') {
                rawdata = rawdata + now;
                return "?";
            } else {
                rawdata = rawdata + now;
            }
        }
        return null;
    }

    public static int lastIndexOfUCL(String str, char looking) {
        for (int i = 0; i <= str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                if (str.charAt(i) == looking) {
                    return i;
                }
            }
        }
        return -1;
    }
}
