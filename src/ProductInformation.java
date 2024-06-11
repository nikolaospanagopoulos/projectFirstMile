public class ProductInformation {
    Product product;
    int productQuantity;

    public ProductInformation(Product product, int productQuantity) {
        this.product = product;
        this.productQuantity = productQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "ProductInformation{" +
                "product=" + product +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
