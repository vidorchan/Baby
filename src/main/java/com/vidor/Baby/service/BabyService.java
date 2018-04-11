package com.vidor.Baby.service;

import com.vidor.Baby.entity.Baby;
import com.vidor.Baby.model.BabyModel;
import com.vidor.Baby.repository.BabyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BabyService {

    @Autowired
    private BabyRepository babyRepository;

    /**
     * Use Transactional annotation
     * Mysql engine = InnoDB (MyISAM can't support transation)
     */
    @Transactional
    public void insertTwo() {
        Baby baby = new Baby();
        baby.setName("x123");
        baby.setSex(1);
        baby.setDob(new Date());
        baby.setAge(7);
        babyRepository.save(baby);

        baby.setName("xopi0");
        baby.setAge(10);
        baby.setSex(1);
        babyRepository.save(baby);
    }

    public Object findById(Integer id) {
        Baby baby = babyRepository.findById(id).get();
        BabyModel babyModel = new BabyModel();
        BeanUtils.copyProperties(baby, babyModel);
        return babyModel;
    }

    public List<BabyModel> findAll() {
        List<BabyModel> babyModels = new ArrayList<>();
        List<Baby> babyList = babyRepository.findAll();
        for (Baby baby : babyList) {
            BabyModel babyModel = new BabyModel();//must here
            BeanUtils.copyProperties(baby, babyModel);
            babyModels.add(babyModel);
        }
        return babyModels;
    }
}
