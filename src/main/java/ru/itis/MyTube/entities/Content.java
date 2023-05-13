package ru.itis.MyTube.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.MyTube.enums.AgeCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Content {
    @Enumerated(EnumType.STRING)
    protected AgeCategory ageCategory = AgeCategory.EVERYONE;
}
