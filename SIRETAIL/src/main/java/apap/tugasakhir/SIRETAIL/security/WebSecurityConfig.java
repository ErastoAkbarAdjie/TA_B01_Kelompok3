package apap.tugasakhir.SIRETAIL.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/api-docs").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config").permitAll()
                .antMatchers("/cabang/delete/**").hasAnyAuthority("Kepala Retail", "Manager Cabang")
                .antMatchers("/cabang/viewAll/request").hasAuthority("Kepala Retail")
                .antMatchers("/cabang/tolak/**").hasAuthority("Kepala Retail")
                .antMatchers("/cabang/setuju/**").hasAuthority("Kepala Retail")
                .antMatchers("/user/add").hasAuthority("Kepala Retail")
                .antMatchers("/cabang/add").hasAnyAuthority("Kepala Retail", "Manager Cabang")
                .antMatchers("/user/update/**").hasAnyAuthority("Kepala Retail", "Manager Cabang")
                .antMatchers("/item/viewAllCoupon/**").hasAnyAuthority("Kepala Retail", "Manager Cabang")
                .antMatchers("/cabang/update/**").hasAnyAuthority("Kepala Retail", "Manager Cabang")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailService;

    //    Jika belum terdapat user pada data base, komen kode di bawah ini

     @Autowired
     public void configAuthentication (AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailService).passwordEncoder(encoder());
     }

    //    Jika belum terdapat user pada data base, nonaktifkan komen di bawah ini

//   @Autowired
//   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//       auth.inMemoryAuthentication()
//               .passwordEncoder(encoder())
//               .withUser("admin").password(encoder().encode("admin")).roles(("ADMIN"));
//   }
}
