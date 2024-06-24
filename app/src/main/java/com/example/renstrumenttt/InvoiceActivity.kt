package com.example.renstrumenttt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.renstrumenttt.databinding.ActivityInvoiceBinding

class InvoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var instrumentName: String
    private lateinit var imageUrl: String
    private var days: Int = 0
    private var totalPrice: Double = 0.0
    private lateinit var paymentMethod: String
    private lateinit var startDate: String
    private lateinit var endDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        instrumentName = intent.getStringExtra("instrumentName") ?: ""
        imageUrl = intent.getStringExtra("imageUrl") ?: ""
        days = intent.getIntExtra("days", 0)
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0)
        paymentMethod = intent.getStringExtra("paymentMethod") ?: ""
        startDate = intent.getStringExtra("startDate") ?: ""
        endDate = intent.getStringExtra("endDate") ?: ""

        // Load invoice HTML into WebView
        val invoiceHtml = generateInvoiceHtml()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadDataWithBaseURL(null, invoiceHtml, "text/html", "UTF-8", null)

        // Set print button action
        binding.buttonPrint.setOnClickListener {
            createWebPrintJob(binding.webView)
        }
    }

    private fun generateInvoiceHtml(): String {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 16px; }
                    .invoice-container { width: 100%; max-width: 600px; margin: auto; }
                    .header { text-align: center; }
                    .content { margin-top: 16px; }
                    .item { margin-bottom: 16px; }
                    .item img { width: 100%; max-width: 200px; }
                    .item-details { margin-top: 8px; }
                    .total { font-size: 1.2em; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="invoice-container">
                    <div class="header">
                        <h1>Struk Penyewaan</h1>
                        <p>Tanggal: ${startDate.split(" ")[0]}</p>
                    </div>
                    <div class="content">
                        <div class="item">
                            <img src="$imageUrl" alt="Instrument">
                            <div class="item-details">
                                <p>Nama Alat Musik: $instrumentName</p>
                                <p>Durasi Sewa: $days hari</p>
                                <p>Tanggal Mulai: $startDate</p>
                                <p>Tanggal Selesai: $endDate</p>
                                <p>Metode Pembayaran: $paymentMethod</p>
                                <p class="total">Harga Total: $$totalPrice</p>
                            </div>
                        </div>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    private fun createWebPrintJob(webView: WebView) {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter: PrintDocumentAdapter = webView.createPrintDocumentAdapter("Struk Penyewaan")
        val jobName = "${getString(R.string.app_name)} Document"
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }
}
