package jko;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @RequestMapping("/foo")
    public Bar greeting(@RequestParam(value="name", defaultValue="bar") String name) {
        return new Bar( "Foo Bar" );
    }
}
