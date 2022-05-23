package it.myke.identity.utils;

import lombok.Getter;
import lombok.Setter;

public class Person {
    @Setter
    @Getter
    public String name, gender;
    @Getter
    @Setter
    public int age;

    /**
     * This is the schema for the Storing of player custom Identity data.
     * @param name Name of the player
     * @param gender Gender of the player
     * @param age Age of the player
     */
    public Person(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;



    }


}
