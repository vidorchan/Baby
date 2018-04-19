package com.vidor.Baby.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void executeAsyncTask(Integer i) {
        System.out.println("异步执行任务：" + i);
    }

    @Async
    public void executeAsyncTask2(Integer i) {
        System.out.println("异步执行任务2：" + i);
    }
}
