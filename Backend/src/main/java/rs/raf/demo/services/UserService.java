package rs.raf.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.implementations.SqlUserRepository;
import rs.raf.demo.repositories.interfaces.UserRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public User addUser(User user) throws SqlUserRepository.UserWithEmailExistsException {
        try {
            return userRepository.addUser(user);
        } catch (SqlUserRepository.UserWithEmailExistsException e) {
            return null;
        }
    }

    public User findUser(Integer id) {
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> allUsers() {
        return userRepository.allUsers();
    }

    public List<User> usersByPage(Integer pageNum) {
        return userRepository.usersByPage(pageNum);
    }


    public User editUser(User user) {
        return userRepository.editUser(user);
    }

    public void deleteUser(Integer id){
        this.userRepository.deleteUser(id);
    }

    public void activateUser(Integer id){
        this.userRepository.activateUser(id);
    }

    public void deactivateUser(Integer id){
        this.userRepository.deactivateUser(id);
    }

    public String login(String email, String password) {

        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUserByEmail(email);
        if(user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime());
        expiresAt.setMonth(expiresAt.getMonth() + 1); //traje mesec dana

        Algorithm algorithm = Algorithm.HMAC256("secret");


        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("lastname", user.getLastname())
                .withClaim("role", user.getRole().toString())
                .withClaim("status", user.getStatus())
                .sign(algorithm);
    }
    public boolean isAuthorized(String token) {

        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String email = jwt.getSubject();

            User user = this.userRepository.findUserByEmail(email);
            if (user == null) {
                return false;
            }

            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public User returnUserFromToken(String token){

        System.out.println(token);
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String email = jwt.getSubject();
            User user = this.userRepository.findUserByEmail(email);
            return user;

        }catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
