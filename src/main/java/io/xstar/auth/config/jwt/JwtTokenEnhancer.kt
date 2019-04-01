package io.xstar.auth.config.jwt

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
class JwtTokenEnhancer : TokenEnhancer {

  override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication?): OAuth2AccessToken {
    val info = hashMapOf<String, Any>()
    info["blog"] = "http://www.baidu.com"
    val token = accessToken as DefaultOAuth2AccessToken
    token.additionalInformation = info
    return token
  }
}