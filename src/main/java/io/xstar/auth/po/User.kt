package io.xstar.auth.po

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Entity
@Table(name = "users", uniqueConstraints = [(UniqueConstraint(columnNames = ["usr"]))])
@NamedEntityGraph(name = "User.roles", attributeNodes = [NamedAttributeNode("roles")])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column(nullable = false, length = 255)
    val usr: String = "",

    @Column(nullable = false, length = 200)
    val pwd: String = "",

    @Column(length = 50)
    val nickname: String? = "",

    @Column(nullable = false)
    @JsonIgnore
    val isAccountNonExpiredAlias: Boolean = true,

    @Column(nullable = false)
    @JsonIgnore
    val isAccountNonLockedAlias: Boolean = true,

    @Column(nullable = false)
    @JsonIgnore
    val isCredentialsNonExpiredAlias: Boolean = true,

    @Column(nullable = false)
    @JsonIgnore
    val isEnabledAlias: Boolean = true,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Version
    @Column(nullable = false)
    @JsonIgnore
    var version: Int = 0,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.REFRESH])
    @JoinTable(
        name = "users_has_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    @JsonIgnore
    var roles: Set<Role> = emptySet()

    /*多账号登录*/
    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    val accounts: Set<Account> = emptySet()*/
) : UserDetails {
  companion object {
    private const val serialVersionUID = 6298843159549723556L
  }

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    val authorities = mutableListOf<GrantedAuthority>()
    roles
        .forEach {
          it.resources!!
              .forEach { authorities.add(SimpleGrantedAuthority(it.authority)) }
        }
    return authorities
  }

  override fun getUsername(): String {
    return usr
  }

  override fun getPassword(): String {
    return pwd
  }

  override fun isEnabled(): Boolean {
    return isEnabledAlias
  }

  override fun isCredentialsNonExpired(): Boolean {
    return isCredentialsNonExpiredAlias
  }

  override fun isAccountNonExpired(): Boolean {
    return isAccountNonExpiredAlias
  }

  override fun isAccountNonLocked(): Boolean {
    return isAccountNonLockedAlias
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    if (other == null || javaClass != other.javaClass) return false

    val user = other as User?

    return EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(id, user!!.id)
        .append(isAccountNonExpiredAlias, user.isAccountNonExpiredAlias)
        .append(isAccountNonLockedAlias, user.isAccountNonLockedAlias)
        .append(isCredentialsNonExpiredAlias, user.isCredentialsNonExpiredAlias)
        .append(isEnabledAlias, user.isEnabledAlias)
        .append(createdAt, user.createdAt)
        .append(version, user.version)
        .append(nickname, user.nickname)
        .append(usr, user.usr)
        .append(pwd, user.pwd)
        .append(description, user.description)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(nickname)
        .append(usr)
        .append(pwd)
        .append(isAccountNonExpiredAlias)
        .append(isAccountNonLockedAlias)
        .append(isCredentialsNonExpiredAlias)
        .append(isEnabledAlias)
        .append(description)
        .append(createdAt)
        .append(version)
        .toHashCode()
  }

  override fun toString(): String =
      StringBuilder("User(")
          .append("id = ").append(id)
          .append(", nickname = ").append(nickname)
          .append(", usr = ").append(usr)
          .append(", pwd = ").append(pwd)
          .append(", description = ").append(description)
          .append(", isAccountNonExpiredAlias = ").append(isAccountNonExpiredAlias)
          .append(", isAccountNonLockedAlias = ").append(isAccountNonLockedAlias)
          .append(", isCredentialsNonExpiredAlias = ").append(isCredentialsNonExpiredAlias)
          .append(", isCredentialsNonExpiredAlias = ").append(isCredentialsNonExpiredAlias)
          .append(", isEnabledAlias = ").append(isEnabledAlias)
          .append(", createdAt = ").append(createdAt)
          .append(", version = ").append(version)
          .append(")")
          .toString()
}