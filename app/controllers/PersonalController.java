package controllers;

import models.Personal;
import play.data.Form;
import play.mvc.*;

import java.util.List;

/**
 * Created by John on 22/06/2016.
 */
public class PersonalController extends Controller {

    public Result crear() {
        Form<Personal> formulario = Form.form(Personal.class);
        return ok(views.html.Personal.newPersonal.render(formulario));
    }

    public Result guardarNuevo(){
        Form<Personal> formulario = Form.form(Personal.class).bindFromRequest();

        if(request().method().equals("POST")){
            if(!formulario.field("contrasenia").value().isEmpty()){
                if(!formulario.field("confirm_password").value().equals(formulario.field("contrasenia").value())){
                    formulario.reject("confirm_password", "Las contraseñas no coinciden");
                }
            }

            if(!formulario.field("nombreUsuario").value().isEmpty()){

                List<Personal> lista = Personal.find.where().eq("nombreUsuario",
                        formulario.field("nombreUsuario").value()).findList();

                if(lista.size() > 0){
                    formulario.reject("nombreUsuario", "Ya existe un usuario con ese nombre de usuario registrado");
                }
            }

            if(formulario.hasErrors()){

                return ok(views.html.Personal.newPersonal.render(formulario));
            }
            else{
                Personal personal = formulario.get();
                personal.save();

                return ok(views.html.index.render());
            }
        }
        else{
            return ok(views.html.Personal.newPersonal.render(formulario));
        }
    }

    public Result guardarEditado(Long id) {

        Form<Personal> formulario = Form.form(Personal.class).bindFromRequest();

        if(request().method().equals("POST")){

            boolean cambioPassword = false;

            Personal usuarioActual = Personal.find.byId(id);

            formulario.discardErrors();

            if(!formulario.field("contrasenia").value().isEmpty()){
                if(!formulario.field("confirm_password").value().equals(formulario.field("contrasenia").value())){
                    formulario.reject("confirm_password", "Las contraseñas no coinciden");
                }
                else{
                    cambioPassword = true;
                }
            }

            if(formulario.hasErrors()){
                return ok(views.html.index.render());
            }
            else {

                Personal cliente = usuarioActual;

                if(cambioPassword){
                    cliente.contrasenia = formulario.field("contrasenia").value();
                }

                cliente.nombreUsuario = formulario.field("nombre").value();

                cliente.save();

                return ok(views.html.index.render());
            }

        }
        else{
            return ok(views.html.index.render());
        }
    }

    public Result editar(long id) {

        Form<Personal> formulario = Form.form(Personal.class);

        Personal personal = Personal.find.byId(id);

        formulario.fill(personal);

        return ok(views.html.Personal.editar.render(formulario, id));

    }


    public Result personals() {
        return Results.TODO;
    }


    public Result mostrar(long id) {
        return Results.TODO;
    }
}
