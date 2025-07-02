package com.example.flo_android.data.local

import com.example.flo_android.data.entities.Fire
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class FireDao {
    private var databaseReference: DatabaseReference? = null

    init{
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("fire")
    }

    // 등록
    fun add(fire: Fire?): Task<Void>{
        return databaseReference!!.push().setValue(fire)
    }

    // 조회
    fun getFireList(): Query?{
        return databaseReference
    }

    // 삭제
    fun fireDelete(key: String): Task<Void>{
        return databaseReference!!.child(key).removeValue()
    }
}