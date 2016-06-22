package controllers;

import models.Usuario;
import play.*;
import play.data.Form;
import play.mvc.*;

import java.util.Date;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by John on 17/06/2016.
 */
public class UsuarioController extends Controller {

    public Result prueba(){

        Usuario user = new Usuario();

        user.nombre = "Johnathan";
        user.email = "js.salamanca1967@uniandes.edu.co";
        user.contrasenia = "asdf";
        user.cumpleanios = new Date(94,3,20);

        user.save();

        return ok(views.html.index.render("Usuario creado"));
    }

    public Result darUsuarios(){

        List<Usuario> lista = Usuario.find.all();
        return ok(views.html.Usuario.index.render(lista));

    }

    public Result show(Long id){
        Usuario usuario = Usuario.find.byId(id);
        if(usuario == null)
            return ok(views.html.Usuario.show.render(usuario));
        else
            return ok(views.html.Usuario.show.render(usuario));
    }

    public Result editar(Long id){
        Usuario usuario = Usuario.find.byId(id);
        return ok(views.html.Usuario.show.render(usuario));
    }

    public Result crear(){
        Form<Usuario> formulario = Form.form(Usuario.class);
        return ok(views.html.Usuario.newUser.render(formulario));
    }

    public Result guardarNuevo(){
        Form<Usuario> formulario = Form.form(Usuario.class).bindFromRequest();

        if(request().method().equals("POST")){
            if(!formulario.field("contrasenia").value().isEmpty()){
                if(!formulario.field("confirm_password").value().equals(formulario.field("contrasenia").value())){
                    formulario.reject("confirm_password", "Las contraseñas no coinciden");
                }
            }

            if(!formulario.field("email").value().isEmpty()){
                if(!formulario.field("confirm_email").value().equals(formulario.field("email").value())){
                    formulario.reject("confirm_password", "Los correos electrónicos no coinciden");
                }

                List<Usuario> lista = Usuario.find.where().eq("email", formulario.field("email").value()).findList();

                if(lista.size() > 0){
                    formulario.reject("email", "Ya existe un usuario con ese e-mail registrado");
                }

            }

            if(formulario.hasErrors()){
                System.out.println(formulario.errorsAsJson());
                System.out.println(formulario.error("cumpleanios").message());

                return badRequest(views.html.Usuario.newUser.render(formulario));
            }
            else{
                Usuario usuario = formulario.get();
                usuario.save();

                return ok(views.html.index.render("Todo listo"));
            }

        }
        else{
            return ok(views.html.Usuario.newUser.render(formulario));
        }
    }


}
