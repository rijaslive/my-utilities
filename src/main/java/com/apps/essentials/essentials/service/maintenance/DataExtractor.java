package com.apps.essentials.essentials.service.maintenance;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RiJAS
 * @Date 21-05-2024
 *
 */
@Service
@Slf4j
public class DataExtractor {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

    public Map<String, Object> processData(String dataFromImage, Integer amount, String message) throws IOException {
        /**
         * Transaction Successful
         * 08:17 pm on 21 May 2024
         * Paid to
         * Jairaj Apartments Maintenance Account 2,907
         * XXXXXXX1340
         * State Bank of India
         * Transfer Details “A
         * Message
         * C3, Pacific Spring Maintenance charge for April 2024
         * Transaction ID
         * 72405212017026772208839
         * Debited from
         * QO XXXXXXXX1185 2,907
         * UTR: 414229961564
         * Powered by
         * LIA)? VES BANK
         */
        Map<String,Object> data = new LinkedHashMap<>();

        LocalDate now = LocalDate.now();
        Month paymentMonth = now.minusMonths(1).getMonth();
        String paymentYear = String.valueOf(now.getYear());
        if(paymentMonth.equals(Month.DECEMBER)){
            paymentYear = String.valueOf(now.minusYears(1).getYear());
        }

        String paymentMonthStr = StringUtils.capitalize(paymentMonth.toString().toLowerCase());

        data.put("paymentMonth",paymentMonthStr);
        data.put("paymentYear",paymentYear);


        String amountRegex = "\\d.*,\\d.*";
        String specialCharRegex = "[^a-zA-Z0-9\\s]";


        Pattern pattern = Pattern.compile("(?m)^[ \t]*\r?\n");

        // Create a Matcher object to match the regular expression against the string.
        Matcher matcher = pattern.matcher(dataFromImage);

        // Replace all empty lines with an empty string.
        dataFromImage = matcher.replaceAll("");


        String[] lines = StringUtils.split(dataFromImage, "\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if(StringUtils.contains(line,"Transaction Successful")){
                String transactionDateTime = lines[i+1];
                String datePart = transactionDateTime.substring(transactionDateTime.indexOf("on") + 3);
                LocalDate localDate = LocalDate.parse(datePart, dateFormatter);
                Month month = localDate.getMonth().minus(1);
                String forMonth = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                data.put("forMonth", forMonth);
                data.put("transactionDateTime", transactionDateTime);
            }


            if(StringUtils.contains(line,"Paid to")){
                String accountNameAndAmount = lines[i+1];
                String toBankAccount = lines[i+2];

                String toAccountName = RegExUtils.removeAll(accountNameAndAmount, amountRegex);
                toAccountName = RegExUtils.removeAll(toAccountName, specialCharRegex);
                data.put("toAccountName", toAccountName.trim());
                data.put("toBankAccount",toBankAccount.trim());


                // Use replaceFirst to manipulate the string and extract the match
                String firstMatch = accountNameAndAmount.replaceFirst(".*?(" + amountRegex + ").*", "$1");

                // Check if the match is not the entire string
                if (firstMatch.equals(accountNameAndAmount)) {
                    firstMatch = ""; // No match found
                }
                data.put("maintenanceCharge", amount==null?firstMatch.trim(): String.valueOf(amount));
            }
            if(StringUtils.contains(line,"Message")){
                String messageFromFile = lines[i+1];
                data.put("message", messageFromFile.trim());
            }else{
                String forMonth = data.get("forMonth").toString();
                data.put("message",  String.format("C3, Pacific Spring Maintenance charges for %s %s",StringUtils.capitalize(String.valueOf(forMonth).toLowerCase()), paymentYear));
            }

            if(StringUtils.contains(line,"Transaction ID")){
                String transactionId = lines[i+1];
                data.put("transactionId", transactionId.trim());
            }
            if(StringUtils.contains(line,"UTR: ")){
                String utrLine = lines[i];
                data.put("utr", utrLine.replace("UTR:","").trim());
            }
        }

//        String successImage = getSuccessImage();
//        data.put("successImage",successImage);

        return data;
    }


    private String getSuccessImage() throws IOException {


        byte[] imgBytes = IOUtils.toByteArray(getClass().getResourceAsStream("/check.png"));
        String imgDataAsBase64 = new String( Base64.getEncoder().encode(imgBytes));
        String logo = "data:image/png;base64," + imgDataAsBase64;
        return logo;
    }

}
