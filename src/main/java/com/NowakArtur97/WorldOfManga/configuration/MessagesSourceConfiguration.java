package com.NowakArtur97.WorldOfManga.configuration;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessagesSourceConfiguration {

	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/validation/messages");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {

		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource);

		return validatorFactoryBean;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.US);
		
		return sessionLocaleResolver;
	}
}
