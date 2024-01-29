package ku.cs.csProject.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Subject {

    @Id
    @GeneratedValue
    private UUID subjectId;
    protected String subjectName;
}
