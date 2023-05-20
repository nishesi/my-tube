package ru.itis.nishesi.MyTube.view.peb.functions;

import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;


public class UrlResolverFunction implements Function {
    private final List<String> argumentNames = List.of("mapping", "pathVars", "queryParams");

    @SuppressWarnings("unchecked")
    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        String mapping = (String) args.get("mapping");
        var pathVariables = (Map<String, Object>) args.get("pathVars");
        var queryParams = (Map<String, String>) args.get("queryParams");

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        if (queryParams != null)
            queryParams.forEach(uriComponentsBuilder::queryParam);

        var builder = MvcUriComponentsBuilder.fromMappingName(uriComponentsBuilder, mapping);
        if (pathVariables != null)
            pathVariables.forEach((key, value) -> builder.arg(Integer.parseInt(key), value));
        return context.getVariable("contextPath") + builder.build();
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }
}
