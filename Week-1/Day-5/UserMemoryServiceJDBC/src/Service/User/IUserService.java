package Service.User;

import Table.User;
import exceptions.*;

import java.sql.SQLException;

public interface IUserService {
    void addUser(User newUser) throws SQLException;
    void deleteUser(int id) throws UserNotFoundException, SQLException;
    boolean hasUser(int id) throws SQLException;
    User getUserById(int id) throws UserNotFoundException, ClassNotFoundException;
}
