package repository;
import model.User;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;



public class UserRepository implements CrudRepository<User, String> {
    private final Map<String, User> userMap;

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User instance cannot be null.");
        }
    }

    private void validateUserName(User user) {
        if (userMap.containsValue(user.getUserName())) {
            throw new IllegalArgumentException("Username in use. Must be Unique.");
        }
    }


    private void validateEmail(User user) {
        if (userMap.containsValue(user.getEmailAddress())) {
            throw new IllegalArgumentException("Email already in use.");
        }
    }

    private void validateUserId(String userId) {
        if ((!userMap.containsKey(userId)) || (userMap.get(userId) == null)) {
            throw new IllegalArgumentException("User id Invalid");
        }
    }



    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    @Override
    public void create(User user) {
        validateUser(user);
        validateUserName(user);
        validateEmail(user);

        userMap.put(user.getUniqueId(), user);

    }

    @Override
    public User read(String userId) {
        validateUserId(userId);
        return userMap.get(userId);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void update(String userId, User user) {
        validateUser(user);
        validateUserId(userId);
        validateUserName(user);
        validateEmail(user);
        userMap.put(userId, user);
    }

    @Override
    public void delete(String userId) {
        validateUserId(userId);
        userMap.remove(userId);
    }


}
