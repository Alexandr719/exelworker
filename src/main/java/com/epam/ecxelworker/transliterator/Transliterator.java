package com.epam.ecxelworker.transliterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Transliterator {


    @Autowired
    @Qualifier("namesProperty")
    private Map<String, String> namesMap;

    @Autowired
    @Qualifier("lettersProperty")
    private Map<String, String> lettersMap;

    public String transliterateField(String field) {
        StringBuilder outWord = new StringBuilder("");
        String[] words = field.split("\\s");
        for (String subStr : words) {
            outWord.append(transliterateWord(subStr)).append(" ");
        }
        return outWord.toString();
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
            outWord = transliterateChars(chars);
        }
        return outWord.substring(0, 1).toUpperCase() + outWord.substring(1);
    }
    private String transliterateChars(char[] chars) {
        StringBuilder outWord = new StringBuilder("");
        boolean findLetter = false;
        for (char aChar : chars) {
            for (String key : lettersMap.keySet()) {
                if (key.charAt(0) == aChar) {
                    outWord.append(lettersMap.get(key));
                    findLetter = true;
                }
            }
            if(!findLetter){
                outWord.append(aChar);
            }
        }
        return outWord.toString();
    }


}