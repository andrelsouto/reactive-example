package br.com.andre.reactivesample.repositories;

import br.com.andre.reactivesample.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> getById(Integer id);
    Flux<Person> findAll();
}
