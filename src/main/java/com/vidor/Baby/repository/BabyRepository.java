package com.vidor.Baby.repository;

import com.vidor.Baby.entity.Baby;
import com.vidor.Baby.model.BabyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BabyRepository extends JpaRepository<Baby, Integer> {
    public List<Baby> findByAge(Integer age);
}
