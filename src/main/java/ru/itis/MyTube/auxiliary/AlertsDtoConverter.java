package ru.itis.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.Alert;
import ru.itis.MyTube.enums.AlertType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlertsDtoConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(new ConvertiblePair(AlertsDto.class, String.class),
                new ConvertiblePair(String.class, AlertsDto.class)
        );
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType().equals(AlertsDto.class)) {
            AlertsDto alertsDto = (AlertsDto) source;
            return Arrays.stream(alertsDto.alerts())
                    .map(alert -> alert.getType().toString() + "#" + alert.getMessage())
                    .collect(Collectors.joining(" "));

        } else {
            String[] arr = ((String) source).split(" ");
            Alert[] alerts = Arrays.stream(arr)
                    .map(str -> str.split("#"))
                    .map(params -> new Alert(AlertType.valueOf(arr[0]), arr[1]))
                    .toArray(Alert[]::new);
            return new AlertsDto(alerts);
        }
    }
}
