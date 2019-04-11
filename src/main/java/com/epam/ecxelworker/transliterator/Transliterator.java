package com.epam.ecxelworker.transliterator;

import com.epam.ecxelworker.PropertiesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class Transliterator {
    @Autowired
    PropertiesLoader propertiesLoader;

    Map<String, String> namesMap;
    Map<String, String> lettersMap;


    public void run() {
        System.out.println("here");
        Properties nameProperties = propertiesLoader.getNameProperties();
        Properties lettersProperties = propertiesLoader.getLettersProperties();
        namesMap = getMapFromProperties(nameProperties);
        lettersMap =
                getMapFromProperties(lettersProperties);


        String word = "Александр Филатов";
        String[] words = word.split("\\s");
        StringBuffer stringBuffer = new StringBuffer();
        for (String subStr : words) {
            System.out.println(transliterateWord(subStr));
        }
    }


    private String transliterateWord(String word) {
        word = word.toLowerCase();
        String outWord = null;
        boolean findName = false;
        for (String key : namesMap.keySet()) {
            if (key.equals(word)) {
                outWord = namesMap.get(key);
                findName = true;
                break;
            }
        }
        if (!findName) {
            char[] chars = word.toCharArray();
            outWord = transliterateChar(chars);
        }
        return outWord.substring(0, 1).toUpperCase() + outWord.substring(1);
    }

    private String transliterateChar(char[] chars) {
        StringBuilder outWord = new StringBuilder("");
        for (char aChar : chars) {
            for (String key : lettersMap.keySet()) {
                if (key.charAt(0) == aChar) {
                    outWord.append(lettersMap.get(key));
                }

            }
        }
        return outWord.toString();
    }


    private Map<String, String> getMapFromProperties(Properties properties) {
        Map<String, String> map = new HashMap<>();
        for (String name : properties.stringPropertyNames())
            map.put(name, properties.getProperty(name));
        return map;
    }


}