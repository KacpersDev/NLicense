package me.kacper.license.manager;

import com.mongodb.client.model.Filters;
import me.kacper.Main;
import me.kacper.license.License;
import org.bson.Document;

public class LicenseManager {

    public void createLicense(License license){
        Document document = new Document();
        document.put("product", license.getProduct());
        document.put("license", license.getLicense());
        document.put("addresses", license.getAddresses());
        Main.getDatabase().getLicenseCollection().insertOne(document);
    }

    public boolean licenseExists(License license) {
        return Main.getDatabase().getLicenseCollection()
                .find(Filters.eq("license", license.getLicense())).first() != null;
    }

    public void deleteLicense(License license) {
        Document document = Main.getDatabase()
                .getLicenseCollection().find(Filters.eq("license", license.getLicense())).first();
        if (document != null) {
            if (document.getString("product").equalsIgnoreCase(license.getProduct())) {
                Main.getDatabase().getLicenseCollection().deleteOne(document);
            }
        }
    }
}
