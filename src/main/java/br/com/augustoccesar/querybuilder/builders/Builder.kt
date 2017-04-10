package br.com.augustoccesar.querybuilder.builders

import br.com.augustoccesar.querybuilder.helpers.MetaKey

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
interface Builder {
    fun build(withAlias: Boolean = false): String
    fun meta(): HashMap<MetaKey, Any>
}