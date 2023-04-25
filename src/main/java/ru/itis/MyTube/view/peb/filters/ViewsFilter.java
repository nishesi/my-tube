package ru.itis.MyTube.view.peb.filters;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class ViewsFilter implements Filter {
    private final List<String> argNames = List.of();

    @Override
    public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
        if (input == null)
            return "views not found";
        if (!(input instanceof Long views))
            return "unknown views type";

        if (views >= 1_000_000_000) {
            double blnViews = (double) Math.round(((double) views) / 1_000_000_000 * 10) / 10;
            return blnViews + " billion views";

        } else if (views >= 1_000_000) {
            double mlnViews = (double) Math.round(((double) views) / 1_000_000 * 10) / 10;
            return mlnViews + " million views";

        } else if (views >= 1_000) {
            double thsViews = (double) Math.round(((double) views) / 1_000 * 10) / 10;
            return thsViews + " thousand views";
        }
        return views + " views";
    }

    @Override
    public List<String> getArgumentNames() {
        return argNames;
    }
}
