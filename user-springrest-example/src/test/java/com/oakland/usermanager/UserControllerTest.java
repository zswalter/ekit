package com.oakland.usermanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class UserControllerTest {

  public static final String server_uri = "http://localhost:8080";
  private Map<Integer, User> userData = new HashMap<>();

  @Before
  public void setUp() {
    User user1 = User.builder()
        .withId(9999)
        .withUserName("user1")
        .withPassword("TestPassword")
        .withIsSpecial(false)
        .build();
    User user2 = User.builder()
        .withId(9998)
        .withUserName("user2")
        .withPassword("TestPassword")
        .withIsSpecial(false)
        .build();
    User user3 = User.builder()
        .withId(9997)
        .withUserName("user3")
        .withPassword("TestPassword")
        .withIsSpecial(false)
        .build();

    userData.put(user1.getId(), user1);
    userData.put(user2.getId(), user2);
    userData.put(user3.getId(), user3);

  }


  @Test
  public void test_get_user() {
    RestTemplate restTemplate = new RestTemplate();
    User user = restTemplate.getForObject(server_uri + "/rest/user/1", User.class);
    assertEquals(1, user.getId());
    assertEquals("MainTestUser", user.getUserName());
    assertEquals("MainTestPassword", user.getPassword());
    assertFalse(user.getIsSpecial());

  }

  @Test
  public void test_get_all_users() {
    RestTemplate restTemplate = new RestTemplate();
    // json to linked hashmap
    @SuppressWarnings("unchecked")
    List<LinkedHashMap> users =
        restTemplate.getForObject(server_uri + UserRestURIConstants.GET_ALL_USER, List.class);
    System.out.println(users.size());
    for (LinkedHashMap map : users) {
      System.out.println("ID=" + map.get("id") + ",Name=" + map.get("name"));
    }
  }

  @Test
  public void test_create_user() {
    RestTemplate restTemplate = new RestTemplate();
    User user = User.builder()
        .withId(1)
        .withUserName("MainTestUser")
        .withPassword("MainTestPassword")
        .withIsSpecial(false)
        .build();
    User response = restTemplate.postForObject(server_uri + UserRestURIConstants.CREATE_USER, user,
        User.class);

  }

  @Test
  public void test_delete_user() {
    // TODO
  }
}
