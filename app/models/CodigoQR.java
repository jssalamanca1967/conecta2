package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 *
 */

@Entity
@Table(name="codigoqr")
public class CodigoQR extends Model{

    @Id
    public Long id;

    @Constraints.Required
    @Column(unique=true)
    public String codigo;

    /**
     * Capacidad total en MB
     */
    @Constraints.Required
    public double capacidadTotal;

    /**
     * Capacidad consumida en MB
     */
    public double consumido;

    @OneToOne
    public Cliente cliente;

    @OneToOne
    public Usuario usuario;

    public static Finder<Long, CodigoQR> find = new Finder<Long,CodigoQR>(CodigoQR.class);

}
