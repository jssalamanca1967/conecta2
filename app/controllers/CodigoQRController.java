package controllers;

import models.CodigoQR;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.index;

import java.util.List;

/**
 * Created by John on 17/06/2016.
 */
public class CodigoQRController extends Controller {

    public Result guardarNuevo() {

        Form<CodigoQR> form = Form.form(CodigoQR.class).bindFromRequest();

        if(request().method().equals("POST")){
            if(form.hasErrors()){
                return badRequest(views.html.CodigoQR.newCodigoQR.render(form));
            }
            else{
                CodigoQR codigo = form.get();
                codigo.save();

                return redirect(routes.CodigoQRController.mostrar(codigo.id));
            }

        }
        else{
            return ok(index.render("Your new application is ready."));
        }
    }

    public Result codigos() {

        List<CodigoQR> codigos = CodigoQR.find.all();

        return ok(views.html.CodigoQR.index.render(codigos));
    }

    public Result mostrar(long id) {

        CodigoQR codigo = CodigoQR.find.byId(id);

        return ok(views.html.CodigoQR.show.render(codigo));
    }

    public Result editar(long id) {
        return ok(index.render("Your new application is ready."));
    }

    public Result crear(){

        Form<CodigoQR> formulario = Form.form(CodigoQR.class);
        return ok(views.html.CodigoQR.newCodigoQR.render(formulario));

    }

}
