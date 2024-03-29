package ru.itis.nishesi.MyTube.repositories.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import ru.itis.nishesi.MyTube.entities.Content;
import ru.itis.nishesi.MyTube.enums.AgeCategory;

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

    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findByAgeCategory(AgeCategory ageCategory, Pageable pageable, Class<T> clazz) {
        JpaEntityInformation<?, ?> information = getJpaEntityInformation(clazz);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<?> root = criteriaQuery.from(information.getJavaType());

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("ageCategory"), ageCategory));

        TypedQuery typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        List<T> resultList = (List<T>) typedQuery.getResultList();

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
