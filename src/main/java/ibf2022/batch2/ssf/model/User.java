package ibf2022.batch2.ssf.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class User {

    @NotNull(message="Please enter your username")
    @Size(min=2, message="Username must be at least 2 characters")
    private String username;

    @NotNull(message="Please enter your username")
    @Size(min=2, message="Password must be at least 2 characters")
    private String password;

    public String captcha;
    public double answer;
    public boolean attempted = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "account [username=" + username + ", password=" + password + "]";
    }   


    public static JsonObject toJSON(String json){
        JsonReader r = Json.createReader(new StringReader(json));
        return r.readObject();
    }

    
    //create json object
    public static User create(JsonObject o){
        User u = new User();
        u.setUsername(o.getString("username"));
        u.setPassword(o.getString("password"));
        return u;
    }
    
       //method to create json WHICH IS CORRECT???
    public static User create(String jsonStr){
        JsonObject o = toJSON(jsonStr);
        User u = User.create(o);    
        u.setUsername(o.getString("username"));
        u.setPassword(o.getString("password"));
        return u;
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
            .add("username", this.username)
            .add("password", this.password)
            .build();
    }
    
}
