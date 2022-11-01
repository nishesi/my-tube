package ru.itis.MyTube.auxiliary.tags;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class WhenAddedTag extends TagSupport {
    private LocalDateTime addedDate;

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    private final SimpleDateFormat yearsFormat = new SimpleDateFormat("dd.MM.yyyy 'at' mm:hh");

    private final SimpleDateFormat monthsFormat = new SimpleDateFormat(" dd.MM 'at' mm:hh");

    @Override
    public int doStartTag() {
        long millis = Date.from(addedDate.toInstant(ZoneOffset.UTC)).getTime();
        int minutes = (int) ((System.currentTimeMillis() - millis) / 1000 / 60);
        int hours = (minutes / 60);
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;

        try {
            if (years >= 1) {
                pageContext.getOut().print(yearsFormat.format(addedDate));

            } else if (months >= 1) {
                pageContext.getOut().print(monthsFormat.format(addedDate));

            } else if (days >= 1) {
                pageContext.getOut().print(days + " days ago");

            } else if (hours >= 1) {
                pageContext.getOut().print(hours + " hours ago");

            } else if (minutes >= 1){
                pageContext.getOut().print(minutes + " minutes ago");
            } else {
                pageContext.getOut().print("Added just now");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
