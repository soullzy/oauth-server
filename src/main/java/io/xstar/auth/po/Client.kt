package io.xstar.auth.po

import io.xstar.constant.CommonConstant
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails
import javax.persistence.*

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Entity
@Table(name = "clients")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    val id: Long = 0,

    @Column(length = 50, columnDefinition = "varchar(50) default ''")
    val name: String = "",

    @Column(length = 50, columnDefinition = "varchar(50) default ''")
    val clientIdAlias: String = "",

    @Column(length = 100, columnDefinition = "varchar(100) default ''")
    val resourceIdStr: String = "",

    @Column(length = 100, columnDefinition = "varchar(100) default ''")
    val clientSecretAlias: String = "",

    /**
     * Available values: read, write
     */
    @Column(length = 100, columnDefinition = "varchar(100) default ''")
    val scopeStr: String = "",

    /**
     * grant types include "authorization_code", "password", "assertion", and "refresh_token". Default
     * description is "authorization_code,refresh_token".
     */
    @Column(length = 100, columnDefinition = "varchar(100) default ''")
    val authorizedGrantTypeStr: String = "",

    /**
     * The redirect URI(s) established during registration (optional, comma separated).
     */
    @Column(length = 1024)
    val registeredRedirectUriStr: String? = "",

    /**
     * Authorities that are granted to the client (comma-separated). Distinct from the authorities
     * granted to the user on behalf of whom the client is acting.
     * <pre>
     * For example: USER
    </pre> *
     */
    @Column(length = 500, columnDefinition = "varchar(500) default ''")
    val authoritiesStr: String = "",

    /**
     * The access token validity period in seconds (optional). If unspecified a global default will be
     * applied by the token services.
     */
    @Column(nullable = false, columnDefinition = "INT default 1800")
    val accessTokenValiditySecondsAlias: Int = 1800,

    /**
     * The refresh token validity period in seconds (optional). If unspecified a global default will
     * be applied by the token services.
     */
    @Column(nullable = false, columnDefinition = "INT default 3600")
    val refreshTokenValiditySecondsAlias: Int = 3600,

    /**
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be
     * useful, for example, for storing descriptive information.
     */
    val additionalInformationStr: String? = "",

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    val createdBy: Long = 0,

    @Column(nullable = false)
    var lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    var lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    var version: Int = 0
) : ClientDetails {
  companion object {
    private const val serialVersionUID = 6500601540965188191L
  }

  override fun getClientId(): String {
    return clientIdAlias
  }

  override fun getResourceIds(): Set<String> {
    return str2Set(resourceIdStr)
  }

  override fun isSecretRequired(): Boolean {
    return true
  }

  override fun getClientSecret(): String {
    return clientSecretAlias
  }

  override fun isScoped(): Boolean {
    return true
  }

  override fun getScope(): Set<String> {
    return str2Set(scopeStr)
  }

  override fun getAuthorizedGrantTypes(): Set<String> {
    return str2Set(authorizedGrantTypeStr)
  }

  override fun getRegisteredRedirectUri(): Set<String> {
    return if (StringUtils.isBlank(registeredRedirectUriStr)) {
      emptySet()
    } else {
      str2Set(registeredRedirectUriStr!!)
    }
  }

  override fun getAuthorities(): Collection<GrantedAuthority> {
    return authorizedGrantTypeStr.split(CommonConstant.COMMA)
        .map { SimpleGrantedAuthority(it) }
  }

  override fun getAccessTokenValiditySeconds(): Int? {
    return accessTokenValiditySecondsAlias
  }

  override fun getRefreshTokenValiditySeconds(): Int? {
    return refreshTokenValiditySecondsAlias
  }

  override fun isAutoApprove(scope: String): Boolean {
    return false
  }

  override fun getAdditionalInformation(): Map<String, Any>? {
    return null
  }

  private fun str2Set(str: String): Set<String> {
    return if (StringUtils.isBlank(str)) emptySet() else str.split(CommonConstant.COMMA).toHashSet()
  }
}