
package com.example.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://localhost:8080/api/hello?lang=ja でアクセス
 */
@RestController
@RequestMapping("/api")
public class HelloController {
    @RequestMapping("/hello")
    public String hello(
            @RequestParam String lang
    ) {
        return "HELLO";
    }
}