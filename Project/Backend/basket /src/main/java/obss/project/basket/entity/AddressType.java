package obss.project.basket.entity;

public enum AddressType {
    ISTANBUL_OFFICE("Istanbul Office") {
        @Override
        public Address getDefaultAddress() {
            return new Address(null, this, "..... istanbul", "Istanbul", "Turkey", "34000");
        }
    },
    ANKARA_OFFICE("Ankara Office") {
        @Override
        public Address getDefaultAddress() {
            return new Address(null, this, "..... ankara", "Ankara", "Turkey", "06000");
        }
    },
    OTHERS("Others") {
        @Override
        public Address getDefaultAddress() {
            return new Address(null, this, "undefined", "undefined", "undefined", "undefined");
        }
    };

    private final String description;

    AddressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract Address getDefaultAddress();
}
