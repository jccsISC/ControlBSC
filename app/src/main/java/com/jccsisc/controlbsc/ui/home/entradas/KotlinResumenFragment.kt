package com.jccsisc.controlbsc.ui.home.entradas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jccsisc.controlbsc.R
import com.jccsisc.controlbsc.activities.MainActivity
import com.jccsisc.controlbsc.adapters.ProductosAdapterResumen
import com.jccsisc.controlbsc.model.Detalle
import com.jccsisc.controlbsc.model.Movimiento
import com.jccsisc.controlbsc.model.Producto
import com.jccsisc.controlbsc.utilidades.NodosFirebase
import com.mikelau.views.shimmer.ShimmerRecyclerViewX
import java.text.SimpleDateFormat
import java.util.*

class KotlinResumenFragment : Fragment {
    private var rvEntradasResumen: ShimmerRecyclerViewX? = null
    private var productoArrayList: ArrayList<Producto>? = null
    private var entradasAdapter: ProductosAdapterResumen? = null
    private var txtDate: TextView? = null
    var dateToday: String? = null
    var fechaCalendar: String? = null

    constructor() {}
    constructor(fecha: String?) {
        fechaCalendar = fecha
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_resumen, container, false)
        MainActivity.edtAppBar.visibility = View.INVISIBLE
        txtDate = v.findViewById(R.id.txtDate)
        rvEntradasResumen = v.findViewById(R.id.recyclerViewEntradasResumen)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rvEntradasResumen.setLayoutManager(linearLayoutManager)
        productoArrayList = ArrayList()
        entradasAdapter = ProductosAdapterResumen(productoArrayList, activity, "Entrada")
        rvEntradasResumen.setAdapter(entradasAdapter)
        rvEntradasResumen.showShimmerAdapter()
        NodosFirebase.myRef.orderByChild("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                rvEntradasResumen.hideShimmerAdapter()
                productoArrayList.removeAll(productoArrayList)
                for (snapshot in dataSnapshot.children) {
                    val key = snapshot.child("movimientos").key
                    val producto = Producto(
                            snapshot.child("name").getValue(String::class.java),
                            snapshot.child("unit").getValue(String::class.java),
                            snapshot.child("idKey").getValue(String::class.java))
                    for (movimiento in snapshot.child("movimientos").children) {
                        var movimiento1: Movimiento
                        //                        if(movimiento.child("date").getValue(String.class).equals("19-07-2020")) {
                        Log.e("dato", movimiento.child("date").getValue(String::class.java).toString() + "fecha")
                        movimiento1 = Movimiento(
                                movimiento.child("date").getValue(String::class.java),
                                movimiento.child("type").getValue(String::class.java),
                                movimiento.child("hour").getValue(String::class.java),
                                movimiento.child("destiny").getValue(String::class.java),
                                movimiento.child("status").getValue(String::class.java),
                                movimiento.child("idKey").getValue(String::class.java),
                                movimiento.child("idMovimiento").getValue(String::class.java),
                                movimiento.child("weight").getValue(Double::class.java)!!,
                                movimiento.child("quantity").getValue(Int::class.java)!!)
                        for (detalles in movimiento.child("detalles").children) {
                            val detalle = Detalle(
                                    detalles.child("idKey").getValue(String::class.java),
                                    detalles.child("peso").getValue(Double::class.java)!!)
                            movimiento1.addDetalles(detalle)
                        }
                        producto.addMovimiento(movimiento1)
                        //                        }
                    }
                    txtDate.setText(fechaCalendar)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = Date()
                    dateToday = dateFormat.format(date)
                    for (c in producto.getMovimientos().indices) {
                        if (producto.getMovimientos()[c].getDate() == dateToday) {
                            productoArrayList!!.add(producto)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        return v
    }
}