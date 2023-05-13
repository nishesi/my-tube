package ru.itis.MyTube.repositories.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import ru.itis.MyTube.entities.Content;
import ru.itis.MyTube.enums.AgeCategory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ContentRepositoryImpl<T extends Content, ID extends Serializable> implements ContentRepository<T, ID> {

    private final EntityManager entityManager;

    private final Map<Class<?>, JpaEntityInformation<?, ?>> informationMap;

    public ContentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        informationMap = new ConcurrentHashMap<>();
    }

    @Override
    public Page<T> findByAgeCategory(AgeCategory ageCategory, Pageable pageable, Class<T> clazz) {
        JpaEntityInformation<?, ?> information = getJpaEntityInformation(clazz);
        Query query = entityManager
                .createQuery("select c from " + information.getEntityName() + " c where c.ageCategory = :ageCategory")
                .setParameter("ageCategory", ageCategory)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<T> resultList = (List<T>) query.getResultList();

        Query query1 = entityManager
                .createQuery("select count(*) from " + information.getEntityName() + " c where c.ageCategory = :ageCategory")
                .setParameter("ageCategory", ageCategory);
        Long total = (Long) query1.getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    private JpaEntityInformation<?, ?> getJpaEntityInformation(Class<T> clazz) {
        JpaEntityInformation<?, ?> information = informationMap.getOrDefault(clazz, null);
        if (information == null) {
            information = JpaEntityInformationSupport.getEntityInformation(clazz, entityManager);
            informationMap.put(clazz, information);
        }
        return information;
    }
}
