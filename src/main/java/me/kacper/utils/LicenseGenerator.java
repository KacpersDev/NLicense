package me.kacper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LicenseGenerator {

    private final Random random = new Random();
    public List<Character> characters = new ArrayList<>();

    public LicenseGenerator(){
        for (char x = 'A'; x < 'Z'; x++) {
            characters.add(x);
        }
    }

    public List<Character> license(){
        List<Character> licenseCharacters = new ArrayList<>();

        for (int i = 0; i < 19; i++) {
            int randomInt = random.nextInt(100);
            if (randomInt < 50) {
                licenseCharacters.add(characters.get(random.nextInt(25)));
            } else {
                licenseCharacters.add('0');
            }
        }

        licenseCharacters.set(4, '_');
        licenseCharacters.set(9, '_');
        licenseCharacters.set(14, '_');

        return licenseCharacters;
    }

    public String licenseCodec(List<Character> characters){
        String coded = "";

        for (Character character : characters) {
            if (character == '0') {
                coded += String.valueOf(random.nextInt(9));
            } else {
                coded += character;
            }
        }

        return coded;
    }
}
