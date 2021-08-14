package persistence.example.domain;

import java.time.LocalDate;

public class Employee {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String country;
    private final LocalDate hireDate;

    public Employee(Integer id, String firstName, String lastName, String country, LocalDate hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.hireDate = hireDate;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }

}
