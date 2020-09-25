package com.oakland.usermanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void test_creation_1() {
    User user = User.builder()
        .withId(1)
        .withUserName("TestName")
        .withPassword("TestPassword")
        .withIsSpecial(false)
        .build();

    assertEquals(1, user.getId());
    assertEquals("TestName", user.getUserName());
    assertEquals("TestPassword", user.getPassword());
    assertFalse(user.getIsSpecial());
  }

  @Test
  void test_creation_2() {
    User user = User.builder()
        .withId(2)
        .withUserName("TestName")
        .withPassword("TestPassword")
        .withIsSpecial(true)
        .build();

    assertEquals(2, user.getId());
    assertEquals("TestName", user.getUserName());
    assertEquals("TestPassword", user.getPassword());
    assertTrue(user.getIsSpecial());
  }

}
