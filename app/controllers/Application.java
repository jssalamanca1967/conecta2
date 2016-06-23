package controllers;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result login(){

        DynamicForm form = Form.form();
        return ok(login.render(form));
    }

    public Result loginSubmit(){

        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        return ok(login.render(form));
    }



}
