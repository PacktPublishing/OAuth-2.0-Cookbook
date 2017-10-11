package example.packt.com.resourceownerpassword.login;

public class LoginService {

    public void loadUser(String login, String password, Callback callback) {
        if ("adolfo".equals(login) && "123".equals(password)) {
            User user = new User(login, password);
            callback.onSuccess(user);
        } else {
            callback.onFailed("user or password invalid");
        }
    }

    public interface Callback {
        void onSuccess(User user);
        void onFailed(String message);
    }
}
