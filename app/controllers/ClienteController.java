package controllers;

import models.Cliente;
import models.CodigoQR;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import java.util.List;

/**
 * Created by John on 17/06/2016.
 */
public class ClienteController extends Controller {

    public Result crear() {
        Form<Cliente> formulario = Form.form(Cliente.class);
        return ok(views.html.Cliente.newCliente.render(formulario));
    }

    public Result guardarNuevo(){
        Form<Cliente> formulario = Form.form(Cliente.class).bindFromRequest();

        if(request().method().equals("POST")){
            if(!formulario.field("contrasenia").value().isEmpty()){
                if(!formulario.field("confirm_password").value().equals(formulario.field("contrasenia").value())){
                    formulario.reject("confirm_password", "Las contraseñas no coinciden");
                }
            }

            if(!formulario.field("nit").value().isEmpty()){

                List<Cliente> lista = Cliente.find.where().eq("nit", formulario.field("nit").value()).findList();

                if(lista.size() > 0){
                    formulario.reject("nit", "Ya existe un usuario con ese NIT registrado");
                }
            }

            if(formulario.hasErrors()){

                return badRequest(views.html.Cliente.newCliente.render(formulario));
            }
            else{
                Cliente cliente = formulario.get();
                cliente.save();

                return ok(views.html.index.render());
            }
        }
        else{
            return ok(views.html.index.render());
        }
    }

    public Result guardarEditado(Long id) {

        Form<Cliente> formulario = Form.form(Cliente.class).bindFromRequest();

        if(request().method().equals("POST")){

            boolean cambioPassword = false;

            Cliente usuarioActual = Cliente.find.byId(id);

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
                return badRequest(views.html.Cliente.editar.render(formulario, id));
            }
            else {

                Cliente cliente = usuarioActual;

                if(cambioPassword){
                    cliente.contrasenia = formulario.field("contrasenia").value();
                }

                cliente.nombre = formulario.field("nombre").value();

                cliente.save();

                return redirect(routes.ClienteController.mostrar(id));
            }

        }
        else{
            return redirect(routes.ClienteController.mostrar(id));
        }
    }

    public Result editar(long id) {

        Form<Cliente> formulario = Form.form(Cliente.class);

        Cliente cliente = Cliente.find.byId(id);

        formulario.fill(cliente);

        return ok(views.html.Cliente.editar.render(formulario, id));

    }

    public Result mostrar(long id) {
        Cliente cliente = Cliente.find.byId(id);
        return ok(views.html.Cliente.show.render(cliente));
    }

    public Result clientes() {
        List<Cliente> cliente = Cliente.find.all();
        return ok(views.html.Cliente.index.render(cliente));
    }

    public Result agregarCodigo(Long id){

        DynamicForm form = Form.form();

        Cliente cliente = Cliente.find.byId(id);
        List<CodigoQR> codigos = CodigoQR.find.where().isNull("cliente_id").findList();

        return ok(views.html.Cliente.agregarCodigo.render(codigos, cliente));

    }

    public Result guardarCodigo(long idCliente, Long idCodigo) {



            CodigoQR code = CodigoQR.find.byId(idCodigo);
            Cliente cliente = Cliente.find.byId(idCliente);


            if (code != null) {
                cliente.codigos.add(code);
                cliente.save();
                code.cliente = cliente;
                code.save();
                return redirect(routes.ClienteController.mostrar(idCliente));
            } else {
                List<CodigoQR> codigos = CodigoQR.find.all();
                return badRequest(views.html.Cliente.agregarCodigo.render(codigos, cliente));
            }

    }
}
