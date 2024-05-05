package com.pack.seproject;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.pack.seproject.model.Category;
import com.pack.seproject.model.Reminder;
import com.pack.seproject.model.User;
import com.pack.seproject.service.CategoryService;
import com.pack.seproject.service.ReminderService;
import com.pack.seproject.service.UserService;

@SpringBootTest
class SeprojectApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ReminderService reminderService;
    
    @Test
    void login(){
        String username = "Vamsi";
        User user = userService.findByUsername(username);
        assertNotEquals(null, user);
    }

    @Test
    void saveUser(){
        User user = new User("user1", "user1@123", "first1", "last1", "user1@gmail.com");
        User test = userService.saveUser(user);
        assertNotEquals(null, test);
    }

    @Test
    @Transactional
    @Rollback(true)
    void saveCategory(){
        User user = userService.findByUsername("user1");
        Category category = new Category("Hospital", user);
        Category test = categoryService.saveCategory(category);
        assertNotEquals(null, test);
    }

    @Test
    @Transactional
    @Rollback(true)
    void saveTask(){
        LocalDateTime lt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        User user = userService.findByUsername("Vamsi");
        Category category = categoryService.findByCategoryName("Hospital");
        Reminder reminder = new Reminder("test1","testing",lt, "2 min", user, category, "Inprogress", 0);
        Reminder test = reminderService.saveReminder(reminder);
        assertNotEquals(null, test);
    }
}
