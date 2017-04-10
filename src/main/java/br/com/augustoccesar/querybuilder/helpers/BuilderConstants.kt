package br.com.augustoccesar.querybuilder.helpers

/**
 * Author: augustoccesar
 * Date: 06/04/17
 */
enum class BuilderConstants(val repr: String) {
    UNDERLINE("_"),
    EQUALS(" = "),
    SPACE(" "),
    COMMA(" , "),
    OPEN_PARENTHESES(" ( "),
    CLOSE_PARENTHESES(" ) "),
    AS(" AS "),
    ON(" ON "),
    FROM(" FROM "),
    LIMIT(" LIMIT "),
    WHERE(" WHERE "),
    OFFSET(" OFFSET "),
    SELECT(" SELECT "),
    GROUP_BY(" GROUP_BY "),
    ORDER_BY(" ORDER BY "),
    DISTINCT(" DISTINCT ")
}