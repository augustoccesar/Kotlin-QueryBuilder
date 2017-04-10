package br.com.augustoccesar.querybuilder.trackers

import br.com.augustoccesar.querybuilder.builders.QueryBuilder
import br.com.augustoccesar.querybuilder.models.Table

/**
 * Author: augustoccesar
 * Date: 07/04/17
 */
class FromTracker {
    val tables: MutableList<Table> = mutableListOf()
    val queryBuilders: MutableList<QueryBuilder> = mutableListOf()

    fun addTables(vararg tables: Table) {
        this.tables.addAll(tables)
    }

    fun addTables(vararg markedTables: String) {
        this.tables.addAll(Table.fromMultipleMarkdown(*markedTables))
    }

    fun addQueryBuilders(vararg queryBuilders: QueryBuilder) {
        this.queryBuilders.addAll(queryBuilders)
    }

    // Checkers

    fun hasData(): Boolean {
        return hasTables() || hasQueryBuilders()
    }

    fun hasTables(): Boolean {
        return this.tables.size > 0
    }

    fun hasQueryBuilders(): Boolean {
        return this.queryBuilders.size > 0
    }
}