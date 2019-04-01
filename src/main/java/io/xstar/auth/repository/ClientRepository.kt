package io.xstar.auth.repository

import io.xstar.auth.po.Client
import java.util.*

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
interface ClientRepository : CustomRepository<Client, Long> {
  fun findByClientIdAlias(clientIdAlias: String): Optional<Client>
}