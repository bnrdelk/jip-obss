import exceptions.UserArrayFullException;
import exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class UserMapService implements IUserService {
    private Map<Integer, User> users;
    private int capacity = 0;

    public UserMapService() {
        this.users = new HashMap<>();
    }

    @Override
    public void addUser(User newUser) throws UserArrayFullException {
        if (users.size() < capacity || capacity == 0) { // capacity 0 ise sınırsız kapasite
            users.put(newUser.getId(), newUser);
        } else {
            throw new UserArrayFullException("User array is full.");
        }
    }

    @Override
    public void deleteUser (int id) throws UserNotFoundException {
        if (users.containsKey(id)) {
            users.remove(id);
        } else {
            throw new UserNotFoundException("User not found.");
        }
    }

    @Override
    public boolean hasUser(int id) {
        return users.containsKey(id);
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new UserNotFoundException("User not found.");
        }
    }
}
