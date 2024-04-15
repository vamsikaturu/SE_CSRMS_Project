package com.pack.seproject.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pack.seproject.model.Reminder;
import com.pack.seproject.model.User;
import com.pack.seproject.repository.ReminderRepository;
import com.pack.seproject.repository.UserRespository;

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
    private TaskScheduler scheduler;

    @Autowired
	JavaMailSender javaMailSender;

    @PostMapping("/addTask")
    public String addTask(Reminder reminder, Model m){
        User user = userRespository.findByUsername(reminder.getUser().getUsername());
        Reminder task = new Reminder(reminder.getTitle(), reminder.getDescription(), reminder.getDateTime(), user, reminder.getStatus());
        reminderRepository.save(task);

        sendEmail(task, user.getEmail(), reminder.getDateTime());

        return "redirect:/userhome";
    }

    public void sendEmail(Reminder reminder, String email, LocalDateTime localDateTime) {

        LocalDateTime scheduledDateTime = localDateTime;

        Runnable task = () ->{
            
            Reminder checkReminder = reminderRepository.findByTaskId(reminder.getTaskId());
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
                }
                else{
                    System.out.println("Mail not Sent");
                }
            }
            else{
                System.out.println("task deleted");
            }
        };

        Instant triggerTime = scheduledDateTime.atZone(ZoneId.systemDefault()).toInstant();
        scheduler.schedule(task, triggerTime);

    }

    @PostMapping("updateTask")
    public String updateTask(Reminder reminder){

        Reminder updateReminder = reminderRepository.findByTaskId(reminder.getTaskId());

        updateReminder.setTitle(reminder.getTitle());
        updateReminder.setDateTime(reminder.getDateTime());
        updateReminder.setDescription(reminder.getDescription());
        reminderRepository.save(updateReminder);

        sendEmail(updateReminder, updateReminder.getUser().getEmail(), updateReminder.getDateTime());
        return "redirect:/userhome";
    }


    @RequestMapping("deleteTask")
    public String deleteTask(@RequestParam("taskId") int taskId){
        System.out.println("delete task");
        Reminder reminder = reminderRepository.findByTaskId(taskId);
        reminder.setUser(null);
        reminderRepository.delete(reminder);
        return "redirect:/userhome";
    }
}
