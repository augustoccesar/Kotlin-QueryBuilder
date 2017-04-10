package br.com.augustoccesar.querybuilder.models

import br.com.augustoccesar.querybuilder.exceptions.InvalidPatternException

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
class Table(var name: String?, var alias: String? = null){
    fun toSql(): String {
        return "$name $alias"
    }

    companion object {
        /**
         *
         * Method for translating the markdown to [Table] model.
         *
         * ### Pattern
         *
         * *users{u}* will produce *users u*
         *
         * @property nameMarkdown Name of the table using the markdown for table names.
         *
         * @return [Table] class representation of the markdown string.
         */
        @JvmStatic
        fun fromMarkdown(nameMarkdown: String): Table {
            val regex = Regex("(\\w+)(\\{(\\w+)})")
            val match = regex.matchEntire(nameMarkdown)

            if (match != null) {
                return Table(match.groups[1]?.value, match.groups[3]?.value)
            } else {
                throw InvalidPatternException("Table")
            }
        }

        /**
         * TODO Documentation
         */
        @JvmStatic
        fun fromMultipleMarkdown(vararg tablesMarkdown: String): MutableList<Table> {
            val tables: MutableList<Table> = mutableListOf()
            tablesMarkdown.mapTo(tables) { Table.fromMarkdown(it) }
            return tables
        }
    }
}