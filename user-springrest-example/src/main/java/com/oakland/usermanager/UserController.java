package com.oakland.usermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController publishes all endpoints in UserRestURIConstants.java
 * 
 */

@RestController
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  // Map to store users, eventually this will be a database
  Map<Integer, User> userData = new HashMap<>();


  @GetMapping("/login")
  public User login(@RequestParam(value = "id", defaultValue = "1111") int id,
      @RequestParam(value = "userName", defaultValue = "defaultName") String userName,
      @RequestParam(value = "password", defaultValue = "defaultPassword") String password,
      @RequestParam(value = "isSpecial", defaultValue = "false") boolean isSpecial) {
    return User.builder()
        .withId(id)
        .withUserName(userName)
        .withPassword(password)
        .build();
  }

  @RequestMapping(value = UserRestURIConstants.DUMMY_USER, method = RequestMethod.GET)
  public @ResponseBody User getDummyUser() {
    logger.info("Start getDummyUser");
    int dummyUserId = 9999;
    return userData.get(dummyUserId);
  }

  @RequestMapping(value = UserRestURIConstants.GET_USER, method = RequestMethod.GET)
  public @ResponseBody User getUser(@PathVariable("id") int userId) {
    logger.info("Start getUser. ID=" + userId);
    return userData.get(userId);
  }

  @RequestMapping(value = UserRestURIConstants.GET_ALL_USER, method = RequestMethod.GET)
  public @ResponseBody List<User> getAllUsers() {
    logger.info("Start getAllUsers.");
    List<User> users = new ArrayList<>();
    for (Integer i : userData.keySet()) {
      users.add(userData.get(i));
    }
    return users;
  }

  @RequestMapping(value = UserRestURIConstants.CREATE_USER, method = RequestMethod.POST)
  public @ResponseBody User createUser(@RequestBody User user) {
    logger.info("Start createUser.");
    userData.put(user.getId(), user);
    return user;
  }

  @RequestMapping(value = UserRestURIConstants.DELETE_USER, method = RequestMethod.PUT)
  public @ResponseBody User deleteUser(@PathVariable("id") int userId) {
    logger.info("Start deleteUser.");
    User user = userData.get(userId);
    userData.remove(userId);
    return user;
  }
}
