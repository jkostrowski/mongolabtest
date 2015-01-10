package jko.rest;

import jko.mongo.Benchmark;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FooController {

    @RequestMapping("/foo")
    public Map<String,Object> benchmark(@RequestParam(value="loops", defaultValue="100") String loops) {
        return new Benchmark().run();
    }
}
