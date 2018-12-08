package org.lily.micourse.config.security

import io.jsonwebtoken.*
import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.entity.user.User
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
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Author: J.D. Liao
 * Date: 2018/12/6
 * Description:
 */

val logger = LoggerFactory.getLogger("JwtAuthenticationEntryPoint")!!

class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
        logger.error("Authentication failed, message is $e")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}

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
        val header: String = request.getHeader("Authorization")
        val token = jwtTokenProvider.getJwtFromRequestHeader(header)!!

        if (jwtTokenProvider.validateToken(token)) {
            // set authentication information only if the token is valid

            try {
                val registrationName = jwtTokenProvider.getNameFromToken(token)
                val userDetails = userPrincipalService.loadUserByUsername(registrationName)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication

            } catch (e: Exception) {
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }

}

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret:JwtSecretKey}")
    private lateinit var _secret: String

    @Value("\${jwt.expiration:1800}")
    private var _expiration: Int? = null

    @Value("\${jwt.prefix:Bearer}")
    private lateinit var _prefix: String

    fun getJwtFromRequestHeader(bearerToken: String): String? {
        if (bearerToken.isNotBlank() && bearerToken.startsWith(_prefix))
            return bearerToken.substring(_prefix.length).trim()

        return null
    }

    fun getNameFromToken(token: String): String =
        Jwts.parser().setSigningKey(_secret).parseClaimsJws(token).body.subject

    fun generateToken(authentication: Authentication): String {
        val idUserDetails = authentication.principal as IdUserDetails

        val expirationDate = Date(Date().time + _expiration!! * 1000)

        return Jwts.builder()
            .setSubject(idUserDetails.id.toString())
            .setIssuedAt(Date())
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, _secret)
            .compact()
    }

    fun validateToken(token: String): Boolean {

        try {
            Jwts.parser().setSigningKey(_secret).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature");
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token");
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token");
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token");
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.");
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

        return IdUserDetails(user)
    }
}

class IdUserDetails(user: User) : UserDetails {

    val id = user.id

    val registrationEmail = user.registerEmail

    private val pwd = user.password

    private val banned = user.banned

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // No authorities in MiCourse user
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return id.toString()
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
        return banned
    }

    override fun toString(): String {
        return "IdUserDetails(id=$id, registrationEmail='$registrationEmail', pwd='$pwd', banned=$banned)"
    }


}