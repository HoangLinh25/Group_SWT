package edu.fu.controller;

import edu.fu.dto.JobRequest;
import edu.fu.entities.Job;
import edu.fu.services.JobService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

public class JobControllerSimulator {

    public static void main(String[] args) {

        JobRequest jobRequest = new JobRequest();

        jobRequest.setTitle("Fresher Java 2026");

        jobRequest.setDescription("""
                We are looking for a Fresher Java Developer
                to build scalable microservices
                for an EdTech platform.
                The candidate will work with Spring Boot,
                PostgreSQL, Docker,
                and Kubernetes in an Agile environment.
                """);

        jobRequest.setLocation("Ha Noi, Viet Nam");
        jobRequest.setMinSalary(25000000.0);
        jobRequest.setMaxSalary(45000000.0);

        jobRequest.setUtmSource("linkedin");
        jobRequest.setUtmMedium("social-media");
        jobRequest.setDeadline(
                Instant.parse("2026-06-30T23:59:59Z")
        );

        // Khởi tạo Spring Context
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Lấy bean theo class tránh lỗi tên bean
        JobService jobService =
                context.getBean(JobService.class);

        // Tạo job
        Job result = jobService.createJob(jobRequest);

        // In kết quả
        System.out.println("===== JOB CREATED =====");
        System.out.println(result);
    }

    // Config Spring ngay trong file
    @Configuration
    @ComponentScan(basePackages = "edu.fu")
    static class AppConfig {

    }
}