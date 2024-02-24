package ku.cs.csProject.entity;

import jakarta.persistence.*;
import ku.cs.csProject.common.UserRole;
import ku.cs.csProject.common.UserStatus;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID userID;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String telephoneNumber;
    private String lineId;
    private String facebookLink;

    private UserStatus userStatus;
    private UserRole userRole;

    public String getFirstName() {
        return firstName;
    }
}
