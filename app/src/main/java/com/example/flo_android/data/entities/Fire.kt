package com.example.flo_android.data.entities

data class Fire(
    var fireKey: String,
    var fireTitle: String,
    var fireSinger: String
){
    constructor(): this("", "", "")
}