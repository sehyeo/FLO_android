package com.example.flo_android

data class Fire(
    var fireKey: String,
    var fireTitle: String,
    var fireSinger: String
){
    constructor(): this("", "", "")
}