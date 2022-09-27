package ku.cs.shop.services;

import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;

public class ProductListHardcodeDataSource implements DataSource<ProductList>{
    public ProductList productList;

    public ProductListHardcodeDataSource(){
        productList = new ProductList();
        hardcode();

    }

    public void hardcode(){
        productList.addProduct(new Product("baseball bat",25,1,"/ku/cs/images/baseball_bat.jpg"));
        productList.addProduct(new Product("football",55,1,"/ku/cs/images/football.jpg"));
        productList.addProduct(new Product("tennis racket",23.67,1,"/ku/cs/images/tennis_racket.jpg"));
        productList.addProduct(new Product("sport shoes",100,1,"/ku/cs/images/sport_shoes.jpg"));
    }

    @Override
    public ProductList readData() {
        return productList;
    }

    @Override
    public void writeData(ProductList productList) {
        this.productList = productList;
    }
}
