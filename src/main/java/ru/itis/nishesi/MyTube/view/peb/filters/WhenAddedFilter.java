package ru.itis.nishesi.MyTube.view.peb.filters;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class WhenAddedFilter implements Filter {
    private final List<String> argNames = List.of();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'в' HH:mm");
    @Override
    public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
        if (input == null)
            return "date not found";
        if (!(input instanceof LocalDateTime addedDate))
            return "unknown date type";

        long sec = Duration.between(addedDate, LocalDateTime.now()).getSeconds();
        int minutes = (int) sec / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        int months = days / 30;
        int years = months / 12;

        String result;
        if (years >= 1) {
            result = formatter.format(addedDate);

        } else if (months >= 1) {
            result = formatter.format(addedDate);

        } else if (days >= 1) {
            result = days + " days ago";

        } else if (hours >= 1) {
            result = hours + " hours ago";

        } else if (minutes >= 1) {
            result = minutes + " minutes ago";
        } else {
            result = "Added just now";
        }
        return result;
    }

    @Override
    public List<String> getArgumentNames() {
        return argNames;
    }
}
