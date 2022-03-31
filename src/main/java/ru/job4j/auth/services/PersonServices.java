package ru.job4j.auth.services;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.store.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServices {

    private final PersonRepository persons;

    public PersonServices(PersonRepository posts) {
        this.persons = posts;
    }

    public List<Person> findAll() {
        return ((List<Person>) persons.findAll());
    }

    public Person save(Person person) {
        return persons.save(person);
    }

    public Optional findById(int id) {
        return persons.findById(id);
    }

    public void delete(int id) {
        persons.delete((Person) findById(id).get());
    }
}
