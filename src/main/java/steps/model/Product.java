package steps.model;

public class Product {

    private String productName;
    private Double productPrice;

    public Product(String prodName, Double prodPrice) {
        this.productName = prodName;
        this.productPrice = prodPrice;
    }

    public String getName() {
        return productName;
    }


    public Double getPrice() {
        return productPrice;
    }
}
