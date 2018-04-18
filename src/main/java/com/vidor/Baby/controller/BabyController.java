package com.vidor.Baby.controller;

import com.vidor.Baby.config.AdminConfig;
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
}
