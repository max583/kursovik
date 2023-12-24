package server.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.User;
import server.db.DB;

@SpringBootApplication

@RestController
public class Controller {

    @Autowired
    private DB db;
    @GetMapping("/appConfig")
    public ResponseEntity<String> appConfig(){
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"appConfig\":123");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @GetMapping("/getAllUsers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAllUsers(HttpServletResponse response){
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"users\":"+ new Gson().toJson(db.getAllUsers())+"}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @GetMapping("/getAllRoles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAllRoles(HttpServletResponse response){
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"roles\":" + new Gson().toJson(db.getAllRoles()) + "}");
        }
        catch (Exception e){
                return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
            }
    }

    @GetMapping("/getUserRoles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getUserRoles(@RequestParam String username, HttpServletResponse response){
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"roles\":"+ new Gson().toJson( db.getUserRoles(username))+"}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @GetMapping("/checkUserHasRole")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkUserHasRole(@RequestParam String username, @RequestParam String role, HttpServletResponse response){
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"hasRole\":"+db.checkUserHasRole(username, role)+"}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response){
        User user = new User(username, password);
        try {
            db.addUser(user);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":\""+e.getMessage()+"\"}");
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{}");
    }

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@RequestParam String username, HttpServletResponse response){
        try {
            db.deleteUser(username);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @PatchMapping("/setUserPassword")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> setUserPassword(@RequestParam String username, @RequestParam String password, HttpServletResponse response){
        try {
            db.setUserPassword(username, password);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @PostMapping("/setUserRole")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> setUserRole(@RequestParam String username, @RequestParam String role, HttpServletResponse response){
        try {
            db.grantRoleToUser(username, role);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":\""+e.getMessage()+"\"}");
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{}");
    }

    @PostMapping("/revokeUserRole")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> revokeUserRole(@RequestParam String username, @RequestParam String role, HttpServletResponse response){
        try {
        db.revokeRoleFromUser(username, role);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }

    @GetMapping("/checkUserExists")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkUserExists(@RequestParam String username, @RequestParam String password, HttpServletResponse response){
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"exists\":" + db.checkUserExists(username, password) + "}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json").body("{\"error\":"+ new Gson().toJson(e.getMessage())+"}");
        }
    }
}
