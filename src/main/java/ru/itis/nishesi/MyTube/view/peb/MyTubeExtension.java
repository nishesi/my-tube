package ru.itis.nishesi.MyTube.view.peb;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.extension.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.enums.VideoCollectionType;
import ru.itis.nishesi.MyTube.view.peb.filters.DurationFilter;
import ru.itis.nishesi.MyTube.view.peb.filters.ViewsFilter;
import ru.itis.nishesi.MyTube.view.peb.filters.WhenAddedFilter;
import ru.itis.nishesi.MyTube.view.peb.functions.GeneratePagesFunction;
import ru.itis.nishesi.MyTube.view.peb.functions.UrlResolverFunction;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MyTubeExtension extends AbstractExtension {
    private final GeneratePagesFunction generatePagesFunction;
    private final WhenAddedFilter whenAddedFilter;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public Map<String, Object> getGlobalVariables() {
        return Map.of(
                "LOGO_URL", contextPath + "/static/images/reg-background-img.jpg",
                "APP_NAME", "MyTube",
                "videoCollections", List.of(VideoCollectionType.values()),
                "contextPath", contextPath);
    }

    @Override
    public Map<String, Filter> getFilters() {
        return Map.of(
                "views", new ViewsFilter(),
                "duration", new DurationFilter(),
                "whenAdded", whenAddedFilter
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.of(
                "generatePages", generatePagesFunction,
                "resolve", new UrlResolverFunction());
    }
}
