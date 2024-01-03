package ku.cs.csProject.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class RequestSubject {

    @Id
    @GeneratedValue
    private UUID requestId;
    private String subjectRequest;
    private LocalDateTime requestDate;
    private String requestStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User informer;
}
