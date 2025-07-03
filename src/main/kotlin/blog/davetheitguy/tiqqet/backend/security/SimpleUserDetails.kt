package blog.davetheitguy.tiqqet.backend.security

import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SimpleUserDetails: UserDetails, CredentialsContainer {
    private var _authorities = mutableListOf<String>()
    private var _password = ""
    private var _username = ""

    fun addAuthority(authority: String) {
        _authorities.add(authority)
    }

    fun addAuthorities(vararg authorities: String) {
        _authorities.addAll(authorities)
    }

    fun setAuthorities(value: List<String>) {
        _authorities = value.toMutableList()
    }

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return _authorities.map{ SimpleGrantedAuthority(it) }
    }

    fun setPassword(value: String) {
        _password = value
    }

    override fun getPassword(): String? {
        return _password
    }

    fun setUsername(value: String) {
        _username = value
    }

    override fun getUsername(): String? {
        return _username
    }

    override fun eraseCredentials() {
        TODO("Not yet implemented")
    }

}