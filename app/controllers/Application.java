package controllers;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.Usuario;
import java.util.List;
import play.libs.Crypto;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    public Result login(){

        DynamicForm form = Form.form();
        return ok(login.render(form));
    }

    public Result loginSubmit(){

        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        Logger.info("Capto el mensaje "+ username +  " "+password);
        List<Usuario> lista = Usuario.find.all();
        for (Usuario u : lista) {
            Logger.info(u.email);
        }
        Usuario user = null;
        boolean encontro = false;
        for (Usuario i: lista){
            if (i.email.equals(username)){
                user = i;
                Logger.info("Encontro a un usuario");
                encontro = true;
                break;
            }
        }
        if (!encontro){
            return badRequest("No se encontró un usuario con este correo");
        }
        if (!checkPassword(password, user.contrasenia)){
            return badRequest("La contraseña es incorrecta");
        }
        Logger.info("Paso la prueba de contraseña");
        session().clear();
        session("username", username);
        return ok(login.render(form));
    }

    public boolean checkPassword(String webPassword, String password){
        String encoded = play.Play.application().injector().instanceOf(Crypto.class).sign(webPassword);
        Logger.info("La contra codificada es "+ encoded);
        return encoded.equals(password);
    }
}

