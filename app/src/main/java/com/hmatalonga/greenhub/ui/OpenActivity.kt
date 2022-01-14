package com.hmatalonga.greenhub.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.app.sharejourny.Utils.UserPrefs
import com.hmatalonga.greenhub.R

class OpenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open2)

        Handler().postDelayed({

            if(UserPrefs(this@OpenActivity).isLogin){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, Login::class.java))
            }

            finish()

        }, 1500)


    }
}