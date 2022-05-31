package com.mokha.compshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekNoHP: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNama = new_nama.getText().toString()
                cekAlamat = new_alamat.getText().toString()
                cekNoHP = new_nohp.getText().toString()

                if (isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekNoHP!!)) {
                    Toast.makeText(this@UpdateData,
                        "Data tidak boleh kosong",
                        Toast.LENGTH_SHORT).show()
                } else {
                    val setdata_teman = data_comp()
                    setdata_teman.nama = new_nama.getText().toString()
                    setdata_teman.alamat = new_alamat.getText().toString()
                    setdata_teman.no_hp = new_nohp.getText().toString()
                    updateTeman(setdata_teman)
                }
            }
        })
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    private val data: Unit
        private get() {
            val getNama = intent.extras!!.getString("dataNama")
            val getAlamat = intent.extras!!.getString("dataAlamat")
            val getNoHP = intent.extras!!.getString("dataNoHP")

            new_nama!!.setText(getNama)
            new_alamat!!.setText(getAlamat)
            new_nohp!!.setText(getNoHP)
        }

    private fun updateTeman(teman: data_comp) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("DataComputer")
            .child(getKey!!)
            .setValue(teman)
            .addOnSuccessListener {
                new_nama!!.setText("")
                new_alamat!!.setText("")
                new_nohp!!.setText("")
                Toast.makeText(this@UpdateData, "Data berhasil diubah",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}