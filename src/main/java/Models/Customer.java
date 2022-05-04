package Models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
