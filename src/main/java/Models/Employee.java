package Models;

import org.springframework.stereotype.Component;

@Component
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Employee(int employeeId, String firstName, String lastName, String userName, String password) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
}
