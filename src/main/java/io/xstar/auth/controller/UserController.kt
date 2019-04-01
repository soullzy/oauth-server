package io.xstar.auth.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import io.jsonwebtoken.Jwts
import io.xstar.constant.JwtConstant
import org.apache.commons.lang3.StringUtils
import sun.misc.BASE64Encoder


/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@RestController
@RequestMapping("/api")
class UserController {

  @GetMapping("/user")
  fun getUser(): Authentication {
    return SecurityContextHolder.getContext().authentication
  }

  @GetMapping("/my")
  fun my(user: Authentication, request: HttpServletRequest) {
    val userName = user.principal
    val name = user.name
    val header = request.getHeader("Authorization")
    val token = StringUtils.substringAfter(header, "bearer ")
    val body = Jwts.parser().setSigningKey(BASE64Encoder().encode(JwtConstant.SIGN_KEY.toByteArray()))
        .parseClaimsJws(token).body

    println("解析token获取到的username为${body.get("user_name")}")
    println("从Authentication里获取到的username为$userName")
    println("从Authentication里获取到的username为$name")
    println(body)
  }

  @GetMapping("/test")
  fun test() {
    println("oauth test")
  }
}