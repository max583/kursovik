package server;

public class User {
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private final String name;
    private final String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}

