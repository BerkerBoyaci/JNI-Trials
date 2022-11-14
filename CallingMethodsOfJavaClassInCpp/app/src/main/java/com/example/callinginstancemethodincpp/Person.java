package com.example.callinginstancemethodincpp;

public class Person {
    String name;
    int age;

    public Person(String name){
        this.name = name;
    }

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }


    public static String printPersonInfo(Person person){
        return person.name;
    }

    @Override
    public String toString(){
        return "Person [name=" + name + " , age = " + age + "]";
    }

    public static String printInfo(Person person){
        return person.toString();
    }

    public static void setInfo(Person person, String name, int age){
        person.name = name;
        person.age = age;
    }

}
