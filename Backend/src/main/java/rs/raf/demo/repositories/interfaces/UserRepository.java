package rs.raf.demo.repositories.interfaces;

import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.implementations.SqlUserRepository;

import java.util.List;

public interface UserRepository {

    public User addUser(User user) throws SqlUserRepository.UserWithEmailExistsException;
    public User findUserById(Integer id);
    public User findUserByEmail(String email);
    public User editUser(User user);
    public List<User> allUsers();
    public void deleteUser(Integer id);
    public void activateUser(Integer id);
    public void deactivateUser(Integer id);
    public List<User> usersByPage(Integer pageNum);
}
