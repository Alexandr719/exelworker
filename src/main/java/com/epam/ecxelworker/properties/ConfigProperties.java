package com.epam.ecxelworker.properties;

import com.epam.ecxelworker.PropertiesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ConfigProperties {
    @Autowired
    PropertiesLoader propertiesLoader;

    @Bean(name = "namesProperty")
    public Map<String, String> nameMap(){
        return getMapFromProperties(propertiesLoader.getNameProperties());
    }

    @Bean(name = "lettersProperty")
    public Map<String, String> letterMap(){
        return getMapFromProperties(propertiesLoader.getLettersProperties());
    }
//Todo register
    private Map<String, String> getMapFromProperties(Properties properties) {
        Map<String, String> map = new HashMap<>();
        for (String name : properties.stringPropertyNames())
            map.put(name, properties.getProperty(name));
        return map;
    }


}