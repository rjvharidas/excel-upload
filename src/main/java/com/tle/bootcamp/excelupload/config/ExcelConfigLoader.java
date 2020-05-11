package com.tle.bootcamp.excelupload.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Configuration
@ConfigurationProperties("excel")
public class ExcelConfigLoader {
    private List<ExcelConfig> configs = new ArrayList<>();

    public Optional<ExcelConfig> getConfig(String agency) {
        return configs
                .stream()
                .filter(ex -> ex.getAgencyName().equalsIgnoreCase(agency))
                .findFirst();
    }

    @Setter
    @Getter
    public static class ExcelConfig {
        private String agencyName;
        private int startingRow;
        private int rowLimit;
        private int sheet;
        private String configName;
    }
}
