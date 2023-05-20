package ru.itis.nishesi.MyTube.view.peb;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.extension.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.controllers.UrlPatterns;
import ru.itis.nishesi.MyTube.enums.VideoCollectionType;
import ru.itis.nishesi.MyTube.view.peb.filters.DurationFilter;
import ru.itis.nishesi.MyTube.view.peb.filters.ViewsFilter;
import ru.itis.nishesi.MyTube.view.peb.filters.WhenAddedFilter;
import ru.itis.nishesi.MyTube.view.peb.functions.GeneratePagesFunction;
import ru.itis.nishesi.MyTube.view.peb.functions.UrlResolverFunction;

import java.util.List;
import java.util.Map;

@Component
public class MyTubeExtension extends AbstractExtension {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public Map<String, Object> getGlobalVariables() {
        return Map.of(
                "LOGO_URL", contextPath + "/static/images/reg-background-img.jpg",
                "APP_NAME", "MyTube",
                "urlPatterns", new UrlPatterns(),
                "videoCollections", List.of(VideoCollectionType.values()),
                "contextPath", contextPath);
    }

    @Override
    public Map<String, Filter> getFilters() {
        return Map.of(
                "views", new ViewsFilter(),
                "duration", new DurationFilter(),
                "whenAdded", new WhenAddedFilter()
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.of(
                "generatePages", new GeneratePagesFunction(),
                "resolve", new UrlResolverFunction());
    }
}
