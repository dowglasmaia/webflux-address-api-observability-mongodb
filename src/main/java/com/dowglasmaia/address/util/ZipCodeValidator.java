package com.dowglasmaia.address.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ZipCodeValidator {
    private static final Pattern CEP_PATTERN = Pattern.compile("^(?<cepPrefix>\\d{5})-(?<cepSuffix>\\d{3})$");

    public static boolean isValidCEP(String cep) {
        if (cep == null || cep.isEmpty()) {
            return false;
        }
        Matcher matcher = CEP_PATTERN.matcher(cep);
        return matcher.matches();
    }
}
