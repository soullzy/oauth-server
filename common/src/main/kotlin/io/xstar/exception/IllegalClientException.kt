package io.xstar.exception

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
class IllegalClientException(
    override val message: String? = "Illegal client.",
    val code: String? = "SYS001"
) : RuntimeException()