package com.example.pama.pamaDB

class Business {

    var id : Int = 0
    var businessname: String
    var category: String
    var location: String


    constructor(username: String, category: String, location: String) {
        this.businessname = username
        this.category = category
        this.location = location

    }

}