public class ShoppingCart {
    private Product[] products;
    private int ctr;
    private double total = 0;

    public ShoppingCart(int size) {
        products = new Product[size];
        ctr = 0;
    }

    public void addProduct(Product product) {
        if (ctr < products.length) {
            products[ctr] = product;
            ctr++;
        } else {
            System.out.println("Shopping cart is full, " + product.getName() + " can't be added.");
        }
    }

    public double calculateTotalPrice() {
        for (int i = 0; i < ctr; i++) {
            total += products[i].getDiscountedPrice();
        }
        return total;
    }

    public Product[] getProducts() {
        return products;
    }

}
