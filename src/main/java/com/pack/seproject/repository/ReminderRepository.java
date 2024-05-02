package com.pack.seproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pack.seproject.model.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

    List<Reminder> findByUserId(int id);

    Reminder findByTaskId(int taskId);
    
    void deleteByTaskId(int taskId);

    List<Reminder> findByCategoryCategoryName(String value);
    
}
