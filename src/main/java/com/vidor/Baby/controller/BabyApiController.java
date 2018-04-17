package com.vidor.Baby.controller;

import com.vidor.Baby.entity.Baby;
import com.vidor.Baby.enums.ExceptionEnum;
import com.vidor.Baby.exception.BabyException;
import com.vidor.Baby.model.BabyModel;
import com.vidor.Baby.repository.BabyRepository;
import com.vidor.Baby.result.Result;
import com.vidor.Baby.service.BabyService;
import com.vidor.Baby.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/babies")
public class BabyApiController {

    private static final Logger logger = LoggerFactory.getLogger(BabyApiController.class);

    private static final String RLS = "Requested locale string. See section 14.4: https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html";

    @Autowired
    @Qualifier(value = "devErrorMessageSource")
    private MessageSource messageSource;

    @Autowired
    private BabyRepository babyRepository;

    @Autowired
    private BabyService babyService;

    /**
     * return all babies
     */
    @ApiOperation(value="获取Babies列表", notes="")
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
    @ApiOperation(value="添加Baby", notes="")
    @CachePut(value = "baby")
    @PostMapping("/add")
    public Baby addBaby(@ApiParam(value = RLS,required = true) @RequestHeader(value = "Accept-Language",required = true) String acceptLanguage,
                        @ApiParam(value = "输入Baby属性", required = true) @RequestBody @Valid Baby baby) {
        return babyRepository.save(baby);
    }

    /**
     * update a baby
     * @param id
     * @param baby
     * @return
     */
    @ApiOperation(value="更新Baby", notes="根据ID更新Baby")
    @PutMapping("/{id}")
    public Object updateBaby(@ApiParam(value = RLS,required = true) @RequestHeader(value = "Accept-Language",required = true) String acceptLanguage,
                             @ApiParam(value = "输入Baby ID", required = true) @PathVariable Integer id,
                             @RequestHeader(value="User-Token", required=false) String userToken,
                             @ApiParam(value = "输入Baby属性", required = true) @RequestBody @Valid Baby baby, BindingResult bindingResult) {
        logger.info("updateBaby execute...");
        if (bindingResult.hasFieldErrors()) {
            throw new BabyException(ExceptionEnum.PARAMTER_EXCEPTION.getCode(), ExceptionEnum.PARAMTER_EXCEPTION.getMsg());
//            return bindingResult.getFieldError().getDefaultMessage();
        }
        baby.setId(id);
        Result  result = ResultUtil.ok(babyRepository.save(baby));
        String[] args = {"param1", "param2"};
        result.setMsg(messageSource.getMessage(result.getMsg(), args, LocaleUtils.toLocale(acceptLanguage)));
        return result;
    }

    /**
     * delete a baby
     * @param id
     * @return
     */
    /*
    表明Spring应该在缓存中清除一个或多个条目
     */
    @ApiOperation(value="删除Baby", notes="根据ID删除Baby")
    @CacheEvict(value = "baby")
    @DeleteMapping("/{id}")
    public String deleteBaby(@ApiParam(value = "输入Baby ID", required = true) @PathVariable Integer id) {
        babyRepository.deleteById(id);
        return "delete success";
    }

    /**
     * find a baby by id
     */
    @ApiOperation(value="查找Baby", notes="根据ID查找Baby")
    @GetMapping("/{id}")
    public Object findBaby(@ApiParam(value = "输入Baby ID", required = true) @PathVariable Integer id) {
//        return babyRepository.getOne(id);
//        return babyRepository.findById(id);
        return babyService.findById(id);
    }

    /**
     * find babies by age
     */
    @ApiOperation(value="查找Baby", notes="根据年龄查找Baby")
    @GetMapping("/age/{age}")
    public List<Baby> findBabiesByAge(@ApiParam(value = "输入Baby年龄", required = true) @PathVariable Integer age) {
        return babyRepository.findByAge(age);
    }

    /**
     * How to search by model
     */
    @ApiOperation(value="插入2个Baby", notes="测试事务")
    @PostMapping("/insertTwo")
    public Object insert() {
        babyService.insertTwo();
        return "insert success";
    }

    @ApiOperation(value="根据年龄分组，获取Baby列表", notes="实现方式：原生sql")
    @PostMapping("/groupAge")
    public Object groupAge() {
        babyRepository.groupByBabyAsSql();
        logger.info("group by own sql : {}", babyRepository.groupByBabyAsSql());
        return "group age success.";
    }

    @ApiOperation(value="根据年龄分组，获取Baby列表", notes="实现方式：Hsql")
    @PostMapping("/groupAgeH")
    public Object groupAgeH() {
        babyRepository.groupByBabyAsHql();
        logger.info("group by own sql : {}", babyRepository.groupByBabyAsHql());
        return "group age H success.";
    }

    @ApiOperation(value="根据年龄分组，获取Baby列表", notes="实现方式：Specification")
    @PostMapping("/groupAgeS/{number}")
    public Object groupAgeS(@ApiParam(value = "输入Baby年龄", required = true) @PathVariable Integer number) {
        babyRepository.groupBybabyAsSpecification(number);
        logger.info("group by own sql : {}", babyRepository.groupBybabyAsSpecification(number));
        return "group age S success.";
    }

    @ApiOperation(value="根据年龄查询，获取Baby", notes="实现方式：@Query")
    @GetMapping("findBaby/{age}")
    public Object findBabyQ(@ApiParam(value = "输入Baby年龄", required = true) @PathVariable Integer age) {
        logger.info("find a baby using Query : {}",babyRepository.findBaby(age).toString());
        return "find baby using Query success";
    }

    @ApiOperation(value="根据年龄查询，获取Baby", notes="查找hibernate cache:查询缓存")
    @GetMapping("findBabyAge/{age}")
    public Object findByAgeCache(@ApiParam(value = "输入Baby年龄", required = true) @PathVariable Integer age) {
        logger.info("find a baby by cache : {}",babyRepository.findByAgeCache(age).toString());
        return "find babies using cache";
    }

    /*
    表明在Spring调用之前，首先应该在缓存中查找方法的返回值，如果这个值能够找到，就会返回缓存的值，否则这个方法会被调用，返回值会放到缓存中
     */
    @ApiOperation(value="根据ID查询，获取Baby", notes="查找hibernate cache:二级缓存")
    @Cacheable(value = "baby")
    @GetMapping("findBabyId/{id}")
    public Baby findByIdCache(@ApiParam(value = "输入Baby ID", required = true) @PathVariable Integer id) {
        logger.info("find a baby by cache : {}",babyRepository.findByIdCache(id).toString());
        return babyRepository.findByIdCache(id);
    }

    @ApiOperation(value="删除Baby", notes="")
    @DeleteMapping("redis/{id}")
    public void deleteRedisId(@ApiParam(value = "输入Baby ID", required = true) @PathVariable Integer id) {
        babyRepository.deleteBaby(id);
    }

     /*
    表明Spring应该将该方法返回值放到缓存中，在方法调用前不会检查缓存，方法始终会被调用
    , key = "#root.caches[0].name + ':' + #baby.id"
     */
     @ApiOperation(value="更新Baby", notes="")
    @CachePut(value = "baby", key = "#id")
    @PutMapping("/redis/update/{id}")
    public Baby updateRedis(@ApiParam(value = "输入Baby ID", required = true) @PathVariable Integer id,
                            @ApiParam(value = "输入Baby属性", required = true) @RequestBody @Valid Baby baby) {
        baby.setId(id);
        return babyRepository.save(baby);
    }
}
