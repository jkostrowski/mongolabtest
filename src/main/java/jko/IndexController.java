package jko;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @RequestMapping("/")
    public Map<String,String> index() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("Hello", "JKO");
        map.put("Foo", "Bar");
        return  map;
    }
}
