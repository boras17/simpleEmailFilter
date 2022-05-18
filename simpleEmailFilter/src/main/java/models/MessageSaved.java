package models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
@Entity
public class MessageSaved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String author;
    private String title;
    private LocalDateTime sentDate;
    @Lob
    private String body;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<>();

    public MessageSaved(){}
}
