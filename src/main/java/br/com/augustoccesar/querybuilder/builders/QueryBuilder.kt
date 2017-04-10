package br.com.augustoccesar.querybuilder.builders

import br.com.augustoccesar.querybuilder.helpers.MetaKey
import br.com.augustoccesar.querybuilder.trackers.FromTracker
import br.com.augustoccesar.querybuilder.trackers.SelectTracker
import br.com.augustoccesar.querybuilder.transformers.QueryBuilderTransformer

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
class QueryBuilder(val alias: String? = null): Builder {
    private val _selectTracker: SelectTracker = SelectTracker()
    private val _fromTracker: FromTracker = FromTracker()

    fun select(vararg fieldsMarked: String): QueryBuilder {
        this._selectTracker.addFields(*fieldsMarked)
        return this
    }

    fun select(vararg queryBuildersAsField: QueryBuilder): QueryBuilder {
        this._selectTracker.addQueryBuilders(*queryBuildersAsField)
        return this
    }

    fun from(vararg tablesMarked: String): QueryBuilder {
        this._fromTracker.addTables(*tablesMarked)
        return this
    }

    fun from(vararg queryBuildersAsTable: QueryBuilder): QueryBuilder {
        this._fromTracker.addQueryBuilders(*queryBuildersAsTable)
        return this
    }

    // Overrides

    override fun build(withAlias: Boolean): String {
        return QueryBuilderTransformer(this).build(withAlias)
    }

    override fun meta(): HashMap<MetaKey, Any> {
        return hashMapOf(
                MetaKey.SELECT_TRACKER to this._selectTracker,
                MetaKey.FROM_TRACKER to this._fromTracker
        )
    }
}