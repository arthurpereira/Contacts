package br.com.arthurpereira.contacts.util;

import org.apache.commons.lang3.text.WordUtils;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;

public class StringUtil {

    public static String formatName(String text){
        text = removeUnnecessarySpaces(text);
        text = capitalize(text);

        return text;
    }

    public static String removeUnnecessarySpaces(String text) {
        return text.trim().replaceAll(" +", " ");
    }

    private static String capitalize(String text) {
        return WordUtils.capitalize(text);
    }

    public static String generateToken(int length) {
        return new BigInteger(length * 5, new SecureRandom()).toString(32);
    }

    public static String formatPhoneNumber(String number) {
        JFormattedTextField formatPhoneNumber = new JFormattedTextField();

        if (number.matches("^\\d*$")) {
            switch (number.length()){
                case 7:
                    try {
                        formatPhoneNumber = new JFormattedTextField(new MaskFormatter("###-####"));
                        formatPhoneNumber.setText(number);
                    } catch (ParseException ex) {}
                    break;

                case 8:
                    try {
                        formatPhoneNumber = new JFormattedTextField(new MaskFormatter("####-####"));
                        formatPhoneNumber.setText(number);
                    } catch (ParseException ex) {}
                    break;

                case 9:
                    try {
                        formatPhoneNumber = new JFormattedTextField(new MaskFormatter("#####-####"));
                        formatPhoneNumber.setText(number);
                    } catch (ParseException ex) {}
                    break;

                case 10:
                    if (number.matches("0{1}[1-9]{1}0{2}[0-9]{6}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("#### ###-###"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }else if (number.matches("0{1}[0-9]{9}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(###) ###-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }else{
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }
                    break;

                case 11:
                    if (number.matches("0{1}[1-9]{1}0{2}[0-9]{7}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("#### ###-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }else if (number.matches("0{1}[0-9]{10}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(###) ####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    } else{
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }
                    break;

                case 12:
                    if (number.matches("0{1}[1-9]{1}0{2}[0-9]{8}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("#### ####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }else if (number.matches("0{1}[0-9]{11}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(###) #####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }else{
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(## ##) ####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException ex) {}
                    }
                    break;

                case 13:
                    if (number.matches("0{1}[0-9]{12}")){
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(### ##) ####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException exx) {}
                    }else{
                        try {
                            formatPhoneNumber = new JFormattedTextField(new MaskFormatter("(## ##) #####-####"));
                            formatPhoneNumber.setText(number);
                        } catch (ParseException exx) {}
                    }
                    break;

                default:
                    formatPhoneNumber.setText(number);
                    break;
            }
        } else {
            formatPhoneNumber.setText(number);
        }

        return formatPhoneNumber.getText();
    }
    
}
