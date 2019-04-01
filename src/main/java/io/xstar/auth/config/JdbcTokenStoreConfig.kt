package io.xstar.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource


/**
 * JDBC TOKEN配置信息
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Configuration
@ConditionalOnProperty(prefix = "security.oauth2", name = ["storeType"], havingValue = "jdbc")
class JdbcTokenStoreConfig(private val dataSource: DataSource) {

  @Bean
  fun tokenStore(): TokenStore {
    return JdbcTokenStore(dataSource)
  }
}