package me.kacper.product;


import org.bson.Document;

public class Product {

    private String name;

    public Product(String name) {
        this.name = name;
    }

    public Document toDocument(){
        Document document = new Document();
        document.put("name", this.name);
        return document;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
