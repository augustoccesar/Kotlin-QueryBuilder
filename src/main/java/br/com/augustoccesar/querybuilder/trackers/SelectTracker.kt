package br.com.augustoccesar.querybuilder.trackers

import br.com.augustoccesar.querybuilder.builders.QueryBuilder
import br.com.augustoccesar.querybuilder.models.Field

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
class SelectTracker {
    val fields: MutableList<Field> = mutableListOf()
    val queryBuilders: MutableList<QueryBuilder> = mutableListOf()

    fun addFields(vararg field: Field) {
        this.fields.addAll(field)
    }

    fun addFields(vararg fieldsMarked: String) {
        this.fields.addAll(Field.fromMultipleMarkdown(*fieldsMarked))
    }

    fun addQueryBuilders(vararg queryBuilder: QueryBuilder) {
        this.queryBuilders.addAll(queryBuilder)
    }
}