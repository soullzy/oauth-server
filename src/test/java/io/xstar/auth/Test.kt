package io.xstar.auth

import org.springframework.security.crypto.bcrypt.BCrypt

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
fun main(args: Array<String>) {
  val rawPwd = "6LU7u4ro7dF2%Hr"
  println(BCrypt.hashpw(rawPwd, BCrypt.gensalt()))
}