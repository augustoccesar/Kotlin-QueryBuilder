package br.com.augustoccesar.querybuilder.models

import br.com.augustoccesar.querybuilder.exceptions.InvalidPatternException
import br.com.augustoccesar.querybuilder.helpers.BuilderConstants
import java.lang.StringBuilder

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
class Field(val name: String?,
            val tableAlias: String?,
            var alias: String? = null,
            var distinct: Boolean = false) {

    private var sqlField: String = tableAlias + "." + name

    init {
        alias = alias ?: tableAlias + "_" + name
    }

    fun toSql(withAlias: Boolean = true): String {
        val result: StringBuilder = StringBuilder()

        if (this.distinct) {
            result.append(BuilderConstants.DISTINCT.repr)
        }

        if (withAlias) {
            result.append(this.sqlField).append(BuilderConstants.AS.repr).append(this.alias)
        } else {
            result.append(this.sqlField)
        }

        return result.toString()

    }

    companion object {
        /**
         *
         * Method for translating the markdown to [Field] model.
         *
         * ### Pattern
         *
         * *{u}name* will produce *u.name AS u_name*
         * *{u}name{user_name}* will produce *u.name AS user_name*
         * *{u}\*\*name* will produce *DISTINCT u.name AS u_name*
         *
         * @property nameMarkdown Name of the table using the markdown for table names.
         *
         * @return [Field] class representation of the markdown string.
         */
        @JvmStatic
        fun fromMarkdown(nameMarkdown: String): Field {
            val regex = Regex("(\\{(\\w+)})\\*{0,2}(\\w+)(\\{(\\w+)})?")
            val match = regex.matchEntire(nameMarkdown)

            if (match != null) {
                val name = match.groups[3]?.value
                val tableAlias = match.groups[2]?.value
                val alias = match.groups[5]?.value
                var distinct = false

                if (nameMarkdown.contains("**")) {
                    distinct = true
                }

                return Field(name, tableAlias = tableAlias, alias = alias, distinct = distinct)
            } else {
                throw InvalidPatternException("Field")
            }
        }

        /**
         * TODO Documentation
         */
        @JvmStatic
        fun fromMultipleMarkdown(vararg namesMarkdown: String): MutableList<Field> {
            val fields: MutableList<Field> = mutableListOf()
            namesMarkdown.mapTo(fields) { Field.fromMarkdown(it) }
            return fields
        }
    }
}