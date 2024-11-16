package ampath.co.ke.amrs_kenyaemr.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.stream.Collectors;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure().load();

        // Convert Dotenv entries to a Map
        Map<String, Object> properties = dotenv.entries().stream()
                .collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));

        // Add these properties to the environment
        environment.getPropertySources().addLast(new MapPropertySource("dotenvProperties", properties));
    }
}
