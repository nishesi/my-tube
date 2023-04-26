package ru.itis.MyTube.view.peb.filters;

import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import ru.itis.MyTube.view.Alert;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class AlertHandlerFunction implements Function {
    private final List<String> argNames = List.of("alerts");

    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        List<? extends Alert> alerts = (List<? extends Alert>) args.get("alerts");
        if (alerts == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class=\"toast-container position-fixed top-0 end-0 me-5 p-3\">");
        for (Alert alert : alerts) {
            stringBuilder.append("<div class=\"toast show align-items-center border-0 mb-1\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n");
            stringBuilder.append("<div class=\"d-flex alert ").append(alert.getType().getAlertType()).append(" p-0\">\n");
            stringBuilder.append("<div class=\"toast-body\">").append(alert.getMessage()).append("</div>\n");
            stringBuilder.append("<button type=\"button\" class=\"btn-close btn-close me-2 m-auto\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n");
            stringBuilder.append("</div>\n</div>");
        }
        stringBuilder.append("</div>");
        return stringBuilder.toString();
    }

    @Override
    public List<String> getArgumentNames() {
        return argNames;
    }
}
