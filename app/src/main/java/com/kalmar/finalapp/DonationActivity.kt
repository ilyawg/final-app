package com.kalmar.finalapp

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cloudipsp.android.*
import com.cloudipsp.android.CardInputView.ConfirmationErrorHandler

private const val MERCHANT_ID = 1396424

class DonationActivity : AppCompatActivity(), Cloudipsp.PayCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)
        val editAmount: EditText = findViewById(R.id.edit_amount)
        val spinnerCcy: Spinner = findViewById(R.id.spinner_ccy)
        val editEmail: EditText = findViewById(R.id.edit_email)
        val editDescription: EditText = findViewById(R.id.edit_description)
        val cardInput: CardInputView = findViewById(R.id.card_input)
        cardInput.isHelpedNeeded = BuildConfig.DEBUG
        val btnPay: Button = findViewById(R.id.btn_pay)
        val webView: CloudipspView = findViewById(R.id.web_view)
        val cloudipsp = Cloudipsp(MERCHANT_ID, webView)
        spinnerCcy.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Currency.values())

        btnPay.setOnClickListener {
            val order = createOrder()
            val card: Card? = cardInput.confirm(object : ConfirmationErrorHandler {
                override fun onCardInputErrorClear(view: CardInputView?, editText: EditText?) {
                }

                override fun onCardInputErrorCatched(
                    view: CardInputView?,
                    editText: EditText?,
                    error: String?
                ) {
                }
            })
            if (card != null) cloudipsp.pay(card, order, this)
            else Log.e("ERR_MSG", "card is null")
        }

        val btnAmount: TextView = findViewById(R.id.btn_amount)
        btnAmount.setOnClickListener {
            editAmount.setText("10")
            editEmail.setText("test@example.com")
            editDescription.setText("test payment")
        }
    }

    override fun onPaidProcessed(receipt: Receipt) {
        Log.e("INFO","onPaidProcessed")
        Toast.makeText(
            this,
            "Paid " + receipt.status.name + "\nPaymentId:" + receipt.paymentId,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPaidFailure(e: Cloudipsp.Exception) {
        Log.e("INFO","onPaidFailure")
        when (e) {
            is Cloudipsp.Exception.Failure -> {
                val failure = e as Cloudipsp.Exception.Failure
                Toast.makeText(
                    this,
                    "Failure\nErrorCode: " + failure.errorCode
                            + "\nMessage: " + failure.message
                            + "\nRequestId: " + failure.requestId,
                    Toast.LENGTH_LONG
                ).show()
            }
            is Cloudipsp.Exception.NetworkSecurity -> Toast.makeText(
                this,
                "Network security error: " + e.message,
                Toast.LENGTH_LONG
            ).show()
            is Cloudipsp.Exception.ServerInternalError -> Toast.makeText(
                this,
                "Internal server error: " + e.message,
                Toast.LENGTH_LONG
            ).show()
            is Cloudipsp.Exception.NetworkAccess -> Toast.makeText(
                this,
                "Network error",
                Toast.LENGTH_LONG
            ).show()
            else -> {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
                Log.e("ERR_MSG", "onPaidFailure: "+ e.message)
            }
        }
        e.printStackTrace()
    }

    private fun createOrder(): Order {
        val editAmount: EditText = findViewById(R.id.edit_amount)
        val amount: Int = editAmount.text.toString().toInt()
        val editEmail: EditText = findViewById(R.id.edit_email)
        val email: String = editEmail.text.toString()
        val editDescription: EditText = findViewById(R.id.edit_description)
        val description: String = editDescription.text.toString()
        val spinnerCcy: Spinner = findViewById(R.id.spinner_ccy)
        val currency: Currency = spinnerCcy.selectedItem as Currency
        return Order(amount, currency, "vb_" + System.currentTimeMillis(), description, email)
    }
}