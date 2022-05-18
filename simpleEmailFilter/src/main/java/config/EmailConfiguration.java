package config;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Optional;
import java.util.Properties;

public class EmailConfiguration {
    public Session getSession(String password, String email){
        Optional<Properties> properties = EmailConfigurationProperties.getConfig();
        System.out.println(properties.isPresent());
        return properties.map(value -> Session.getDefaultInstance(value,null)).orElseThrow(() -> new RuntimeException("Nie udało się otworzyć sesji"));
    }
}
