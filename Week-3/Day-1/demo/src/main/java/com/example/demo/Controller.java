package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Controller {
    private final IService service1;
    private final IService service2;

    @Autowired
    public Controller(@Qualifier("serviceImpl")IService service1, @Qualifier("serviceImpl2")IService service2) {
        this.service1 = service1;
        this.service2 = service2;
    }

    public void print() {
        System.out.println(service1.sayHello());
        System.out.println(service2.sayHello());
    }
}
