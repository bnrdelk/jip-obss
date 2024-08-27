package obss.project.basket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Seller")
public class Seller extends User {

    @Column(name = "products")
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products;

}
