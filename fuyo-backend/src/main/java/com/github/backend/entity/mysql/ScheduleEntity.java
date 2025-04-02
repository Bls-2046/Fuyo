package com.github.backend.entity.mysql;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 日程安排
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedule")
public class ScheduleEntity {
    public ScheduleEntity() {
        this.id = UUID.randomUUID().toString();
        this.isReminderInClient = false;
        this.isSendWeChatReminder = false;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "openid", nullable = false, length = 64)
    private String openid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "reminder_date_time", nullable = false)
    private LocalDateTime reminderDateTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_reminder_in_client", nullable = false, columnDefinition = "BIT(1) DEFAULT 0")
    private Boolean isReminderInClient;

    @Column(name = "is_send_we_chat_reminder", nullable = false, columnDefinition = "BIT(1) DEFAULT 0")
    private Boolean isSendWeChatReminder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity userEntity;
}
