package com.abelm.blog_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("!no-scheduling")
public class SchedulingConfiguration {
}
