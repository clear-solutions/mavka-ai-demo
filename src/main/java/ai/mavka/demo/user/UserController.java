package ai.mavka.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<String, User> userStorage = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (userStorage.containsKey(user.getUsername())) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        userStorage.put(user.getUsername(), user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userStorage.get(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User user) {
        if (!userStorage.containsKey(username)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        userStorage.put(username, user);
        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        if (!userStorage.containsKey(username)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        userStorage.remove(username);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }



    public class User {
        private String username;
        private String email;
        private String fullName;
        public User() {}

        public User(String username, String email, String fullName) {
            this.username = username;
            this.email = email;
            this.fullName = fullName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
