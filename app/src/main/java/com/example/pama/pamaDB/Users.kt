package com.example.pama.pamaDB



class Users {


    var id : Int = 0
    var username: String
    var password: String


    constructor(username: String, password: String, string: Any?) {
        this.username = username.toString()
        this.password = password

    }


}