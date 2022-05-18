package models.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.saves.SavingStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EmailFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<FilteredEmail> filteredEmails = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<SavingStrategy> savingStrategies = new HashSet<>();

    public void deleteFilteredEmail(int filteredEmailId){
        this.filteredEmails.removeIf(filteredEmail -> filteredEmail.getId() == filteredEmailId);
    }

    public void addFilteredEmail(FilteredEmail filteredEmail){
        this.filteredEmails.add(filteredEmail);
    }
}