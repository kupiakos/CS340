package server.models;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;
import server.db.IDAOObject;

import java.util.Objects;

public class User implements IDAOObject {

    @Expose
    private int id;

    @Expose
    private String username;

    @Expose
    private String password;

    public User() {
        this(0, "", "");
    }

    public User(int id, @NotNull String username, @NotNull String password) {
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            return this.equals((User) other);
        }
        return false;
    }

    public boolean equals(User other) {
        return (id == other.id &&
                Objects.equals(username, other.username) &&
                Objects.equals(password, other.password)
        );
    }

}
