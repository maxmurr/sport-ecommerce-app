package ku.cs.shop.models;

public class Product {
    private String name;
    private double price;
    private int amount;
    private String path;
    private String description;
    private String marketName;
    private int lowAmount;




    public Product() {
        this.name = null;
        this.price = 0;
        this.amount = 0;
        this.path = "images/profiledefault.png";
        this.description = "";
        this.marketName = null;
        this.lowAmount = 0;
    }

    public Product(String name, double price, int amount, String path) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.path = path;
        this.description = "";
        this.marketName = null;
        this.lowAmount = 0;

    }

    public boolean checkLowAmount(){
        if(amount-lowAmount <= 0){
            return true;
        }
        return false;
    }

    public void buyProduct(int amount){
        this.amount -= amount;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getAmount(){
        return amount;
    }

    public String getPath(){
        return path;
    }

    public String getDescription() {
        return description;
    }

    public String getMarketName() {
        return marketName;
    }

    public int getLowAmount() {
        return lowAmount;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public void setPath(String path){
        this.path = path;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public void setLowAmount(int lowAmount) {
        this.lowAmount = lowAmount;
    }

    public boolean checkName(String name){
        if (this.name.equals(name)){
            return true;
        }
        return false;
    }



    public String toCsv(){
        return name+","+price+","+amount+","+path
                + ","+description+","+marketName+","+lowAmount    ;
    }


    @Override
    public String toString() {
        return   name+","+price+","+amount+","+path
                + ","+description+","+marketName +","+lowAmount    ;

    }
}
