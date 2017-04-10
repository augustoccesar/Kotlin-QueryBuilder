# Kotlin-QueryBuilder

Kotlin implementation of [QueryBuilder](https://github.com/augustoccesar/QueryBuilder)

### Select Field Markdown

`"{u}name"` => **generates** => `u.name AS u_name`

`"{u}name{u_user_name}"` => **generates** => `u.name AS u_user_name`

### Select Tables Markdown

`"users{u}"` => **generates** => `users u`

### Brainstorm

```
.select("{u}email", "{u}username", "{up}first_name", "{up}last_name")
.select(Field("email", "u"), Field("username", "u"), Field("first_name", "up"), Field("last_name", "up"))

.from("users{u}")
.from(Table("users", "u"))

.join(Join(Join.INNER_JOIN, "user_profile{up}", "{up}user_id", "{u}id"))
.innerJoin("user_profile{up}", "{up}user_id", "{u}id")

.where("{up}first_name", Comparator.EQUALS, "Augusto")
.where(Condition("{up}first_name", Comparator.EQUALS, "Augusto"))
.where(Condition.eq("{up}first_name", "Augusto"))
.where(Condition.eq("{up}first_name", "Augusto"), Condition.eq("{up}last_name", "César"))
.where(
    Condition.or(
        Condition.eq("{up}first_name", "Augusto"),
        Condition.eq("{up}first_name", "Irineu")
    )
)

._limit(1)

._offset(1)

.order("{up}first_name", Order.ASC)
.order(Order("{up}first_name", Order.ASC))
.order(Order("{up}first_name", Order.ASC), Order("{up}last_name", Order.ASC))
.order("{up}first_name", Order.ASC).order("{up}last_name", Order.ASC)
```