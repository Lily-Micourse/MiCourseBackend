package org.lily.micourse.config.security

import io.jsonwebtoken.*
import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.exception.InvalidTokenException
import org.lily.micourse.po.user.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Author: J.D. Liao
 * Date: 2018/12/6
 * Description:
 */

private val logger = LoggerFactory.getLogger("JwtAuthenticationEntryPoint")!!


class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
        logger.error("Authentication failed, message is $e")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var userPrincipalService: UserPrincipalService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Get jwt from request
        val header = request.getHeader("Authorization")

        if (header == null) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtTokenProvider.getJwtFromRequestHeader(header)

        if (!jwtTokenProvider.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }


        try {
            val registrationName = jwtTokenProvider.getNameFromToken(token)
            val userDetails = userPrincipalService.loadUserByUsername(registrationName)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication

        } catch (e: Exception) {
            logger.error("Could not set user authentication in security context", e)
            SecurityContextHolder.clearContext()
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }

        filterChain.doFilter(request, response)
    }

}

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret:JwtSecretKey}")
    private lateinit var secret: String

    @Value("\${jwt.expiration:1800}")
    private var expiration: Int? = null

    @Value("\${jwt.prefix:Bearer}")
    private lateinit var prefix: String

    val tokenType: String
        get() = prefix

    fun getJwtFromRequestHeader(bearerToken: String): String {
        if (bearerToken.isNotBlank() && bearerToken.startsWith(prefix))
            return bearerToken.substring(prefix.length).trim()

        // Return empty string instead
        logger.error("Invalid authorization")
        return ""
    }

    fun getNameFromToken(token: String): String =
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject

    fun generateToken(authentication: Authentication): String {
        val idUserDetails = authentication.principal as UserPrincipal

        val expirationDate = Date(Date().time + expiration!! * 1000)

        return Jwts.builder()
            .setSubject(idUserDetails.username)
            .setIssuedAt(Date())
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun validateToken(token: String): Boolean {

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.")
        }
        return false
    }
}

@Service
class UserPrincipalService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(registration: String): UserDetails {
        val user = userRepository.findByRegisterEmail(registration)
            ?: throw UsernameNotFoundException("Registration email $registration not found")

        return UserPrincipal(user)
    }
}

/**
 * User information in authentication record
 * @property id user id
 * @property getUsername actually is user's registered email
 */
class UserPrincipal(user: User) : UserDetails {

    val id = user.id

    private val registrationEmail = user.registerEmail

    private val pwd = user.password

    private val banned = user.banned

    private val verified = user.isRegisterEmailValidated

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // No authorities in MiCourse user
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return registrationEmail
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !banned
    }

    override fun toString(): String {
        return "UserPrincipal(id=$id, registrationEmail='$registrationEmail', pwd='$pwd', banned=$banned)"
    }


}