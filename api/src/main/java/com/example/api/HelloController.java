package com.example.api;

import com.example.api.entities.GreetingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 以下でローカルブラウザから接続可能
 * http://localhost:8080/api/hello?lang=en
 * http://localhost:8080/api/hello?lang=ja
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private GreetingRepository repository;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/hello")
    public String hello(
            @RequestParam String lang
    ) {
        GreetingEntity entity = this.repository.findFirstByLang(lang);
        return entity.text;
    }
}