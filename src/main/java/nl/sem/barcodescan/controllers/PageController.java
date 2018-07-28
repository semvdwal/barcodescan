package nl.sem.barcodescan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping("scanner")
    public Mono<String> scanner() {
        return Mono.just("scanner");
    }

}
