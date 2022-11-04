package ru.itis.MyTube.auxiliary.validators;

import java.util.regex.Pattern;

public class SearchValidator {

    private static final Pattern pattern = Pattern.compile("[A-Za-zА-Яа-я!?,.()]+");

    public void validate(String search) {
        String mess;
        if (search == null || search.equals("")) {
            mess = "search string is void";

        } else if (!pattern.matcher(search).matches()) {
            mess = "строка содержит служебные символы";

        } else {
            return;
        }

        throw new IllegalArgumentException(mess);
    }
}
