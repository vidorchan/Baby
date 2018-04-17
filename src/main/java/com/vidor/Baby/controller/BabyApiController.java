package com.vidor.Baby.controller;

import com.vidor.Baby.entity.Baby;
import com.vidor.Baby.enums.ExceptionEnum;
import com.vidor.Baby.exception.BabyException;
import com.vidor.Baby.model.BabyModel;
import com.vidor.Baby.repository.BabyRepository;
import com.vidor.Baby.result.Result;
import com.vidor.Baby.service.BabyService;
import com.vidor.Baby.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/babies")
public class BabyApiController {

    private static final Logger logger = LoggerFactory.getLogger(BabyApiController.class);

    @Autowired
    private BabyRepository babyRepository;

    @Autowired
    private BabyService babyService;

    /**
     * return all babies
     */
    @PostMapping({"/",""})
    public List<BabyModel> getAll() {
        return babyService.findAll();
    }

    /**
     * add a baby
     * @param baby
     * @return
     */
    /*
    表明Spring应该将该方法返回值放到缓存中，在方法调用前不会检查缓存，方法始终会被调用
     */
    @CachePut(value = "baby")
    @PostMapping("/add")
    public Baby addBaby(@Valid Baby baby) {
        return babyRepository.save(baby);
    }

    /**
     * update a baby
     * @param id
     * @param baby
     * @return
     */
    @PutMapping("/{id}")
    public Object updateBaby(@PathVariable Integer id, @Valid Baby baby, BindingResult bindingResult) {
        logger.info("updateBaby execute...");
        if (bindingResult.hasFieldErrors()) {
            throw new BabyException(ExceptionEnum.PARAMTER_EXCEPTION.getCode(), ExceptionEnum.PARAMTER_EXCEPTION.getMsg());
//            return bindingResult.getFieldError().getDefaultMessage();
        }
        baby.setId(id);
        return ResultUtil.ok(babyRepository.save(baby));
    }

    /**
     * delete a baby
     * @param id
     * @return
     */
    /*
    表明Spring应该在缓存中清除一个或多个条目
     */
    @CacheEvict(value = "baby")
    @DeleteMapping("/{id}")
    public String deleteBaby(@PathVariable Integer id) {
        babyRepository.deleteById(id);
        return "delete success";
    }

    /**
     * find a baby by id
     */
    @GetMapping("/{id}")
    public Object findBaby(@PathVariable Integer id) {
//        return babyRepository.getOne(id);
//        return babyRepository.findById(id);
        return babyService.findById(id);
    }

    /**
     * find babies by age
     */
    @GetMapping("/age/{age}")
    public List<Baby> findBabiesByAge(@PathVariable Integer age) {
        return babyRepository.findByAge(age);
    }

    /**
     * How to search by model
     */

    @PostMapping("/insertTwo")
    public Object insert() {
        babyService.insertTwo();
        return "insert success";
    }

    @PostMapping("/groupAge")
    public Object groupAge() {
        babyRepository.groupByBabyAsSql();
        logger.info("group by own sql : {}", babyRepository.groupByBabyAsSql());
        return "group age success.";
    }

    @PostMapping("/groupAgeH")
    public Object groupAgeH() {
        babyRepository.groupByBabyAsHql();
        logger.info("group by own sql : {}", babyRepository.groupByBabyAsHql());
        return "group age H success.";
    }

    @PostMapping("/groupAgeS/{number}")
    public Object groupAgeS(@PathVariable Integer number) {
        babyRepository.groupBybabyAsSpecification(number);
        logger.info("group by own sql : {}", babyRepository.groupBybabyAsSpecification(number));
        return "group age S success.";
    }

    @GetMapping("findBaby/{age}")
    public Object findBabyQ(@PathVariable Integer age) {
        logger.info("find a baby using Query : {}",babyRepository.findBaby(age).toString());
        return "find baby using Query success";
    }

    @GetMapping("findBabyAge/{age}")
    public Object findByAgeCache(@PathVariable Integer age) {
        logger.info("find a baby by cache : {}",babyRepository.findByAgeCache(age).toString());
        return "find babies using cache";
    }

    /*
    表明在Spring调用之前，首先应该在缓存中查找方法的返回值，如果这个值能够找到，就会返回缓存的值，否则这个方法会被调用，返回值会放到缓存中
     */
    @Cacheable(value = "baby")
    @GetMapping("findBabyId/{id}")
    public Baby findByIdCache(@PathVariable Integer id) {
        logger.info("find a baby by cache : {}",babyRepository.findByIdCache(id).toString());
        return babyRepository.findByIdCache(id);
    }

    @DeleteMapping("redis/{id}")
    public void deleteRedisId(@PathVariable Integer id) {
        babyRepository.deleteBaby(id);
    }

     /*
    表明Spring应该将该方法返回值放到缓存中，在方法调用前不会检查缓存，方法始终会被调用
    , key = "#root.caches[0].name + ':' + #baby.id"
     */
    @CachePut(value = "baby", key = "#id")
    @PutMapping("/redis/update/{id}")
    public Baby updateRedis(@PathVariable Integer id, Baby baby) {
        baby.setId(id);
        return babyRepository.save(baby);
    }
}
