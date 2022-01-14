package com.hmatalonga.greenhub.ui

//import com.google.firebase.auth.AuthResult
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.hmatalonga.greenhub.R
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*


class Signup : AppCompatActivity() {


//    private lateinit var mAuth: FirebaseAuth
    var firebase_db: FirebaseFirestore? = null

    lateinit var progressDialoge: ProgressDialog




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

//        mAuth = FirebaseAuth.getInstance();
        firebase_db = FirebaseFirestore.getInstance()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }


        btnSignup.setOnClickListener{
            signup()
        }


    }

    fun signup(){


        progressDialoge = ProgressDialog(this@Signup);
        progressDialoge.setMessage("Please Wait");
        progressDialoge.setTitle("Loading");
        progressDialoge.setCancelable(false);


        var name = et_name.text.toString()
        var email = et_email.text.toString()
        var pass = et_pass.text.toString()
        var cpass = et_cpass.text.toString()

        if(name.isEmpty()){
            Toast.makeText(this@Signup, "Enter your name", Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty() || !isValidEmail(email)){
            Toast.makeText(this@Signup, "Enter your correct email", Toast.LENGTH_SHORT).show();
        }else if(pass.isEmpty()){
            Toast.makeText(this@Signup, "Enter your password", Toast.LENGTH_SHORT).show();
        }else if(cpass.isEmpty()){
            Toast.makeText(this@Signup, "Enter your confirm password", Toast.LENGTH_SHORT).show();
        }else if(!pass.equals(cpass)){
            Toast.makeText(this@Signup, "Password not matched.", Toast.LENGTH_SHORT).show();
        }else {

            val user: MutableMap<String, Any> = HashMap()
            user["name"] = "" + name
            user["email"] = "" + email
            user["pass"] = "" + pass
            user["id"] = "" + getRandPassword(20)

            progressDialoge.show();
            try {

               var collectionRef = firebase_db?.collection("users")!!.document(email)

                collectionRef.get().addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot?> {
                    override fun onComplete(task: Task<DocumentSnapshot?>) {
                        if (task.isSuccessful()) {
                            val document: DocumentSnapshot = task.getResult()!!
                            if (!document.exists()) {

                                firebase_db?.collection("users")?.document("" + email)
                                        ?.set(user)?.addOnSuccessListener(OnSuccessListener<Void?> {
                                            Toast.makeText(applicationContext, "You are now connected with Battery Optimizer please login again", Toast.LENGTH_LONG).show()
                                            finish()
                                        })
                                        ?.addOnFailureListener(OnFailureListener {
                                            Toast.makeText(applicationContext, "Registeration Failed", Toast.LENGTH_LONG).show()
                                        })


                            } else {
                                Toast.makeText(applicationContext, "This user already exist", Toast.LENGTH_LONG).show()
                            }

                            progressDialoge.hide();

                        } else {
                            Toast.makeText(applicationContext, "Registeration Failed", Toast.LENGTH_LONG).show()

                            progressDialoge.hide();

                        }
                    }
                })




            } catch (e: Exception) {
                Log.i("=====", "onClick: " + e.message)
                Toast.makeText(applicationContext, "Registeration Failed", Toast.LENGTH_LONG).show()
                progressDialoge.hide();

            }


//            mAuth.createUserWithEmailAndPassword(email, pass)
//                    .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?> {
//                        override fun onComplete(task: Task<AuthResult?>) {
//
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                val user: FirebaseUser = mAuth.getCurrentUser()!!
//
//
//                            } else {
//                                Toast.makeText(this@Signup, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show()
//                            }
//
//                        }
//
//                    })

        }
    }


    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun getRandPassword(n: Int): String
    {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val random = Random(System.nanoTime())
        val password = StringBuilder()

        for (i in 0 until n)
        {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }
}