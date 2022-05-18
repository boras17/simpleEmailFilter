package models.saves;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SavingStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pattern;
    @Enumerated(EnumType.STRING)
    private SavingBy savingBy;

    public static boolean matches(String pattern, String input){
        Pattern p = Pattern.compile(pattern);
        return p.matcher(input).find();
    }
}