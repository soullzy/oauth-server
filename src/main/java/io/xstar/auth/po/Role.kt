package io.xstar.auth.po

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import java.io.Serializable
import javax.persistence.*

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Entity
@Table(name = "roles")
@NamedEntityGraph(name = "Role.resources", attributeNodes = [NamedAttributeNode("resources")])
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column(unique = true, nullable = false, length = 20)
    val name: String = "",

    @Column(length = 500)
    val description: String? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    val createdBy: Long = 0,

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    @JsonIgnore
    var version: Int = 0,

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = [CascadeType.REFRESH])
    @JsonIgnore
    val users: Set<User>? = emptySet(),

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.REFRESH])
    @JoinTable(
        name = "roles_has_resources",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "resource_id")])
    var resources: Set<Resource>? = emptySet()
) : Serializable {
  companion object {
    private const val serialVersionUID = -5193344128221526323L
  }

  @PreRemove
  private fun removeRolesFromUsers() {
    users!!.forEach { user -> user.roles.minus(this) }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    if (other == null || javaClass != other.javaClass) return false

    val role = other as Role?

    return EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(id, role!!.id)
        .append(createdAt, role.createdAt)
        .append(createdBy, role.createdBy)
        .append(lastModifiedAt, role.lastModifiedAt)
        .append(lastModifiedBy, role.lastModifiedBy)
        .append(version, role.version)
        .append(name, role.name)
        .append(description, role.description)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(name)
        .append(description)
        .append(createdAt)
        .append(createdBy)
        .append(lastModifiedAt)
        .append(lastModifiedBy)
        .append(version)
        .toHashCode()
  }

  override fun toString(): String =
      StringBuilder("Role(")
          .append("id = ").append(id)
          .append(", name = ").append(name)
          .append(", description = ").append(description)
          .append(", createdAt = ").append(createdAt)
          .append(", createdBy = ").append(createdBy)
          .append(", lastModifiedAt = ").append(lastModifiedAt)
          .append(", lastModifiedBy = ").append(lastModifiedBy)
          .append(", version = ").append(version)
          .append(")")
          .toString()
}