package nl.sem.barcodescan.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Log4j2
public class DataController {

    @GetMapping(value = "/scan", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> scan(@RequestParam String code) {
        log.info("Received code: " + code);
        return Mono.just(ResponseEntity.ok("{\"result\": true}"));
    }

}
