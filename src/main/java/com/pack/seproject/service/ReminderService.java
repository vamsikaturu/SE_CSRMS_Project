package com.pack.seproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.seproject.model.Reminder;
import com.pack.seproject.repository.ReminderRepository;

@Service
public class ReminderService {

    @Autowired
    ReminderRepository reminderRepository;

    public void saveReminder(Reminder task) {
       reminderRepository.save(task);
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

    public void deleteReminder(int taskId) {
        Reminder reminder = reminderRepository.findByTaskId(taskId);
        reminder.setUser(null);
        reminder.setCategory(null);
        reminderRepository.delete(reminder);
    }
    
}
