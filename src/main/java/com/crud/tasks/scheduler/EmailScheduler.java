package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled (cron = "0 0 10 * * *")
//    @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail(){
        long size = taskRepository.count();
        if (size < 1) {
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),SETCC,
                SUBJECT, "Current in database you got: " + size + " task")
        );
        }
        else {
            simpleEmailService.send(new Mail(
                    adminConfig.getAdminMail(),SETCC,
                    SUBJECT, "Current in database you got: " + size + " tasks")
            ); }
    }

    private static final String SUBJECT = "Tasks: Once a day email";
    private static final String SETCC = "";


}
