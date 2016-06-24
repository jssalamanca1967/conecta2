package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * https://www.playframework.com/documentation/2.4.x/JavaEbean
 * http://ebean-orm.github.io/docs/persist/
 */

@Entity
@Table(name="usuario")
public class Usuario extends Model{

    @Id
    public Long id;

    @Constraints.Required
    public String nombre;

    @Constraints.Required
    public String contrasenia;

    @Constraints.Required
    @Constraints.Email
    @Column(unique=true)
    public String email;

    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date cumpleanios;

    @OneToMany
    public List<CodigoQR> codigos;

    /**
     * Sexo del usuario
     * sexo == "Masculino" || sexo == "Femenino"
     */
    @Constraints.Required
    public String sexo;

    public static Finder<Long, Usuario> find = new Finder<Long,Usuario>(Usuario.class);

    public static Usuario darUsuario(String email){
        List<Usuario> lista = Usuario.find.all();
        for (Usuario i : lista){
            if(i.email.equals(email)){
                return i;
            }
        }
        return null;
    }

}