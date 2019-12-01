package ru.ionov.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "iex")
public class IEXProperties {

    private String scheme;
    private String host;
    private String path;
    private String token;

}
