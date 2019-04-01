package io.xstar.auth.repository

import io.xstar.auth.po.User
import java.util.*

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
interface UserRepository : CustomRepository<User, Long> {
  fun findByUsr(usr: String): Optional<User>
}