package ru.itis.MyTube.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.MyTube.entities.Content;
import ru.itis.MyTube.enums.AgeCategory;

import java.io.Serializable;

public interface ContentRepository<T extends Content, ID extends Serializable> {
    /**
     * Method only for use in implementations.
     * @param ageCategory Age Category
     * @param pageable page information
     * @param clazz target class
     * @return page of special entities
     */

    Page<? extends T> findByAgeCategory(AgeCategory ageCategory, Pageable pageable, Class<T> clazz);
}
