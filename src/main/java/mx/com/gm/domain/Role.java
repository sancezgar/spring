package mx.com.gm.domain;

import javax.persistence.*;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@Table(name="role")
public class Role implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;
    
    @NotEmpty
    private String nombre;
        
}
