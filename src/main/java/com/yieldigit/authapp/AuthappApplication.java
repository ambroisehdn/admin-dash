package com.yieldigit.authapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.yieldigit.authapp.services.user.UserService;


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

	@Autowired 
	UserService userService;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userService);

		return daoAuthenticationProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
				.authorizeHttpRequests()
				// .antMatchers("/").permitAll()

				// .anyRequest().permitAll();

			// .antMatchers("/home").permitAll()
			// .antMatchers("/addrole").permitAll()
			// .antMatchers("/adduser").permitAll()
				// .antMatchers("/admin/home").hasRole("ADMIN");
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				// .failureHandler(
				// 		new AuthenticationFailureHandler() {
						
				
				// 		@Override
				// 		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				// 				AuthenticationException exception)
				// 				throws IOException, ServletException {
				// 				String redirectUrl = request.getContextPath() + "/login?error";
				// 				response.sendRedirect(redirectUrl);							
				// 		}	
				// 	}
				// )
				;
	}
}
