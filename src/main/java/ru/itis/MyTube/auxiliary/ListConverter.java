package ru.itis.MyTube.auxiliary;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.view.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(new ConvertiblePair(ArrayList.class, String.class),
                new ConvertiblePair(String.class, ArrayList.class)
        );
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType().equals(ArrayList.class)) {
            List<Alert> list = (List<Alert>) source;
            return list.stream()
                    .map(alert -> alert.getType().toString() + "#" + alert.getMessage())
                    .collect(Collectors.joining(" "));
        } else {
            String[] arr = ((String) source).split(" ");
            return Arrays.stream(arr)
                    .map(str -> str.split("#"))
                    .map(params -> new Alert(Alert.AlertType.valueOf(arr[0]), arr[1]))
                    .toList();
        }
    }
}
