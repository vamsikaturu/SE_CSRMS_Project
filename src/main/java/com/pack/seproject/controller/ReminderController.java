package com.pack.seproject.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pack.seproject.model.Category;
import com.pack.seproject.model.Reminder;
import com.pack.seproject.model.User;
import com.pack.seproject.service.CategoryService;
import com.pack.seproject.service.ReminderService;
import com.pack.seproject.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ReminderController{
    
    @Autowired
    ReminderService reminderService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
	JavaMailSender javaMailSender;


    @GetMapping("/taskpage")
    public String task(@RequestParam String username, Model m){
        m.addAttribute("username",username);
        m.addAttribute("reminder", new Reminder());
        return "add-task-page";
    }

    @PostMapping("/addTask")
    public String addTask(Reminder reminder, Model m){
        User user = userService.findByUsername(reminder.getUser().getUsername());
        Category category = categoryService.findByCategoryName(reminder.getCategory().getCategoryName());
        Reminder task = new Reminder(reminder.getTitle(), reminder.getDescription(), reminder.getDateTime(), 
                        reminder.getRepeat(), user, category, reminder.getStatus(), 0);
        
        reminderService.sendReminder(task, user.getEmail(), reminder.getDateTime(), task.getRepeat().split(" "));
        reminderService.saveReminder(task);
        

        return "redirect:/userhome";
    }


    @GetMapping("/viewtaskpage")
    public String viewTask(@RequestParam String id, Model m) {
        List<Reminder> reminders = reminderService.findByUserId(Integer.parseInt(id));
        List<Category> categories = categoryService.findByUserId(Integer.parseInt(id));
        m.addAttribute("reminder", reminders);
        m.addAttribute("categories", categories);
        m.addAttribute("id", id);
		if(reminders.size() == 0){
            m.addAttribute("data","null");
			System.out.println("no task");
		}
        return "view-task-page";
    }
    
    @GetMapping("/searchTask")
    public String searchTask(@RequestParam String value, @RequestParam String id, @RequestParam String date , Model m) {
        
        System.out.println(date);
        List<Reminder> reminder = reminderService.searchTask(value, date);
        
        m.addAttribute("reminder", reminder);
        m.addAttribute("id", id);
        return "view-task-page";
    }
    

    @PostMapping("updateTask")
    public String updateTask(Reminder reminder){

        Reminder updateReminder = reminderService.findByTaskId(reminder.getTaskId());
        Category category = categoryService.findByCategoryName(reminder.getCategory().getCategoryName());
        LocalDateTime initialDateTIme = updateReminder.getDateTime();
        updateReminder.setTitle(reminder.getTitle());
        updateReminder.setDateTime(reminder.getDateTime());
        updateReminder.setDescription(reminder.getDescription());
        updateReminder.setCategory(category);
        updateReminder.setIsUpdate(1);

        reminderService.sendReminder(updateReminder, updateReminder.getUser().getEmail(), updateReminder.getDateTime(), 
                        updateReminder.getRepeat().split(" "));
        reminderService.updateReminder(updateReminder, initialDateTIme);

        return "redirect:/viewtaskpage?id="+reminder.getUser().getId();
    }


    @RequestMapping("deleteTask")
    public String deleteTask(@RequestParam("taskId") int taskId, @RequestParam("id") String id){
        reminderService.deleteReminder(taskId);
        return "redirect:/viewtaskpage?id="+id;
    }
}
