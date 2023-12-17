package daniil.backend.entity

import daniil.backend.annotation.NoArgsConstructor
import daniil.backend.enums.UserRole
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.OffsetDateTime
import java.util.UUID

@NoArgsConstructor
@Entity
@Table(name = "users")
class User(
    @Column
    @GeneratedValue
    @Id
    open var id: UUID? = null,

    @Column(unique = true, nullable = false)
    open val name: String,

    @Column(unique = true, nullable = false)
    open var mail: String,

    @Column(nullable = false)
    private var password: String,

    @Column(nullable = false)
    open var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Enumerated
    @Column(nullable = false)
    open var role: UserRole,

    @Column(nullable = false)
    open var isBlocked: Boolean = false,

    @Column(nullable = false)
    open var isRegistered: Boolean = false,

    @Column
    open var token: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    open var ownChannel: Channel? = null,

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Like::class)
    open var likes: MutableSet<Like>? = mutableSetOf()

): UserDetails {

    constructor(name: String, mail: String, password: String) : this(
        name = name, mail = mail, password = password, createdAt = OffsetDateTime.now(), role = UserRole.USER, id = null
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("ROLE_" + role.name))
    }

    override fun getPassword(): String {
        return password
    }

    open fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', mail='$mail', role=$role, isBlocked=$isBlocked, isRegistered=$isRegistered)"
    }

}
