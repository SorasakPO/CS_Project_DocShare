package ku.cs.csProject.model;

import lombok.Data;

@Data
public class SignupRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String telephoneNumber;

    private String lineId;


}
