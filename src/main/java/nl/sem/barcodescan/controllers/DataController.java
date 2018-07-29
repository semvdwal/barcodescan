package nl.sem.barcodescan.controllers;

import lombok.extern.log4j.Log4j2;
import nl.sem.barcodescan.models.Product;
import nl.sem.barcodescan.repositories.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Log4j2
public class DataController {

    private final ProductRepository productRepository;

    public DataController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping(value = "/scan", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> scan(@RequestBody Product product) {
        log.info("Received product: " + product.getCode() + " - " + product.getTitle());
        return productRepository.findByCode(product.getCode()).flatMap(foundProduct -> {
            foundProduct.setStock(foundProduct.getStock() + 1);
            return productRepository.save(foundProduct).map(savedProduct -> {
                return ResponseEntity.ok("{\"result\": true}");
            });
        }).switchIfEmpty(
            productRepository.save(product).map(savedProduct -> {
                return ResponseEntity.ok("{\"result\": true}");
            })
        );
    }

    @GetMapping(value = "/productinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> getProductInfo(@RequestParam String upc) {
        // https://api.upcitemdb.com/prod/trial/lookup
        return WebClient.create("https://api.upcitemdb.com/prod/trial/lookup?upc=" + upc).get().accept(MediaType.APPLICATION_JSON).exchange().flatMap(response -> {
            if(response.statusCode().is2xxSuccessful()) {
                return response.toEntity(String.class).map(body -> {
                    return ResponseEntity.ok(body.getBody());
                });
            } else {
                return response.toEntity(String.class).map(body -> {
                    return ResponseEntity.status(response.statusCode()).body(body).getBody();
                });
            }
        });
    }

}
