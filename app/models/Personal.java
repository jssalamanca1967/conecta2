package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by John on 22/06/2016.
 */
public class Personal extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nombreUsuario;

    @Constraints.Required
    public String contrasenia;

    public static Finder<Long, Personal> find = new Finder<Long,Personal>(Personal.class);

}
