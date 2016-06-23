package controllers;

import models.CodigoQR;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.index;

/**
 * Created by John on 17/06/2016.
 */
public class CodigoQRController extends Controller {

    public Result guardarNuevo() {
        return ok(index.render("Your new application is ready."));
    }

    public Result darUsuarios() {
        return ok(index.render("Your new application is ready."));
    }

    public Result mostrar(long id) {
        return ok(index.render("Your new application is ready."));
    }

    public Result editar(long id) {
        return ok(index.render("Your new application is ready."));
    }

    public Result crear(){

        Form<CodigoQR> formulario = Form.form(CodigoQR.class);
        return ok(views.html.CodigoQR.newCodigoQR.render(formulario));

    }

}
