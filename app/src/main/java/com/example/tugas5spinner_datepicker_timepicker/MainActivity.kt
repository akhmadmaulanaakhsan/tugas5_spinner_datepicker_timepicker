package com.example.tugas5spinner_datepicker_timepicker

import android.R
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.tugas5spinner_datepicker_timepicker.databinding.ActivityMainBinding
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val presensi = arrayOf(
            "Hadir Tepat Waktu",
            "Sakit",
            "Terlambat",
            "Izin"
        )
        with(binding) {
            val adapterPresensi = ArrayAdapter<String>(
                this@MainActivity,
                R.layout.simple_spinner_item, presensi
            )
            spinnerPresensi.adapter = adapterPresensi

            // Sembunyikan EditText awalnya
            keterangan.visibility = View.GONE

            spinnerPresensi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Tampilkan EditText jika pilihan bukan "Hadir Tepat Waktu"
                    if (position != 0) {
                        keterangan.visibility = View.VISIBLE
                    } else {
                        keterangan.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // Handle when nothing is selected (optional)
                }
            }

            var selectedDate: String = ""


            btnSubmit.setOnClickListener {
                // Ambil tanggal dari CalendarView
                datePicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val months = arrayOf(
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "Agustus",
                        "September",
                        "Oktober",
                        "November",
                        "Desember"
                    )
                    selectedDate = "$dayOfMonth ${months[month]} $year"
                }

                // Ambil waktu dari TimePicker
                val selectedTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    .format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, timePickerView.hour)
                        set(Calendar.MINUTE, timePickerView.minute)
                    }.time)

                // Tampilkan pesan ke Toast
                val presensiMessage = "Presensi berhasil $selectedDate jam $selectedTime"

                Toast.makeText(
                    this@MainActivity,
                    presensiMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }
}