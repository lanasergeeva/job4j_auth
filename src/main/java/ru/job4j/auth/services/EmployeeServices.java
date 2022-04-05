package ru.job4j.auth.services;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.store.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServices {

    private final EmployeeRepository employees;

    public EmployeeServices(EmployeeRepository employees) {

        this.employees = employees;
    }

    public List<Employee> findAll() {
        return (List<Employee>) employees.findAll();
    }

    public Employee save(Employee employee) {
        return employees.save(employee);
    }

    public Optional findById(int id) {
        return employees.findById(id);
    }

    public void delete(int id) {
        employees.delete((Employee) findById(id).get());
    }
}
