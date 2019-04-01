package io.xstar.auth.config.custom

import io.xstar.auth.repository.UserRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Service
class CustomAuthenticationProvider(private val userRepository: UserRepository) : AuthenticationProvider {

  override fun authenticate(authentication: Authentication): Authentication {
    val username = authentication.name
    val password = authentication.credentials as String
    val user = userRepository.findByUsr(username).orElseThrow { NoSuchElementException() }
    return UsernamePasswordAuthenticationToken(user, password, user.authorities)
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
  }
}