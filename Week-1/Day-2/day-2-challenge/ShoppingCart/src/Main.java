public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(3);

        cart.addProduct(new Product("A", 40, Category.ELECTRONICS));
        cart.addProduct(new Product("B", 70, Category.CLOTHING));
        cart.addProduct(new Product("C", 90, Category.GROCERY));
        cart.addProduct(new Product("C", 80, Category.GROCERY));

        System.out.println("*---        RECEIPT          ---*");
        System.out.println("Name    Price   *  Discount Rate  ->  Discounted Price");
        for (Product p: cart.getProducts()) {
            System.out.println(p.getName() + "  : " + p.getPrice() + "  *  " + p.getCategory().getDiscountRate() + "  ->  " + p.getDiscountedPrice());
        }
        System.out.println("---------------------------------");
        double totalPrice = cart.calculateTotalPrice();

        System.out.println("Total Price: " + totalPrice);
        System.out.println("*-------------------------------*");
    }
}
