package br.com.andre.reactivesample.repositories.impl;

import br.com.andre.reactivesample.domain.Person;
import br.com.andre.reactivesample.repositories.PersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private static final List<Person> PEOPLE = Arrays.asList(
            Person.builder().id(1).firstName("Michael").lastName("Weston").build(),
            Person.builder().id(2).firstName("Fiona").lastName("Glenanne").build(),
            Person.builder().id(3).firstName("Sam").lastName("Axe").build(),
            Person.builder().id(4).firstName("Jesse").lastName("Porter").build()
    );

    @Override
    public Mono<Person> getById(Integer id) {
        return findAll().filter(person -> person.getId() == id).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(PEOPLE.get(0), PEOPLE.get(1), PEOPLE.get(2), PEOPLE.get(3));
    }
}
