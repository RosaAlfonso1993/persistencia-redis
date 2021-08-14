package persistence.example.dto;

import persistence.example.domain.Employee;

import java.time.LocalDate;

public class EmployeeDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
    private LocalDate hireDate;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Integer id, String firstName, String lastName, String country, LocalDate hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.hireDate = hireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Employee toDomain() {
        return new Employee(
                id,
                firstName,
                lastName,
                country,
                hireDate
        );
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }

}
