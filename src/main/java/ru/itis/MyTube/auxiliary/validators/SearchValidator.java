package ru.itis.MyTube.auxiliary.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SearchValidator {

    private static final Pattern pattern = Pattern.compile("[A-Za-zА-Яа-я0-9!?,.)( #:\"-]+");

    public void validate(String search) {
        String mess;
        if (search == null || search.equals("")) {
            mess = "Search string is void.";

        } else if (!pattern.matcher(search).matches()) {
            mess = "The string contains service characters.";

        } else {
            return;
        }

        throw new IllegalArgumentException(mess);
    }
}
