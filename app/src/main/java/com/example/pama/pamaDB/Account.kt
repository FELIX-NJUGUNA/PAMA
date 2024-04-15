package com.example.pama.pamaDB

class Account {

    var id : Int = 0
    var wallet: String
    var income: Double
    var expense: Double




    constructor(wallet: String, income:Double, expense: Double) {
        this.wallet = wallet
        this.income = income
        this.expense = expense

    }


}