package NBCassignment.JH;

import java.util.ArrayList;
import java.util.List;

public class Order {
    ArrayList<Product> products;
    public Order(){
        products = new ArrayList<>();
    }
    public void addProducts(Product product){
        products.add(product);
    }
    public ArrayList<Product> getProducts(){
        return products;
    }
    public void clearOrder(){
        products.clear();
    }
    public double totalPrice(){
        double result = 0;
        for(Product product : products){
            result += product.getPrice();
        }
        return result;
    }
}
