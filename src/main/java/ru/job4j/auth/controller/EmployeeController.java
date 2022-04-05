package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.services.EmployeeServices;

import java.util.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private RestTemplate rest;

    private final EmployeeServices employees;

    private static final String API = "http://localhost:8080/api/person/";

    private static final String API_ID = "http://localhost:8080/api/person/{id}";

    private static final String API_EMPLOYEE_ID = "http://localhost:8080/api/{empId}/person/";


    public EmployeeController(EmployeeServices employees) {
        this.employees = employees;
    }


    @GetMapping("/employee")
    public List<Employee> findAll() {
        return employees.findAll();
    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var employee = this.employees.findById(id);
        return new ResponseEntity<>(
                (Employee) employee.orElse(new Employee()),
                employee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                this.employees.save(employee),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/employee")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        this.employees.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.employees.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/employee/person")
    public ResponseEntity<Person> createPersonWithRestTemp(@RequestBody Person person) {
        Person rsl = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/employee/person/{id}")
    public ResponseEntity<Void> deletePersonWithRespTemp(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("employee/person/{empId}/create")
    public ResponseEntity<Person> createPersonWithRestTempWithEmployee(@PathVariable(value = "empId") int id,
                                                                       @RequestBody Person person) {
        Map<String, String> map = new HashMap<>();
        map.put("empId", Integer.toString(id));
        Person rsl = rest.postForObject(API_EMPLOYEE_ID, person, Person.class, map);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/employee/person")
    public ResponseEntity<Void> updatePersonWithRestTemp(@RequestBody Person person) {
        Employee empByPersonId = findEmpByPersonId(person.getId());
        Map<String, String> map = new HashMap<>();
        map.put("empId", Integer.toString(empByPersonId.getId()));
        rest.put(API_EMPLOYEE_ID, person, map);
        return ResponseEntity.ok().build();
    }

    private Employee findEmpByPersonId(int id) {
        Employee rsl = null;
        List<Employee> all = employees.findAll();
        for (Employee employee : all) {
            if (employee.getId() == id) {
                rsl = employee;
                break;
            }
        }
        return rsl;
    }
}
