package com.yieldigit.authapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@SpringBootApplication
public class AuthappApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(AuthappApplication.class, args);
	}

	@Bean
	PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		encoder.encode("password");
		auth.inMemoryAuthentication()
			.withUser("user").password(encoder.encode("password")).roles("USER")
			.and()
				.withUser("admin").password(encoder.encode("password")).roles("ADMIN");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeHttpRequests()
					.antMatchers("/home").permitAll()
					.antMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated().and().formLogin();
	}
}
