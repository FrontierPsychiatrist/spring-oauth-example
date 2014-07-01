package de.frontierpsychiatrist.example.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Configuration
public class ConvertersConfiguration {

    /**
     * Converter for the format yyyy-MM-dd HH:mm:ss
     *
     * Currently needed for the approval revoke form that needs to bind the expiresAt and lastUpdatedAt
     * dates of an approval.
     */
    @Bean
    public Converter<String, Date> stringDateConverter() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // We can not use a lambda here since Spring can't detect the generic types that way.
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (source == null) {
                    throw new IllegalArgumentException("Date string may not be null");
                }
                try {
                    return sdf.parse(source);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

}
