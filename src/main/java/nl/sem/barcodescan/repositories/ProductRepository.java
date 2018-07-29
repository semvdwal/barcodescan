package nl.sem.barcodescan.repositories;

import nl.sem.barcodescan.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByCode(String code);

}
