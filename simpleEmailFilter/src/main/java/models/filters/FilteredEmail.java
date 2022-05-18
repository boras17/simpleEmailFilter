package models.filters;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.MessageSaved;
import models.filters.EmailFilter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class FilteredEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String host;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MessageSaved> savedMessages = new HashSet<>();
}
