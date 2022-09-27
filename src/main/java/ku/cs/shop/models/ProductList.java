package ku.cs.shop.models;

import java.util.ArrayList;

public class ProductList {
    private ArrayList<Product> products;

    public  ProductList(){
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public int countProduct(){
        return products.size();
    }

    public Product getProduct(int index){
        return products.get(index);
    }

    public Product searchProductByName(String name){
        for (Product product: products){
            if (product.checkName(name)){
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public String toCsv(){
        String result = "";
        for (Product product: products){
            result += (product.toCsv() + "\n");
        }
        return result;
    }

    public void clear() {
        products.clear();
    }
}
