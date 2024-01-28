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
    private String mail;

    @ManyToOne
    @JoinColumn(name = "userType_id")
    private UserType userType;
}
