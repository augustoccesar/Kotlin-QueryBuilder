package br.com.augustoccesar.querybuilder.transformers

import br.com.augustoccesar.querybuilder.builders.QueryBuilder
import br.com.augustoccesar.querybuilder.exceptions.BuilderException
import br.com.augustoccesar.querybuilder.helpers.BuilderConstants
import br.com.augustoccesar.querybuilder.helpers.MetaKey
import br.com.augustoccesar.querybuilder.trackers.FromTracker
import br.com.augustoccesar.querybuilder.trackers.SelectTracker
import java.lang.StringBuilder

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
class QueryBuilderTransformer(private val _queryBuilder: QueryBuilder) {
    fun build(withAlias: Boolean = false): String {
        val stringBuilder: StringBuilder = StringBuilder()

        // Loading trackers

        val selectTracker: SelectTracker = this._queryBuilder.meta()[MetaKey.SELECT_TRACKER] as SelectTracker
        val fromTracker: FromTracker = this._queryBuilder.meta()[MetaKey.FROM_TRACKER] as FromTracker

        // SELECT Clause
        stringBuilder.append(BuilderConstants.SELECT.repr)
        for (field in selectTracker.fields) {
            stringBuilder.append(field.toSql())
            if (field != selectTracker.fields.last()) {
                stringBuilder.append(",")
            }
        }
        for (queryBuilder in selectTracker.queryBuilders) {
            queryBuilder.alias ?: throw BuilderException("Nested QueryBuilder must have an alias.")

            stringBuilder.append(queryBuilder.build(withAlias))
            if (queryBuilder != selectTracker.queryBuilders.last()) {
                stringBuilder.append(BuilderConstants.COMMA.repr)
            }
        }

        // FROM Clause
        if(fromTracker.hasData()) {
            stringBuilder.append(BuilderConstants.FROM.repr)
            for (table in fromTracker.tables){
                stringBuilder.append(table.toSql())
                if(table != fromTracker.tables.last() || fromTracker.hasQueryBuilders()) {
                    stringBuilder.append(BuilderConstants.COMMA.repr)
                }
            }
            for(queryBuilder in fromTracker.queryBuilders){
                queryBuilder.alias ?: throw BuilderException("Nested QueryBuilder must have an alias.")

                stringBuilder.append(queryBuilder.build(true))
                if(queryBuilder != fromTracker.queryBuilders.last()) {
                    stringBuilder.append(BuilderConstants.COMMA.repr)
                }
            }
        }

        // Returning

        if (_queryBuilder.alias != null && withAlias) {
            return (
                    BuilderConstants.OPEN_PARENTHESES.repr +
                            stringBuilder.toString() +
                            BuilderConstants.CLOSE_PARENTHESES.repr +
                            BuilderConstants.AS.repr + _queryBuilder.alias
                    ).replace("\\s+".toRegex(), " ")
        } else {
            return stringBuilder.toString().replace("\\s+".toRegex(), " ")
        }
    }
}