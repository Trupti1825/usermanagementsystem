package com.UserManagementSystem.controller;

import com.UserManagementSystem.entity.User;
import com.UserManagementSystem.repository.UserRepository;
import com.UserManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


/*-----------THIS METHOD IS USE FOR USER REGISTRATION----------*/
    @PostMapping("/signup")
    public ResponseEntity<String>registerUser(@RequestBody User user){
        if(userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User register successfully!");
    }



/*-------------THIS METHOD IS USE FOR USER SIGNIN----------------*/
    @PostMapping("/signin")
    public ResponseEntity<String>signInUser(@RequestBody User loginuser){
        User user = userRepository.findByUsernameOrEmail(loginuser.getUsername(),loginuser.getEmail());
        if (user != null && user.getPassword().equals(loginuser.getPassword())){
            return ResponseEntity.ok("User Login Successfully");
        } else {
        return ResponseEntity.badRequest().body("Invalid username/email or password!");
        }
    }



/*-------------THIS METHOD IS USE FOR RESET PASSWORD-------------*/
@PostMapping("/resetpassword")
    public ResponseEntity<String>resetpassword(@RequestBody User user) {
    try {
        User existingUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            existingUser.setPassword(user.getPassword());
            userRepository.save(existingUser);
            return ResponseEntity.ok("Password reset successfully!");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password.");
    }
}

    /*-------------THIS METHOD IS USE FOR GET ALL LIST OF USER-------------*/
    @GetMapping("/getallusers")
    public List<User> getAllUser(User user) {
        return userService.getAllUsers();
    }


    /*-------------THIS METHOD IS USE FOR GET USER BY ID-------------*/
    @GetMapping("/getbyid/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }


    /*-------------THIS METHOD IS USE FOR DELETE USER BY ID-------------*/
    @DeleteMapping("/deletebyid/{id}")
    public String deleteUserById(@PathVariable Long id){
        userRepository.deleteById(id);
        return "User deleted successfully";
    }


    /*-------------THIS METHOD IS USE FOR UPDATE USER BY ID------------*/
    @PutMapping("/updateuser/{id}")
    public User updateUser(@RequestBody User user,@PathVariable Long id){
        User existingUser = userRepository.findById(id).orElse(null);
        existingUser.setUsername(user.getUsername());
        existingUser.setAddress(user.getAddress());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setConfirmpassword(user.getPassword());
        return userRepository.save(existingUser);

    }


    /*----------------THIS METHOD IS USE FOR PAGINATION----------------*/
    @PostMapping("/search")
    public ResponseEntity<Page<User>>searchUsers(@RequestBody User searchQuery,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        Page<User> users = userRepository.findAll(
                Example.of(searchQuery),
                PageRequest.of(page,size)
        );
        return ResponseEntity.ok(users);
    }
}
