package com.pack.seproject.model;



import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reminder")
public class Reminder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int taskId;

    String title;

    String description;

    String status;

    LocalDateTime dateTime;

    String repeatReminder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryid")
    Category category;

    public Reminder() {
    }

    public Reminder(String title, String description, LocalDateTime dateTime, String repeatReminder, User user, Category category, String status) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.user = user;
        this.repeatReminder = repeatReminder;
        this.category = category;
        this.status = status;
    }

    

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reminder [title=" + title + ", description=" + description + ", date=" + dateTime +"]";
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getRepeat() {
        return repeatReminder;
    }

    public void setRepeat(String repeatReminder) {
        this.repeatReminder = repeatReminder;
    }

    

}
