package ku.cs.shop.services;

import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductListFileDataSourceTest {

    @Test
    @DisplayName("เพิ่มสินค้า")
    void testAddProduct(){
        DataSource<ProductList> productDataSource = new ProductListHardcodeDataSource();

        ProductList list = productDataSource.readData();
        list.addProduct(new Product("whistle",10,1,"/ku/cs/images/whistle.jpg"));
        DataSource<ProductList> fileDS = new ProductListFileDataSource("unit-test", "products.csv");
        fileDS.writeData(list);


        assertEquals(5, list.countProduct());
    }






    @Test
    @DisplayName("เขียนไฟล์")
    void testWriteProductListToFile(){
        DataSource<ProductList> dataSource = new ProductListHardcodeDataSource();
        ProductList list = dataSource.readData();

        DataSource<ProductList> fileDS = new ProductListFileDataSource("unit-test", "products.csv");
        fileDS.writeData(list);

        assertTrue(true);

    }

}