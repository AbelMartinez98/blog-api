package com.abelm.blog_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Base.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {


}
