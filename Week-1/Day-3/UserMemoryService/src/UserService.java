import exceptions.UserArrayFullException;

public class UserService {
    private User[] users;
    private int userIndex = 0;
    private int capacity;

    public UserService(int capacity) {
        this.capacity = capacity;
        this.users = new User[capacity];
    }

    public void addUser(User newUser) throws UserArrayFullException {
        if(this.userIndex < this.capacity ){
            this.users[userIndex] = newUser;
            userIndex++;
            return;
        }
        throw new UserArrayFullException("User array is full.");
    }

}
