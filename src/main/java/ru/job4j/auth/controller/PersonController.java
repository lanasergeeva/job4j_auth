package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.services.PersonServices;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private RestTemplate rest;

    private PersonServices persons;

    public PersonController(PersonServices persons) {
        this.persons = persons;
    }

    private static final String API = "http://localhost:8080/api/employee/";

    private static final String API_ID = "http://localhost:8080/api/employee/{id}";

    @GetMapping("/person")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<>(
                (Person) person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }


    @PostMapping("/person")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.persons.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/{empId}/person")
    public List<Person> getAllPersonsByEmployeelId(@PathVariable(value = "empId") long id) {
        Employee employee = rest.getForEntity(API_ID,
                Employee.class,
                Long.toString(id)).getBody();
        assert employee != null;
        return new ArrayList<>(employee.getAccounts());
    }


    @PostMapping("/{empId}/person")
    public ResponseEntity<Person> createWithEmployee(@PathVariable(value = "empId") int id,
                                                     @RequestBody Person person) {
        Employee employee = rest.getForEntity(API_ID,
                Employee.class,
                Integer.toString(id)).getBody();
        person.setEmployee(employee);
        System.out.println("In person " + person);
        return new ResponseEntity<>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{empId}/person")
    public ResponseEntity<Void> updateWithEmployee(@PathVariable(value = "empId") int id,
                                                   @RequestBody Person person) {
        System.out.println("Person in UPDATE");
        Employee employee = rest.getForEntity(API_ID,
                Employee.class,
                Integer.toString(id)).getBody();
        person.setEmployee(employee);
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

    /*----------------------*/

    @GetMapping("/person/employee")
    public List<Employee> findAllEmployees() {
        return rest.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                }
        ).getBody();
    }

    @PostMapping("/person/employee")
    public ResponseEntity<Employee> createEmployeeWithRespTemp(@RequestBody Employee employee) {
        Employee rsl = rest.postForObject(API, employee, Employee.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person/employee")
    public ResponseEntity<Void> updateEmployeeWithRespTemp(@RequestBody Employee employee) {
        rest.put(API, employee);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/person/employee/{id}")
    public ResponseEntity<Void> deleteEmployeeWithRespTemp(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }


}
