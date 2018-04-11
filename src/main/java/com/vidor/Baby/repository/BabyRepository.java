package com.vidor.Baby.repository;

import com.vidor.Baby.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BabyRepository extends JpaRepository<Baby, Integer>, BabyRepositoryCustom{
    public List<Baby> findByAge(Integer age);

    @Query("from Baby b where b.age=:age")
    public Baby findBaby(@Param("age") Integer age);
}
