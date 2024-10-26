package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private String category;
    private String size;
    private Double price;
    private Integer qty;
    private String supplier;
}
