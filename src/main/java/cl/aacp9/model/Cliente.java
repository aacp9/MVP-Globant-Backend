package cl.aacp9.model;

import java.util.Date;

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
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cliente_id")
    private Integer id;
    private String nombre;
    private String apellido;
    private String run;
    private String direccion;
    
    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;
    
    private Boolean estado;


}
