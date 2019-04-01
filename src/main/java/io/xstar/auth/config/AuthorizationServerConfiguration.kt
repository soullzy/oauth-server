package io.xstar.auth.config

import io.xstar.auth.config.custom.CustomClientDetailsService
import io.xstar.auth.config.custom.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(
    private val authenticationManager: AuthenticationManager,
    private val tokenStore: TokenStore,
    private val clientDetailsService: CustomClientDetailsService,
    private val userDetailsService: CustomUserDetailsService
) : AuthorizationServerConfigurerAdapter() {
  @Autowired(required = false)
  lateinit var jwtAccessTokenConverter: JwtAccessTokenConverter

  @Autowired(required = false)
  lateinit var jwtTokenEnhancer: TokenEnhancer

  override fun configure(clients: ClientDetailsServiceConfigurer) {
    clients.withClientDetails(clientDetailsService)
  }

  override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
    endpoints.tokenStore(tokenStore)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
    //兼容jwt
    if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
      val tokenEnhancerChain = TokenEnhancerChain()
      val enhancerList = arrayListOf<TokenEnhancer>()
      enhancerList.add(jwtTokenEnhancer)
      enhancerList.add(jwtAccessTokenConverter)
      tokenEnhancerChain.setTokenEnhancers(enhancerList)
      endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter)
    }
  }

  override fun configure(security: AuthorizationServerSecurityConfigurer?) {
    security?.allowFormAuthenticationForClients()
        ?.tokenKeyAccess("Authorization")
        ?.checkTokenAccess("permitAll()");
  }
}