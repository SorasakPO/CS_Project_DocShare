package ku.cs.csProject.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class UserType {

    @Id
    private UUID userTypeId;
    private String userTypename;
}
