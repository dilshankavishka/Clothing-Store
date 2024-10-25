
package entity;

        import jakarta.persistence.Entity;
        import jakarta.persistence.Id;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AdminLoginEntity {
    @Id
    private String email;
    private String password;
}