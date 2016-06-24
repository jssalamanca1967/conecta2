package controllers;

import models.CodigoQR;
import models.Usuario;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.index;
import java.util.List;
import java.util.ArrayList;


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
            return ok(index.render());
        }
    }

    public Result codigos() {
        String authLevel = session("auth");
        Logger.info("El usuario es de nivel "+ authLevel);
        if (authLevel == null)
                return badRequest("Debe iniciar sesión para observar los codigos");
        List<CodigoQR> codigos = CodigoQR.find.all();
        List<CodigoQR> codigosUsuario = new ArrayList<CodigoQR>();
        if (authLevel.equals("user")){
            Usuario user = Usuario.darUsuario(session("email"));
            for (CodigoQR i : codigos){
                if(i.usuario.email.equals(user.email)){
                    codigosUsuario.add(i);
                }
            }
        }
        return ok(views.html.CodigoQR.index.render(codigosUsuario));
    }
    public Result mostrar(long id) {

        CodigoQR codigo = CodigoQR.find.byId(id);

        return ok(views.html.CodigoQR.show.render(codigo));
    }
    public Result editar(long id) {
        return ok(index.render());
    }

    public Result crear(){

        Form<CodigoQR> formulario = Form.form(CodigoQR.class);
        return ok(views.html.CodigoQR.newCodigoQR.render(formulario));

    }

}
