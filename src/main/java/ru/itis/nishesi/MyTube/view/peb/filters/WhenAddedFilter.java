package ru.itis.nishesi.MyTube.view.peb.filters;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WhenAddedFilter implements Filter {
    private final List<String> argNames = List.of();
    private final HttpSession session;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'в' HH:mm");
    @Override
    public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
        if (input == null)
            return "date not found";
        if (!(input instanceof ZonedDateTime addedDate))
            return "unknown date type";

        if (session.getAttribute("zoneId") == null)
            return addedDate.toString();

        var currentTime = ZonedDateTime.now((ZoneId) session.getAttribute("zoneId"));
        long sec = Duration.between(addedDate, currentTime).getSeconds();
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
