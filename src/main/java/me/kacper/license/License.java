package me.kacper.license;

import org.bson.Document;

public class License {

    private final String product;
    private final String license;
    private final int addresses;

    public License(String license, int addresses, String product) {
        this.license = license;
        this.addresses = addresses;
        this.product = product;
    }

    public Document toDocument(){
        Document document = new Document();
        document.put("product", product);
        document.put("license", this.license);
        document.put("addresses", this.addresses);
        return document;
    }

    public String getProduct() {
        return product;
    }

    public String getLicense() {
        return license;
    }

    public int getAddresses() {
        return addresses;
    }
}
