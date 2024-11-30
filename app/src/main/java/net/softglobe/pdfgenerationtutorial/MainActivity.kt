package net.softglobe.pdfgenerationtutorial

import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import net.softglobe.pdfgenerationtutorial.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnCreateTextPdf.setOnClickListener {
            createTextPDF()
        }

        binding.btnCreateViewPdf.setOnClickListener {
            createViewPDF()
        }

    }

    private fun createViewPDF() {
        val screenWidth : Int
        val screenHeight : Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            screenWidth = windowManager.currentWindowMetrics.bounds.width()
            screenHeight = windowManager.currentWindowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels
        }

        val view = LayoutInflater.from(this).inflate(R.layout.invoice_pdf, null)

        view.measure(
            View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY)
        )

        view.layout(0, 0, screenWidth, screenHeight)

        val pdfDocument = PdfDocument()
        val pageInfo =  PageInfo.Builder(screenWidth, screenHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        view.draw(page.canvas)
        pdfDocument.finishPage(page)

        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyViewPDF.pdf")
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
    }

    private fun createTextPDF() {
        val screenWidth : Int
        val screenHeight : Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            screenWidth = windowManager.currentWindowMetrics.bounds.width()
            screenHeight = windowManager.currentWindowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels
        }

        val pdfDocument = PdfDocument()
        val pageInfo =  PageInfo.Builder(screenWidth, screenHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val paint = Paint()
        paint.textSize = 50F
        paint.color = Color.GREEN

        page.canvas.drawText("Hello World", screenWidth/2F-100, screenHeight/2F, paint)

        pdfDocument.finishPage(page)

        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyTextPDF.pdf")
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
    }



}