package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.security.AuthTokenFilter;
import com.talentpath.budgetmanager.security.JwtAuthEntryPoint;
import com.talentpath.budgetmanager.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthEntryPoint entryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public AuthTokenFilter jwtFilter() {return new AuthTokenFilter();}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(entryPoint)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/api/auth/signin").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/auth/signup").permitAll()

                    .antMatchers(HttpMethod.GET,"/api/userdata","/api/userdata/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/api/userdata").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT,"/api/userdata").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/api/userdata/**").hasRole("ADMIN")
        //my data specific
        .anyRequest().authenticated().and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
