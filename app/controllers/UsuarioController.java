package controllers;

import models.CodigoQR;
import models.Usuario;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.index;

import java.util.Date;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by John on 17/06/2016.
 */
public class UsuarioController extends Controller {



    public Result usuarios(){

        List<Usuario> lista = Usuario.find.all();
        return ok(views.html.Usuario.index.render(lista));

    }

    public Result mostrar(Long id){
        Usuario usuario = Usuario.find.byId(id);
        if(usuario == null)
            return ok(views.html.Usuario.show.render(usuario));
        else
            return ok(views.html.Usuario.show.render(usuario));
    }

    public Result editar(Long id){
        Usuario usuario = Usuario.find.byId(id);
        Form formulario = Form.form(Usuario.class).fill(usuario);
        return ok(views.html.Usuario.editar.render(formulario, id));
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
                    formulario.reject("confirm_email", "Los correos electrónicos no coinciden");
                }

                List<Usuario> lista = Usuario.find.where().eq("email", formulario.field("email").value()).findList();

                if(lista.size() > 0){
                    formulario.reject("email", "Ya existe un usuario con ese e-mail registrado");
                }
            }

            if(formulario.hasErrors()){

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

    public Result guardarEditado(Long id) {

        Form<Usuario> formulario = Form.form(Usuario.class).bindFromRequest();

        if(request().method().equals("POST")){

            boolean cambioPassword = false;

            Usuario usuarioActual = Usuario.find.byId(id);

            formulario.discardErrors();

            if(!formulario.field("contrasenia").value().isEmpty()){
                if(!formulario.field("confirm_password").value().equals(formulario.field("contrasenia").value())){
                    formulario.reject("confirm_password", "Las contraseñas no coinciden");
                }
                else{
                    cambioPassword = true;
                }
            }
            if(formulario.field("nombre").value().isEmpty()){
                formulario.reject("nombre", "El nombre no puede ser vacío");
            }

            if(formulario.hasErrors()){
                return badRequest(views.html.Usuario.editar.render(formulario, id));
            }
            else {

                Usuario usuario = usuarioActual;

                if(cambioPassword){
                    usuario.contrasenia = formulario.field("contrasenia").value();
                }

                usuario.nombre = formulario.field("nombre").value();

                usuario.save();

                return redirect(routes.Application.index());
            }

        }
        else{
            return redirect(routes.UsuarioController.mostrar(id));
        }
    }

    public Result redimirCodigo(Long id){

        DynamicForm form = Form.form();

        Usuario usuario = Usuario.find.byId(id);

        return ok(views.html.Usuario.agregarCodigo.render(form, usuario));

    }

    public Result guardarCodigo(long id) {

        if(request().method().equals("POST")) {

            DynamicForm form = Form.form().bindFromRequest();
            String codigo = form.get("codigo");
            System.out.println(codigo);

            CodigoQR code = CodigoQR.find.where().ieq("codigo", codigo).findUnique();
            Usuario user = Usuario.find.byId(id);

            if (code != null) {
                user.codigos.add(code);
                user.save();
                return redirect(routes.UsuarioController.mostrar(id));
            } else {
                form.reject("codigo", "El código que intenta redimir no existe");
                return badRequest(views.html.Usuario.agregarCodigo.render(form, user));
            }
        }
        else{
            return redirect(routes.UsuarioController.mostrar(id));
        }
    }


}
