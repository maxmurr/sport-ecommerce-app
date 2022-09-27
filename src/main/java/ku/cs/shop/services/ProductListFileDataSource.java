package ku.cs.shop.services;

import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;

import java.io.*;

public class ProductListFileDataSource implements DataSource<ProductList>{

    private String directoryName;
    private String filename;

    public ProductListFileDataSource(){
        this("csv-data", "products.csv");
    }

    public ProductListFileDataSource(String directoryName, String filename){
        this.directoryName = directoryName;
        this.filename = filename;
        createFileNotExist();
    }

    private void createFileNotExist(){
        File file = new File(directoryName);
        if(!file.exists()){
            file.mkdir();
        }
    }

    @Override
    public ProductList readData(){
        ProductList list = new ProductList();

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ( (line = buffer.readLine()) != null){
                String[] data = line.split(",");
                Product product = new Product(
                        data[0],
                        Double.parseDouble(data[1]),
                        Integer.parseInt(data[2]),
                        data[3]
                );
                product.setDescription(data[4]);
                product.setMarketName(data[5]);
                product.setLowAmount(Integer.parseInt(data[6]));
                list.addProduct(product);


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public void writeData(ProductList list) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(list.toCsv());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
