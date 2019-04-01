package io.xstar.auth.config.jwt

import io.xstar.constant.JwtConstant
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * JWT TOKEN配置信息
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Configuration
@ConditionalOnProperty(prefix = "security.oauth2", name = ["storeType"], havingValue = "jwt", matchIfMissing = true)
class JwtTokenConfig {

  @Bean
  fun jwtTokenStore(): TokenStore {
    return JwtTokenStore(jwtAccessTokenConverter())
  }

  @Bean
  fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
    val jwtAccessTokenConverter = JwtAccessTokenConverter()
    jwtAccessTokenConverter.setSigningKey(JwtConstant.SIGN_KEY)
    return jwtAccessTokenConverter
  }

  @Bean
  @ConditionalOnMissingBean(name = ["jwtTokenEnhancer"])
  fun jwtTokenEnhancer(): TokenEnhancer {
    return JwtTokenEnhancer()
  }
}