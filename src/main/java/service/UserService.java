package service;

import exception.NoSuchUserException;
import exception.UserAlreadyDefinedException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public boolean exists(String login)
    {
        return findByLogin(login) != null;
    }

    public void createUser(User user) throws Exception
    {
        if(exists(user.getLogin())) throw new UserAlreadyDefinedException();
        else
        {
            saveUser(user);
        }
    }

    public void deleteUser(String login) throws Exception
    {
        User user = findByLogin(login);
        if(user == null) throw new NoSuchUserException();
        userRepository.delete(user);
    }

    public User createStandardUser(String login, String password) throws Exception
    {
        User user = new User(login, password);
        createUser(user);
        return user;
    }

    public User createAdminUser(String login, String password) throws Exception
    {
        User user = new User(login, password, true);
        createUser(user);
        return user;
    }

    public List<User> getAdmins()
    {
        return userRepository.findByAdmin(true);
    }

    public List<User> getStandard()
    {
        return userRepository.findByAdmin(false);
    }

    public void saveUser(User user)
    {
        userRepository.saveAndFlush(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

}
