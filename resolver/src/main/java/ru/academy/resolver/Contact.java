package ru.academy.resolver;


public class Contact {

    private String name;
    private String data;

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public Contact(String name, String data) {
        this.name = name;
        this.data = data;
    }
}