package com.hmatalonga.greenhub.ui

//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuth
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.app.sharejourny.Utils.UserPrefs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.hmatalonga.greenhub.R
import com.hmatalonga.greenhub.fragments.TosFragment
import com.hmatalonga.greenhub.util.LogUtils
import com.hmatalonga.greenhub.util.SettingsUtils
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth

    var firebase_db: FirebaseFirestore? = null

    lateinit var progressDialoge: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        auth = FirebaseAuth.getInstance();

//        et_email.setText("itsshaikhtaha@gmail.com")
//        et_pass.setText("123")

        firebase_db = FirebaseFirestore.getInstance()


        tv_signup.setOnClickListener{
            startActivity(Intent(this@Login, Signup::class.java))
        }

        btnSignIn.setOnClickListener{
            login()
        }


    }

    fun login(){

        progressDialoge = ProgressDialog(this@Login);
        progressDialoge.setMessage("Please Wait");
        progressDialoge.setTitle("Loading");
        progressDialoge.setCancelable(false);


        var email = et_email.text.toString()
        var pass = et_pass.text.toString()

         if (email.isEmpty() || !isValidEmail(email)){
            Toast.makeText(this@Login, "Enter your correct email", Toast.LENGTH_SHORT).show();
        }else if(pass.isEmpty()){
            Toast.makeText(this@Login, "Enter your password", Toast.LENGTH_SHORT).show();
        }else {


             try {

             progressDialoge.show();

             var collectionRef = firebase_db?.collection("users")!!.document(email)

             collectionRef.get().addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot?> {
                 override fun onComplete(task: Task<DocumentSnapshot?>) {
                     if (task.isSuccessful()) {
                         val document: DocumentSnapshot = task.getResult()!!
                         if (document.exists()) {

                             if(document.get("pass")!!.equals(pass)){

                                 // Ensure we don't run this fragment again



                                 SettingsUtils.markTosAccepted(this@Login, true)
                                 UserPrefs(this@Login).isLogin = true
                                 UserPrefs(this@Login).userId = document.get("id").toString()
                                 val i = Intent(applicationContext, MainActivity::class.java)
                                 startActivity(i)
                                 finish()

                             }else {
                                 Toast.makeText(applicationContext, "Incorrect password", Toast.LENGTH_LONG).show()
                                 progressDialoge.hide()
                             }

                         } else {
                             Toast.makeText(applicationContext, "User does not exist.", Toast.LENGTH_LONG).show()
                             progressDialoge.hide()
                         }
                     } else {
                         Toast.makeText(applicationContext, "User does not exist.", Toast.LENGTH_LONG).show()
                         progressDialoge.hide()
                     }
                 }
             })

             }catch (e: Exception){

                 Log.i("====> ", "login: " + e.message)
                 Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                 progressDialoge.hide()
             }


//             auth.signInWithEmailAndPassword(email, pass)
//                     .addOnCompleteListener(this@Login) { task ->
//                         if (task.isSuccessful) {
//                             // Sign in success, update UI with the signed-in user's information
//                             val user = auth.currentUser
//
//                             Log.i("======", "login: " + Gson().toJson(user))
//
//                         } else {
//                             Toast.makeText(this@Login, "Authentication failed " + task.exception,
//                                     Toast.LENGTH_SHORT).show()
//                         }
//                     }

        }
    }


    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}