package com.epam.ecxelworker;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

@Log4j2
@Service
public class PropertiesLoader {

    public Properties getNameProperties() {
        Properties property = new Properties();
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(new File("src/main/resources/names" +
                            ".properties"));
            property.load(new InputStreamReader(fileInputStream,
                    Charset.forName("UTF-8")));

        } catch (IOException e) {
            log.error("Prop error", e);
        }
        return property;
    }


    public Properties getLettersProperties() {
        Properties property = new Properties();
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(new File("src/main/resources/letters" +
                            ".properties"));
            property.load(new InputStreamReader(fileInputStream,
                    Charset.forName("UTF-8")));

        } catch (IOException e) {
            log.error("Prop error", e);
        }
        return property;
    }


}