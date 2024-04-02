package com.example.online_shopping.Services;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidationService {

    public static boolean containsOnlyLetters(String input) {
        return input.matches("^[a-zA-Z]+$");
    }


    public static boolean hasEmailPattern(String input) {
        return input.matches("^\\S+@\\S+\\.\\S+$");
    }

    public static boolean containsOnlyNumbers(String input) {
        return input.matches("^[0-9]*$");
    }

    public static boolean containsOnlyLettersSpaceHyphen(String input) {
        return input.matches("^[a-zA-Z\\s-]*$");
    }

    public static boolean containsOnlyNumbersOfSize10(String input) {
        return input.matches("^[0-9]{10}$");
    }

    public static boolean containsOnlyAlphanumeric(String input) {
        return input.matches("^[a-zA-Z0-9]+$");
    }

    public static boolean containsOnlyNumbersOfLength(String input) {
        if (containsOnlyNumbers(input)) {
            int number = Integer.parseInt(input);
            return (number >= 1 && number <= 100);
        }
        return false;
    }

    public static boolean isStartBeforeOrEqualEndDate(LocalDate start, LocalDate end) {
        return !start.isAfter(end);
    }

    public static boolean isValidPrice(String input) {
        return input.matches("^[0-9]+\\.[0-9]{2}$");
    }

    public static boolean isValidCardFormat(String input) {
        String cardPattern = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";

        Pattern pattern = Pattern.compile(cardPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isValidExpiryDate(String input) {
        String patternString = "^(0[1-9]|1[0-2])/(2[4-9]|[3-9][0-9])$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            return false;
        }


        String[] parts = input.split("/");
        int inputMonth = Integer.parseInt(parts[0]);
        int inputYear = Integer.parseInt(parts[1]);

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR) % 100;
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;

        if (inputYear > currentYear || (inputYear == currentYear && inputMonth >= currentMonth)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidCVV(String input) {
        String patternString = "^\\d{3}$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


}
