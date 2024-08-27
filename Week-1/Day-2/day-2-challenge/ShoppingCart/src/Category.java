public enum Category {
    ELECTRONICS(0.10),
    CLOTHING(0.20),
    GROCERY(0.30);

    private final double discountRate;

    Category(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
