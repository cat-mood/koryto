package cat.mood.koryto.exception;

public class UserExist extends RuntimeException {
    public UserExist(String message) {
        super(message);
    }
    public UserExist() {}
}
