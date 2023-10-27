package src;

import java.util.ArrayList;
import java.util.List;

public class CreateProduct {
    private List<Product> productList;
    public CreateProduct(){
        productList = new ArrayList<>();
    }
    public void addProductList(Product product){
        productList.add(product);
    }
    public List<Product> getProductList(){
        return productList;
    }
}
