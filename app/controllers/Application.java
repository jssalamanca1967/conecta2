package controllers;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import play.libs.Crypto;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    public Result loginUsario(){

        DynamicForm form = Form.form();
        return ok(login.render(form));
    }

    public Result loginCliente(){

        DynamicForm form = Form.form();
        return ok(loginCliente.render(form));
    }

    public Result loginPersonal(){

        DynamicForm form = Form.form();
        return ok(loginPersonal.render(form));
    }

    public Result loginSubmit(){
        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        Logger.info("Capto el mensaje "+ username +  " "+password);
        List<Usuario> lista = Usuario.find.all();
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
        session("auth", "user");
        session("email", username);
        return ok(index.render());
    }

    public Result loginSubmitCliente(){
        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        Logger.info("Capto el mensaje "+ username +  " "+password);
        List<Cliente> lista = Cliente.find.all();
        Cliente user = null;
        boolean encontro = false;
        for (Cliente i: lista){
            if (i.nit.equals(username)){
                user = i;
                Logger.info("Encontro a un Cliente");
                encontro = true;
                break;
            }
        }
        if (!encontro){
            return badRequest("No se encontró un Cliente con este nit");
        }
        if (!checkPassword(password, user.contrasenia)){
            return badRequest("La contraseña es incorrecta");
        }
        Logger.info("Paso la prueba de contraseña");
        session().clear();
        session("auth", "client");
        return ok(index.render());
    }


    public Result loginSubmitPersonal(){
        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        Logger.info("Capto el mensaje "+ username +  " "+password);
        List<Personal> lista = Personal.find.all();
        Personal user = null;
        boolean encontro = false;
        for (Personal i: lista){
            if (i.nombreUsuario.equals(username)){
                user = i;
                Logger.info("Encontro a un empleado");
                encontro = true;
                break;
            }
        }
        if (!encontro){
            return badRequest("No se encontró un empleado con este nombre de usuario");
        }
        if (!checkPassword(password, user.contrasenia)){
            return badRequest("La contraseña es incorrecta");
        }
        Logger.info("Paso la prueba de contraseña");
        session().clear();
        session("auth", "personal");
        return ok(index.render());
    }

    public boolean checkPassword(String webPassword, String password){
        String encoded = play.Play.application().injector().instanceOf(Crypto.class).sign(webPassword);
        Logger.info("La contra codificada es "+ encoded);
        return encoded.equals(password);
    }
}

