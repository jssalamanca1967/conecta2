package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by John on 16/06/2016.
 */
@Entity
@Table(name = "cliente")
public class Cliente extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nit;

    @Constraints.Required
    public String contrasenia;

    @OneToMany
    public List<CodigoQR> codigos;

    public static Finder<Long, Cliente> find = new Finder<Long,Cliente>(Cliente.class);


}
