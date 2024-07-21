package co.edu.iudigital.helpmeiud.models;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "roles")
public class Role implements Serializable {

 static final long serialVersionUID = 1L;

 public Role(){
 }

 public Role(Long id) {
  this.id = id;
 }

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 Long id;

 @Column(unique = true, length = 100)
 String nombre;

 @Column
 String descripcion;

 // bidireccional
 // Opcional
   /* @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    List<Usuario> usuarios;*/
}