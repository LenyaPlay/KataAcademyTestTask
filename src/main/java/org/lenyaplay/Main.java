package org.lenyaplay;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(calc(new Scanner(System.in).nextLine()));
    }

    public static String calc(String input){

        char[] operators = {'+','-','/','*',};

        int operatorIndex = -1;
        char operator = ' ';
        String firstNumber = "";
        String secondNumber = "";

        for(int i = 0; i < input.length(); i++){
            char cur = input.charAt(i);
            if(cur == ' ')
                continue;

            for (char c : operators) {
                if (input.charAt(i) == c) {
                    if (operator != ' ')
                        throw new RuntimeException("Incorrect input");
                    operatorIndex = i;
                    operator = cur;
                }
            }

            if(i < operatorIndex || operatorIndex == -1)
                firstNumber = firstNumber + cur;
            else if(i > operatorIndex)
                secondNumber = secondNumber + cur;
        }


        if(firstNumber.isEmpty() || secondNumber.isEmpty() || operator == ' '){
            throw new RuntimeException("Incorrect input");
        }

        int first = 0;
        int second = 0;

        boolean isArab = true;
        if(firstNumber.charAt(0) >= '0' && firstNumber.charAt(0) <= '9'){
            first = getInteger(firstNumber);
            second = getInteger(secondNumber);
        }

        if(getDigitFromRoman(firstNumber.charAt(0)) != -1){
            first = getIntegerFromRoman(firstNumber);
            second = getIntegerFromRoman(secondNumber);
            isArab = false;
        }

        //clear if need
        System.out.println("first:" + first);
        System.out.println("second:" + second);
        System.out.println("op:" + operator);


        int res = 0;

        if(operator == '+')
            res = (first + second);
        else if(operator == '-')
            res =  (first - second);
        else if(operator == '*')
            res =  (first * second);
        else if(operator == '/')
            res =  (first / second);
        else
            throw new RuntimeException("Incorrect input, need operator for binary operation");


        if(res < 1 && !isArab)
            throw new RuntimeException("Incorrect result, roman number less than zero");

        if(!isArab)
            return convertToRoman(res);

        return res + "";
    }


    private static String convertToRoman(int integer){
        char[] romanDigitsChars = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        char[] romanDigitsIntegers = {1, 5, 10, 50, 100, 500, 1000};

        String roman = "";

        for(int i = romanDigitsIntegers.length-1; i >= 0; i--)
        {
            int count = 0;
            while (integer >= romanDigitsIntegers[i]){
                count++;
                roman += romanDigitsChars[i];
                integer -= romanDigitsIntegers[i];
                if(count > 3) {
                    roman = roman.substring(0, roman.length()-3);
                    roman += romanDigitsChars[i+1];
                }
            }
        }

        return roman;
    }
    private static int getIntegerFromRoman(String number) {
        int second = 0;
        int prevDigit = 0;

        for(int j = 0; j < number.length(); j++){
            int digit = getDigitFromRoman(number.charAt(j));

            if(digit != -1){
                second += digit;
                if(prevDigit < digit)
                    second -= prevDigit * 2;

            } else
                throw new RuntimeException("Incorrect input");

            prevDigit = digit;
        }
        return second;
    }

    private static int getDigitFromRoman(char c){
        char[] romanDigitsChars = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        char[] romanDigitsIntegers = {1, 5, 10, 50, 100, 500, 1000};

        for(int i = 0; i < romanDigitsChars.length; i++)
            if(c == romanDigitsChars[i])
                return romanDigitsIntegers[i];

        return -1;
    }

    private static int getInteger(String number) {
        int res = 0;
        for (int i = 0; i < number.length(); i++){
            if(number.charAt(i) >= '0' && number.charAt(i) <= '9'){
                res = (res * 10) + number.charAt(i) - '0';
            } else
                throw new RuntimeException("Incorrect input");
        }
        return res;
    }
}

