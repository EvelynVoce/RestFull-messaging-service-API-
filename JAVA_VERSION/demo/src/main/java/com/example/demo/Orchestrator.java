package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.HashMap;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class Orchestrator {
    @GetMapping("/SCC")
    public Serializable main(@RequestParam("service") String service) throws MalformedURLException {
        HashMap<String, String> map = new HashMap<>();

        switch (service) {
            case "ID":
                map.put("id", id_service.get_ID());
                return map;
            case "weather":
                map.put("temp", weather_service.get_weather());
                return map;
            default:
                return "we do not offer that service";
        }
    }

}


//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/api/")
//public class MathOperations {
//    @GetMapping("/hello-world")
//    public Serializable hello(@RequestParam("x") int x, @RequestParam("operation") String operation, @RequestParam("y") int y) throws MalformedURLException {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("x", "" + x);
//        map.put("y", "" + y);
//
//        switch (operation) {
//            case "sub":
//                map.put("result", "" + (x - y));
//                map.put("operation", "-");
//                return map;
//            case "multi":
//                map.put("result", "" + (x * y));
//                map.put("operation", "*");
//                return map;
//            case "divide":
//                map.put("result", "" + (x / y));
//                map.put("operation", "/");
//                return map;
//            case "add":
//                map.put("result","" +  (x + y));
//                map.put("operation", "+");
//                return map;
//            default:
//                return "we don't have that operation bruv";
//        }
//    }
//
//}
