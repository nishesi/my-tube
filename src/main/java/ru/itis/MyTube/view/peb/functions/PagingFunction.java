package ru.itis.MyTube.view.peb.functions;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class PagingFunction implements Function {
    private final List<String> argumentNames;

    public PagingFunction() {
        argumentNames = List.of("page", "url");
    }

    private static String generateUrl(int pageNum, int currentPageNum, String url) {
        if (currentPageNum != pageNum) {
            String r = url + "?pageInd=" + (pageNum - 1);
            return "<a href=\"" + r + "\">" + pageNum + "</a>";
        }
        return String.valueOf(pageNum);
    }

    @Override
    public Object execute(
            Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber
    ) throws PebbleException {

        Page<?> page = (Page<?>) args.get("page");
        String url = (String) args.get("url");
        int totalPages = page.getTotalPages();
        int currentPageNum = page.getNumber() + 1;

        if (totalPages < 0) return null;

        List<String> pages = new ArrayList<>();

        for (int i = 1; i <= min(2, totalPages); i++)
            pages.add(generateUrl(i, currentPageNum, url));

        if (currentPageNum > 5 && currentPageNum <= totalPages)
            pages.add("...");

        for (int i = -2; i <= 2; i++) {
            int pageNum = currentPageNum + i;

            if (3 <= pageNum && pageNum <= totalPages - 2)
                pages.add(generateUrl(pageNum, currentPageNum, url));
        }

        if (currentPageNum < totalPages - 4)
            pages.add("...");

        for (int i = max(3, totalPages - 1); i <= totalPages; i++)
            pages.add(generateUrl(i, currentPageNum, url));

        return String.join(", ", pages);
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }
}
