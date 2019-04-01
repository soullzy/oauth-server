package io.xstar.auth.config.custom

import io.xstar.auth.repository.ClientRepository
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.stereotype.Service

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Service
class CustomClientDetailsService(private val clientRepository: ClientRepository) : ClientDetailsService {

  @Throws(ClientRegistrationException::class)
  override fun loadClientByClientId(clientId: String): ClientDetails {
    return clientRepository.findByClientIdAlias(clientId)
        .orElseThrow { throw ClientRegistrationException(String.format("client %s does not exist!", clientId)) }
  }
}