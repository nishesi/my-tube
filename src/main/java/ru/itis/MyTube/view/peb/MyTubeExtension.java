package ru.itis.MyTube.view.peb;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.controllers.UrlPatterns;
import ru.itis.MyTube.view.peb.filters.DurationFilter;
import ru.itis.MyTube.view.peb.filters.ViewsFilter;
import ru.itis.MyTube.view.peb.filters.WhenAddedFilter;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyTubeExtension extends AbstractExtension {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public Map<String, Object> getGlobalVariables() {
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("logoUrl", contextPath + "/static/images/reg-background-img.jpg");
        varMap.put("appName", "MyTube");
        varMap.put("urlPatterns", new UrlPatterns());
        return varMap;
    }

    @Override
    public Map<String, Filter> getFilters() {
        return Map.of(
                "views", new ViewsFilter(),
                "duration", new DurationFilter(),
                "whenAdded", new WhenAddedFilter()
        );
    }
}
