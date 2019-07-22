package ch.sonect.sdk.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.sonect.sdk.SonectSDK
import ch.sonect.sdk.paymentPlugins.PaymentConfig
import ch.sonect.sdk.paymentPlugins.PaymentPlugin

class SdkWrapperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrapper)


        val builder: SonectSDK.Config.Builder = SonectSDK.Config.Builder()
        val sonectSDK = SonectSDK(
            this,
            builder.enablePaymentPlugin(PaymentPlugin.ADYEN)
                .enablePaymentPlugin(PaymentPlugin.HBL, PaymentConfig(null, "hbl"))
                .enablePaymentPlugin(PaymentPlugin.NEON, PaymentConfig(null, "neon"))
                .enablePaymentPlugin(PaymentPlugin.POSTFINANCE, PaymentConfig(null, "postfinance"))
                .enablePaymentPlugin(PaymentPlugin.SOFORT, PaymentConfig(null, "sofort"))
                .enablePaymentPlugin(PaymentPlugin.BALANCE)
                .build()
        )

        //Old user
        //0041785544213
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoiMjAxOS0wNC0wNFQxMDoxNjowNC45MDRaIiwicGhvbmUiOiIwMDQxNzg1NTQ0MjEzIiwic2NvcGUiOiJsb2dpbiIsInR5cGUiOiJ1c2VyIiwiaWF0IjoxNTU0MzcyOTY0fQ.gspRt3jfSdjx7TsDb4G8cUvZLJgtY8d5RQ3JwoLFPJg

        // New user
        //0041786655213
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoiMjAxOS0wNC0xMFQxMDowMjo1OS4zODZaIiwicGhvbmUiOiIwMDQxNzg2NjU1MjEzIiwic2NvcGUiOiJsb2dpbiIsInR5cGUiOiJ1c2VyIiwiaWF0IjoxNTU0ODkwNTc5fQ.NWUzxx2Dw0WwhpSm3mhYFfBdkFYvfu74jCGJzpOLGmQ

        supportFragmentManager.beginTransaction().replace(R.id.container, sonectSDK.getStartFragment()).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}