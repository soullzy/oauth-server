package io.xstar.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@NoRepositoryBean
interface CustomRepository<T, ID> : JpaSpecificationExecutor<T>, JpaRepository<T, ID>