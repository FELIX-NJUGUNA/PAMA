package com.example.pama.pamaDB

class Transactions {

    var title: String
    var category: String
    var wallet: String
    var date: String
    var transactiontype: String
    var amount: Double

    constructor(title: String, category: String, wallet: String, date: String, transactiontype: String, amount: Double ){
        this.title = title
        this.category = category
        this.wallet = wallet
        this.date = date
        this.transactiontype = transactiontype
        this.amount = amount
    }


}