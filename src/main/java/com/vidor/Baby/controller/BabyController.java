package com.vidor.Baby.controller;

import com.vidor.Baby.config.AdminConfig;
import com.vidor.Baby.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//@Controller
//@ResponseBody
@RestController
@RefreshScope
public class BabyController {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private AsyncService asyncService;

    @Value("${admin.content}")
    private String content;

    @Value("${admin.test}")
    private String test;

    @RequestMapping(value = "/hello")//accept post and get
    public String sayH() {
        return "sayH()";
    }

//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping(value = {"/hello","/hi"})
    //public ResponseEntity say() {
    public String say() {
        return adminConfig.getName() + " " + adminConfig.getPassword() + " " + content + "test: " + test;
    }

    @GetMapping(value = "/showUser/{id}")
    public String showId(@PathVariable("id") Integer myId,
                         @RequestParam(value = "name", required = true, defaultValue = "defaultAdmin") String myName,
                         @RequestParam(value="ps", required = true) String myPs) {
        return "id: " + myId + " name: " + myName  + " ps: " + myPs ;
    }

    @GetMapping(value = "/async")
    public void executeAsync() {

        for (int i = 0; i < 10; i++) {
            asyncService.executeAsyncTask(i);
            asyncService.executeAsyncTask2(i);
        }
        /** a sample
         * 异步执行任务：9
         异步执行任务2：2
         异步执行任务：3
         异步执行任务2：3
         异步执行任务：4
         异步执行任务2：4
         异步执行任务：5
         异步执行任务2：5
         异步执行任务：6
         异步执行任务2：6
         异步执行任务：7
         异步执行任务2：8
         异步执行任务：2
         异步执行任务2：1
         异步执行任务：0
         2018-04-19 16:20:01.491  INFO 5408 --- [nio-8001-exec-2] c.v.Baby.interceptors.BabyInterceptor    : interceptor before rendering view
         2018-04-19 16:20:01.492  INFO 5408 --- [nio-8001-exec-2] c.v.Baby.interceptors.BabyInterceptor    : interceptor ending
         异步执行任务2：9
         异步执行任务：8
         异步执行任务2：0
         异步执行任务2：7
         异步执行任务：1
         */
    }
}
