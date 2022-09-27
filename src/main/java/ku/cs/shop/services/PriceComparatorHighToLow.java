package ku.cs.shop.services;

import ku.cs.shop.models.Product;

import java.util.Comparator;

public class PriceComparatorHighToLow implements Comparator {
    @Override
    public int compare(Object p1, Object p2) {
        Product product1 = (Product) p1;
        Product product2 = (Product) p2;

        if(product1.getPrice() > product2.getPrice()) return -1;
        if(product1.getPrice() < product2.getPrice()) return 1;
        return 0;
    }
}
