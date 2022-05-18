package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class EmailConfigurationProperties {

    public static Optional<Properties> getConfig() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        return Optional.of(props);

    }
}
