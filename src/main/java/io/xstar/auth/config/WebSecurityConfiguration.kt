package io.xstar.auth.config

import io.xstar.auth.config.custom.CustomAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级的权限认证
class WebSecurityConfiguration(private val authenticationProvider: CustomAuthenticationProvider) : WebSecurityConfigurerAdapter() {
  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(authenticationProvider())
  }

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }

  @Bean
  fun authenticationProvider(): AuthenticationProvider {
    return authenticationProvider
  }
}