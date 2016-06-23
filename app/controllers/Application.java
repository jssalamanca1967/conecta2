package controllers;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.data.*;
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

    public Result login() {
        return ok(
                login.render(form(Application.Login.class))
        );
    }

    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.index()
            );
        }
    }

    public String validate() {
        if (User.authenticate(email, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }

    public class Login {

        public String email;
        public String password;

    }
}

