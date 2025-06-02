package com.example.flo_android.ui.main.fire

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_android.R
import com.example.flo_android.data.local.FireDao
import com.example.flo_android.data.entities.Fire
import com.example.flo_android.databinding.ActivityFireListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class FireListActivity  : AppCompatActivity() {

    lateinit var binding: ActivityFireListBinding
    lateinit var dao: FireDao
    lateinit var adapter: FireAdapter
    lateinit var fireList: ArrayList<Fire>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFireListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fireList 초기화
        fireList = ArrayList()

        // dao 초기화
        dao = FireDao()

        // adapter 초기화
        adapter = FireAdapter(this, fireList)

        // recyclerView 초기화
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // 노래 정보 가져오기
        getFireList()

        // 노래 삭제 기능
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // 해당 위치 값 변수에 담기
                val position = viewHolder.bindingAdapterPosition

                when(direction){

                    ItemTouchHelper.LEFT ->{
                        val key = fireList[position].fireKey

                        dao.fireDelete(key).addOnSuccessListener { // 성공 이벤트
                            Toast.makeText(this@FireListActivity, "삭제 성공",
                                Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { // 삭제 이벤트
                            Toast.makeText(this@FireListActivity, "삭제 실패: ${it.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }//when
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                // 스와이프 꾸미기
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("삭제")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .create()
                    .decorate()


                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun getFireList(){

        dao.getFireList()?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // 리스트 초기화
                fireList.clear()

                for(dataSnapshot in snapshot.children){

                    val fire = dataSnapshot.getValue(Fire::class.java)

                    // 키값 가져오기
                    val key = dataSnapshot.key

                    // 노래 정보에 키 값 담기
                    fire?.fireKey = key.toString()

                    // 리스트에 담기
                    if(fire != null){
                        fireList.add(fire)
                    }
                }

                // 데이터 적용
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}