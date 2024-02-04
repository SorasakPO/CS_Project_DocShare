package ku.cs.csProject.entity;

import jakarta.persistence.*;
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

    private String userStatus;
    private String userRole;

}
