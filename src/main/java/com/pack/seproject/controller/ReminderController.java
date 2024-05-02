package com.pack.seproject.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pack.seproject.model.Category;
import com.pack.seproject.model.Reminder;
import com.pack.seproject.model.User;
import com.pack.seproject.repository.CategoryRepository;
import com.pack.seproject.repository.ReminderRepository;
import com.pack.seproject.repository.UserRespository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ReminderController{
    
    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    UserRespository userRespository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private TaskScheduler scheduler;

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
        User user = userRespository.findByUsername(reminder.getUser().getUsername());
        Category category = categoryRepository.findByCategoryName(reminder.getCategory().getCategoryName());
        String[] repeat = reminder.getRepeat().split(" ");
        Reminder task = new Reminder(reminder.getTitle(), reminder.getDescription(), reminder.getDateTime(), reminder.getRepeat(), user, category, reminder.getStatus());
        reminderRepository.save(task);

        if(!repeat[0].equals("don't")){
            sendReminder(task, user.getEmail(), reminder.getDateTime(), repeat);
        }

        return "redirect:/userhome";
    }

    public void sendReminder(Reminder reminder, String email, LocalDateTime localDateTime, String[] repeat){
        long time = 0;
        if(repeat[1].equals("min")){
            time = Integer.parseInt(repeat[0])*60*1000;
        }
        else if(repeat[1].equals("hr")){
            time = Integer.parseInt(repeat[0])*60*60*1000;
        }
        else if(repeat[1].equals("day")){
            time = Integer.parseInt(repeat[0])*24*60*60*1000;
        }

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        LocalDateTime scheduledDateTime = localDateTime;

        Runnable task = () ->{
            System.out.println(reminder.getTaskId());
            Reminder checkReminder = reminderRepository.findByTaskId(reminder.getTaskId());
            System.out.println("hello");
            LocalDateTime lt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            if(checkReminder != null){
                System.out.println("**************************");
                System.out.println(checkReminder.getDateTime()+"\n"+lt);
                if(lt.equals(checkReminder.getDateTime()) ){
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setFrom("vamsikaturu008@gmail.com");
                    mailMessage.setTo(email);
                    mailMessage.setText(reminder.getTitle()+"\n"+reminder.getDescription());
                    mailMessage.setSubject("Reminder Alert");
                    javaMailSender.send(mailMessage);
                    
                    reminder.setStatus("completed");
                    reminderRepository.save(reminder);
                    System.out.println("update the reminder");

                    System.out.println("shutdown");
                    executor.shutdown();
                }
                else{
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setFrom("vamsikaturu008@gmail.com");
                    mailMessage.setTo(email);
                    mailMessage.setText(reminder.getTitle()+"\n"+reminder.getDescription());
                    mailMessage.setSubject("Reminder Alert");
                    javaMailSender.send(mailMessage);
                }
            }
            else{
                System.out.println("shutdown");
                executor.shutdown();
            }
        };

        executor.scheduleAtFixedRate(task, 500, time, TimeUnit.MILLISECONDS);
        Instant triggerTime = scheduledDateTime.atZone(ZoneId.systemDefault()).toInstant();
        scheduler.schedule(task, triggerTime);
    }

    

    @GetMapping("/viewtaskpage")
    public String viewTask(@RequestParam String id, Model m) {
        List<Reminder> reminders = reminderRepository.findByUserId(Integer.parseInt(id));
        List<Category> categories = categoryRepository.findByUserId(Integer.parseInt(id));
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
    public String searchTask(@RequestParam String value, @RequestParam String id, Model m) {
        List<Reminder> reminder = reminderRepository.findByCategoryCategoryName(value);
        m.addAttribute("reminder", reminder);
        m.addAttribute("id", id);
        return "view-task-page";
    }
    

    @PostMapping("updateTask")
    public String updateTask(Reminder reminder){

        Reminder updateReminder = reminderRepository.findByTaskId(reminder.getTaskId());
        Category category = categoryRepository.findByCategoryName(reminder.getCategory().getCategoryName());

        updateReminder.setTitle(reminder.getTitle());
        updateReminder.setDateTime(reminder.getDateTime());
        updateReminder.setDescription(reminder.getDescription());
        updateReminder.setCategory(category);
        reminderRepository.save(updateReminder);
        String[] repeat = reminder.getRepeat().split(" ");
        sendReminder(updateReminder, updateReminder.getUser().getEmail(), updateReminder.getDateTime(), repeat);
        return "redirect:/viewtaskpage?id="+reminder.getUser().getId();
    }


    @RequestMapping("deleteTask")
    public String deleteTask(@RequestParam("taskId") int taskId, @RequestParam("id") String id){
        System.out.println("delete task");
        Reminder reminder = reminderRepository.findByTaskId(taskId);
        reminder.setUser(null);
        reminder.setCategory(null);
        reminderRepository.delete(reminder);
        return "redirect:/viewtaskpage?id="+id;
    }
}
