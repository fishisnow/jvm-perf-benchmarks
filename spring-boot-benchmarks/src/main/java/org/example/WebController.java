package org.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangyusong on 2023/10/17
 */
@RestController
@RequestMapping
public class WebController {

    @RequestMapping("/hello")
    public String hello() {
        return "ok";
    }
}
