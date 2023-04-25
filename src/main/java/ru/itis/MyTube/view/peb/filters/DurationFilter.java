package ru.itis.MyTube.view.peb.filters;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class DurationFilter implements Filter {
    private final List<String> argNames = List.of();

    @Override
    public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
        if (input == null)
            return "duration not found";
        if (!(input instanceof Duration duration))
            return "unknown duration type";

        return duration.toHoursPart() + ":" + duration.toMinutesPart() + ":" + duration.toSecondsPart();
    }

    @Override
    public List<String> getArgumentNames() {
        return argNames;
    }
}
