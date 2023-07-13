package me.kacper.product.manager;

import com.mongodb.client.model.Filters;
import me.kacper.Main;
import me.kacper.product.Product;

public class ProductManager {

    public void createProduct(Product product){
        Main.getDatabase().getProductCollection()
                .insertOne(product.toDocument());
    }

    public boolean productExists(Product product) {
        return Main.getDatabase().getProductCollection()
                .find(Filters.eq("product", product.getName())).first() != null;
    }

    public void deleteProduct(Product product) {
        Main.getDatabase().getProductCollection().deleteOne(product.toDocument());
    }
}
