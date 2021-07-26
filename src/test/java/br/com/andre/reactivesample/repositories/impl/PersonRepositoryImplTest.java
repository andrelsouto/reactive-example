package br.com.andre.reactivesample.repositories.impl;

import br.com.andre.reactivesample.domain.Person;
import br.com.andre.reactivesample.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository;

    @BeforeEach
    void setup() {
        personRepository = new PersonRepositoryImpl();
    }

    @Test
    void getByIdBlock() {

        Mono<Person> pernoMono = personRepository.getById(2);

        Person person = pernoMono.block();
        System.out.println(person.toString());
    }

    @Test
    void getByIdSubscribe() {


        Mono<Person> pernoMono = personRepository.getById(2);
        StepVerifier.create(pernoMono).expectNextCount(1).verifyComplete();
        pernoMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void getByIdSubscribeNotFound() {

        Mono<Person> pernoMono = personRepository.getById(99);
        StepVerifier.create(pernoMono).verifyComplete();
        pernoMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void getByIdMapFunction() {

        Mono<Person> pernoMono = personRepository.getById(4);
        pernoMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void findAllBlock() {

        Flux<Person> personFlux = personRepository.findAll();
        Person person = personFlux.blockFirst();
        System.out.println(person.toString());
    }

    @Test
    void findAllSubscribe() {

        Flux<Person> personFlux = personRepository.findAll();
        StepVerifier.create(personFlux).expectNextCount(4).verifyComplete();
        personFlux.subscribe(System.out::println);
    }

    @Test
    void findAllSubscribeToListMono() {

        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> personListMono = personFlux.collectList();
        personListMono.subscribe(list -> list.forEach(System.out::println));
    }

    @Test
    void findAllSubscribeFilter() {

        Flux<Person> personFlux = personRepository.findAll();

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == 3).next();
        personMono.subscribe(System.out::println);
    }

    @Test
    void findAllSubscribeFilterNotFound() {

        Flux<Person> personFlux = personRepository.findAll();

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == 8).next();
        personMono.subscribe(System.out::println);
    }

    @Test
    void findAllSubscribeFilterNotFoundWithException() {

        Flux<Person> personFlux = personRepository.findAll();

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == 8).single();
        personMono
                .doOnError(error -> System.out.println("Person not found"))
                .onErrorReturn(Person.builder().build())
                .subscribe(System.out::println);

    }
}