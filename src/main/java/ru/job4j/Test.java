package ru.job4j;


import ru.job4j.auth.model.Employee;
import ru.job4j.auth.model.Person;

public class Test {
    public static void main(String[] args) {
        Employee employee = Employee.of("Pt", "Op", 1123);
        employee.setId(22);
        Person person = Person.of("La", "Pa", employee);
       /* Gson gson = new Gson();
        String json = gson.toJson(person);
        System.out.println(json);*/
    }
}
