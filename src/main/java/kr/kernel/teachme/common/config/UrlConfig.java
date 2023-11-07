package kr.kernel.teachme.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:url.properties")
public class UrlConfig {
}
