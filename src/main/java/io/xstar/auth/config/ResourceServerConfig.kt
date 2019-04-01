package io.xstar.auth.config

import io.xstar.constant.CommonConstant
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

  override fun configure(resources: ResourceServerSecurityConfigurer) {
    resources.resourceId(CommonConstant.API).stateless(true);
  }

  override fun configure(http: HttpSecurity) {
    http
        .csrf().disable()
        .exceptionHandling()
        .and()
        .authorizeRequests().antMatchers("/oauth/**").permitAll()
        .and()
        .authorizeRequests().antMatchers("/api/**").authenticated()
  }
}