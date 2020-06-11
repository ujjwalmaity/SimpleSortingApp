package dev.ujjwal.simplesortingapp.model

class UserDetail(
    var id: String? = null,
    var name: String? = null,
    var img: String? = null,
    var gender: String? = null,
    var age: Int? = null,
    var date: String? = null,
    var status: String? = null
)

class UserList {
    var list: List<UserDetail>? = null
}
