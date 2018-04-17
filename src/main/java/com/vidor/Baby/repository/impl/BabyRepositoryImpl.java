package com.vidor.Baby.repository.impl;

import com.vidor.Baby.entity.Baby;
import com.vidor.Baby.repository.BabyRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BabyRepositoryImpl implements BabyRepositoryCustom{

    private static final Logger logger = LoggerFactory.getLogger(BabyRepositoryImpl.class);

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String sayHello() {
        return "1111111111111111";
    }

    @Override
    public List<Object[]> groupByBabyAsSql() {
        List<Object[]> list = entityManager
                .createNativeQuery("select age,count(*) from baby group by age")//表名
                .getResultList();

        return list;
    }

    @Override
    public List<Object[]> groupByBabyAsHql() {
        List<Object[]> list = entityManager
                .createQuery("select age,count(*) from Baby group by age") //对应的类名
                .getResultList();
        return list;
    }

    @Override
    public List<Object[]> groupBybabyAsSpecification(Integer number) {
        //根据年龄分组查询，并且Baby数量大于3的所有
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Baby> root = query.from(Baby.class);
        query.multiselect(root.get("age"),builder.count(root.get("id")))
                .groupBy(root.get("age")).having(builder.gt(builder.count(root.get("id")),number));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * List cache
     * @param age
     * @return
     */
    @Override
    public List<Baby> findByAgeCache(Integer age) {
        List<Baby> babies = entityManager.createQuery(
                "select p " +
                        "from Baby p " +
                        "where p.age = :age", Baby.class)
                .setParameter( "age", age)
                .setHint( "org.hibernate.cacheable", "true")
                .getResultList();
        return babies;
    }

    /**
     * Entity cache
     * @param id
     * @return
     */
    @Override
    public Baby findByIdCache(Integer id) {
        Baby baby = entityManager.find(Baby.class, id);
        redisTemplate.opsForValue().set(id.toString(), baby);
        logger.info(" value from redis: {}",redisTemplate.opsForValue().get(id.toString()).toString());
        return baby;
    }

    @Override
    public void deleteBaby(Integer id) {
        entityManager.createQuery("delete from Baby where id = :id")
                .setParameter("id", id);
        redisTemplate.delete(id.toString());
    }
}
