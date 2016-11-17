package server.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class UserSession {
    private static Random random = new Random();
    @SerializedName("playerID")
    @Expose
    private int userId;
    @SerializedName("token")
    @Expose
    private int token;

    public UserSession() {
    }

    public UserSession(int userId, int token) {
        this.userId = userId;
        this.token = token;
    }

    @NotNull
    public static UserSession newSession(int userId) {
        // Assume no duplicates
        return new UserSession(userId, random.nextInt());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        if (userId != that.userId) return false;
        return token == that.token;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + token;
        return result;
    }
}
