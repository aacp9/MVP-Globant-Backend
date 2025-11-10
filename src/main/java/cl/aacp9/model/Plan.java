package cl.aacp9.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //incluye @setter,@Setter, ToString 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="plan")
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id")
    private Integer id;
    private String nombre;
    private int precio;
    private String servicio;
    private Boolean estado;
}
