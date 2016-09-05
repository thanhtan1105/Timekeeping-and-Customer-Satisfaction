package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lethanhtan on 9/1/16.
 */

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello() {
        return "Hello Tan handsome!";
    }


    @RequestMapping("/sayHello")
    public String sayHello() {
        return "Hello Anh Trung";
    }
}
