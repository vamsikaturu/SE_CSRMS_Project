package com.pack.seproject.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.pack.seproject.model.Reminder;
import com.pack.seproject.repository.ReminderRepository;

@Service
public class ReminderService {

    @Autowired
    ReminderRepository reminderRepository;

     @Autowired
    private TaskScheduler scheduler;

    @Autowired
	JavaMailSender javaMailSender;

    public Reminder saveReminder(Reminder task) {
        return reminderRepository.save(task);
    }

    public Reminder findByTaskId(int taskId) {
        return reminderRepository.findByTaskId(taskId);
    }

    public List<Reminder> findByUserId(int id) {
        return reminderRepository.findByUserId(id);
    }

    public List<Reminder> getCategories(String value) {
        return reminderRepository.findByCategoryCategoryName(value);
    }

    public List<Reminder> searchTask(String value, String date) {

        List<Reminder> reminders ;
        

        if(value.compareTo("null") != 0){
            reminders = reminderRepository.findByCategoryCategoryName(value);
        }
        else{
            reminders = reminderRepository.findAll();
        }

        List<Reminder> reminders2 = new ArrayList<>(reminders);

        System.out.println(date);

        if(date.compareTo("null") != 0){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);

            for (Reminder reminder : reminders2) {
                System.out.println(localDate.compareTo(reminder.getDateTime().toLocalDate()));
                if(localDate.compareTo(reminder.getDateTime().toLocalDate()) != 0){
                    reminders.remove(reminder);
                }
            }
        }

        
        return reminders;
    }


    public Reminder updateReminder(Reminder updateReminder, LocalDateTime dateTime) {
        return reminderRepository.save(updateReminder);
    }

    public void deleteReminder(int taskId) {
        Reminder reminder = reminderRepository.findByTaskId(taskId);
        reminder.setUser(null);
        reminder.setCategory(null);
        reminderRepository.delete(reminder);
    }
    
    public void sendReminder(Reminder reminder, String email, LocalDateTime localDateTime, String[] repeat){
        System.out.println("sendreminder");
        long time = 0;
        if(repeat[1].equals("min")){
            time = Integer.parseInt(repeat[0])*60*1000;
        }
        else if(repeat[1].equals("hr")){
            time = Integer.parseInt(repeat[0])*60*60*1000;
        }
        else if(repeat[1].equals("day")){
            time = Integer.parseInt(repeat[0])*24*60*60*1000;
        }else{
            time = 0;
        }

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        LocalDateTime scheduledDateTime = localDateTime;


        Runnable task = () ->{
            System.out.println("********************************************************");
            System.out.println("Task ID: "+reminder.getTaskId());
            System.out.println("Initial task time: "+scheduledDateTime);

            Reminder checkReminder = reminderRepository.findByTaskId(reminder.getTaskId());

            LocalDateTime lt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            if(checkReminder != null){
                System.out.println("********************************************************");

                System.out.println(checkReminder.getDateTime()+"\n"+lt);

                if(lt.equals(checkReminder.getDateTime()) ){
                    SimpleMailMessage mailMessage = new SimpleMailMessage();

                    mailMessage.setFrom("vamsikaturu008@gmail.com");
                    mailMessage.setTo(email);
                    mailMessage.setText(reminder.getTitle()+"\n"+reminder.getDescription());
                    mailMessage.setSubject("Reminder Alert");
                    javaMailSender.send(mailMessage);
                    // updating the reminder status
                    reminder.setStatus("completed");
                    reminderRepository.save(reminder);
                    // shutdown repeate 
                    executor.shutdown();

                }
                else if(scheduledDateTime.equals(checkReminder.getDateTime())){
                        SimpleMailMessage mailMessage = new SimpleMailMessage();
                        mailMessage.setFrom("vamsikaturu008@gmail.com");
                        mailMessage.setTo(email);
                        mailMessage.setText(reminder.getTitle()+"\n"+reminder.getDescription());
                        mailMessage.setSubject("Reminder Alert");
                        javaMailSender.send(mailMessage);

                        System.out.println("Description: "+ reminder.getDescription());
                        System.out.println("update reminder : "+checkReminder.getIsUpdate());
                }
                else{
                    System.out.println("shutdown the inital task");
                    executor.shutdown();
                }
            }
            else{
                System.out.println("shutdown");
                executor.shutdown();
            }
        };

        if(time != 0){
            executor.scheduleAtFixedRate(task, 100, time, TimeUnit.MILLISECONDS);
        }
        Instant triggerTime = scheduledDateTime.atZone(ZoneId.systemDefault()).toInstant();

        scheduler.schedule(task, triggerTime);
    }
    
}
