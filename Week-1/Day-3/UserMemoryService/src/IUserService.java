
import exceptions.*;

public interface IUserService {
    void addUser(User newUser) throws UserArrayFullException;
    void deleteUser(int id) throws UserNotFoundException;
    boolean hasUser(int id);
    User getUserById(int id) throws UserNotFoundException;
}
