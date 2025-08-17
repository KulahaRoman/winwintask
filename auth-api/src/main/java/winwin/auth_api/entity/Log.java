package winwin.auth_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "processing_log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userID;

    @Column(name = "input_text")
    private String inputText;

    @Column(name = "output_text")
    private String outputText;

    @Column(name = "created_at")
    private long createdAt;
}
