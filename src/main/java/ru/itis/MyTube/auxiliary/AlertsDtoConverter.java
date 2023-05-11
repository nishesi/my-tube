package ru.itis.MyTube.auxiliary;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.dto.Alert;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.enums.AlertType;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlertsDtoConverter implements Formatter<AlertsDto> {

    @Nonnull
    @Override
    public AlertsDto parse(@Nonnull String text, @Nonnull Locale locale) throws ParseException {
        try {
            String[] arr = text.split(" ");
            Alert[] alerts = Arrays.stream(arr)
                    .map(str -> str.split("#"))
                    .map(params -> new Alert(AlertType.valueOf(arr[0]), arr[1]))
                    .toArray(Alert[]::new);
            return new AlertsDto(alerts);

        } catch (RuntimeException ex) {
            throw new ParseException("AlertsDto parse failed: " + ex.getMessage(), 0);
        }
    }

    @Nonnull
    @Override
    public String print(AlertsDto alertsDto, @Nonnull Locale locale) {
        return Arrays.stream(alertsDto.alerts())
                .map(alert -> alert.getType().toString() + "#" + alert.getMessage())
                .collect(Collectors.joining(" "));
    }
}
