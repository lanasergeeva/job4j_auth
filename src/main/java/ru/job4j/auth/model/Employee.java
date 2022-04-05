package ru.job4j.auth.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "itn")
    private long itn;
    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Person> accounts = new HashSet<>();

    public Employee() {
    }

    public static Employee of(String name, String surname, long itn) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setItn(itn);
        return employee;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getItn() {
        return itn;
    }

    public void setItn(long itn) {
        this.itn = itn;
    }

    public void addPerson(Person person) {
        if (accounts == null) {
            accounts = new HashSet<>();
        }
        accounts.add(person);
    }

    public Set<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Person> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", itn=" + itn
                + '}';
    }
}
