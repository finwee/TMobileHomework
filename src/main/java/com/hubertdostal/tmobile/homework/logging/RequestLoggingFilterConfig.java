package com.hubertdostal.tmobile.homework.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration to init {@link org.springframework.web.filter.CommonsRequestLoggingFilter} to log all incoming requests
 *
 * @author hubert.dostal@gmail.com
 */
@Configuration
public class RequestLoggingFilterConfig {

    /**
     * Initialisation of {@link org.springframework.web.filter.CommonsRequestLoggingFilter} to log requests
     * @return instance of {@link org.springframework.web.filter.CommonsRequestLoggingFilter}
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("ORIGINAL HTTP REQUEST DATA : ");
        return filter;
    }

}
