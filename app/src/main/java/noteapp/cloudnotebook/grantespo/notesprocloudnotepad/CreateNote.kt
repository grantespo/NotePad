package noteapp.cloudnotebook.grantespo.notesprocloudnotepad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_create_note.*
import java.util.*
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
/*import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.parse.*/


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//class CreateNote : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
class CreateNote : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
   // private var userFirstLogin = true
    private var themeName:String = "Sky"

    private lateinit var titleString: String
    private lateinit var bodyString: String
    private lateinit var noteType: String
    private lateinit var dateString: String
    private lateinit var noteId: String

    //private lateinit var mGoogleApiClient: GoogleApiClient
    //private lateinit var mFirebaseAuth: FirebaseAuth

    //private val permissionCode = 9999



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeSet()
        setContentView(R.layout.activity_create_note)

        //configureGoogleAPIClient()
        initializeUI()
        headerFooterListeners()


    }

    /*private fun configureGoogleAPIClient(){

        val options:GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build()

        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, " " + p0.errorMessage, Toast.LENGTH_LONG).show()
    }*/

    /*private fun signIn(){

        mGoogleApiClient.connect()
        if(ParseUser.getCurrentUser() == null){
           // Log.i("TrackFirstSave", "signIn(): firstSave:$firstSave")
            val intent:Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            //intent.putExtra("firstSave", firstSave)
            startActivityForResult(intent, permissionCode)
        }
        else{
            //Save To Parse
        }

    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //TODO FIGURE OUT WHY firstSAVE returns FALSE HERE, THEN IMPLEMENT NOTES BACkUP TO PARSE
        val extra=data?.extras
        val firstSave = extra!!.getBoolean("firstSave")
        Log.i("TrackFirstSave", "onActivityResult(): firstSave:$firstSave")

        if (requestCode == permissionCode) {

            val result:GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                val account:GoogleSignInAccount? = result.signInAccount
                val idToken:String? = account!!.idToken

                val credential:AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuthWithGoogle(credential)
            }
            else{
                Toast.makeText(this, "Login UnSuccessful", Toast.LENGTH_SHORT).show()
                createNoteProgBar.visibility = View.GONE
                createNoteProgBar2.visibility = View.GONE
                if(firstSave){
                    editor.putBoolean("firstSave", false)
                    editor.commit()
                    val i = Intent(this@CreateNote, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            }
        }
        else{
            Toast.makeText(this, "Login UnSuccessful", Toast.LENGTH_SHORT).show()
            createNoteProgBar.visibility = View.GONE
            createNoteProgBar2.visibility = View.GONE
            if(firstSave){
                editor.putBoolean("firstSave", false)
                editor.commit()
                val i = Intent(this@CreateNote, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

        }

    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential) {
        mFirebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener { authResult ->
                    //User is now authenticated with Firebase via Google Sign In
                    val userEmail = authResult.user.email
                    val userId = authResult.user.uid

                    Log.i("GOOGLE_SIGN_INBRAH", "userEmail: $userEmail")
                    Log.i("GOOGLE_SIGN_INBRAH", "userId: $userId")

                    //Login/Signup user to Parse-Server
                    //Log.i("TrackFirstSave", "firebaseAuthWithGoogle: firstSave:$firstSave")
                    configParseUser(userId, userEmail!!)

                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: " +e.message, Toast.LENGTH_LONG).show()
                    createNoteProgBar.visibility = View.GONE
                    createNoteProgBar2.visibility = View.GONE
                   /* if(firstSave){
                        editor.putBoolean("firstSave", false)
                        editor.commit()
                        val i = Intent(this@CreateNote, MainActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)
                    }*/

                }
    }

    private fun configParseUser(userId: String, userEmail: String){
        //Log.i("TrackFirstSave", "configParseUser: firstSave:$firstSave")

        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereEqualTo("username", userId)
        query.findInBackground { objects, e ->
            if (e == null) {

                if(objects!!.size > 0){
                    //Login
                    ParseUser.logInInBackground(userId, userId) { _, e1 ->

                        if(e1 == null){
                            //Success
                            editor.putBoolean("firstSave", false)
                            editor.commit()
                            Toast.makeText(this@CreateNote, "Login Success", Toast.LENGTH_LONG).show()
                            createNoteProgBar.visibility = View.GONE
                            createNoteProgBar2.visibility = View.GONE
                            createNoteLoginButton.visibility = View.GONE
                            createNoteLogoutButton.visibility = View.VISIBLE
                            /*if(firstSave){
                                val i = Intent(this@CreateNote, MainActivity::class.java)
                                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(i)
                            }*/


                        } else{
                            Toast.makeText(this@CreateNote, "Error: Unable to log you in.  " +e1.message, Toast.LENGTH_LONG).show()
                            createNoteProgBar.visibility = View.GONE
                            createNoteProgBar2.visibility = View.GONE
                           /* if(firstSave){
                                editor.putBoolean("firstSave", false)
                                editor.commit()
                                val i = Intent(this@CreateNote, MainActivity::class.java)
                                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(i)
                            }*/
                        }

                    }
                }
                else{
                    //Sign Up
                    val newUser1 = ParseUser()
                    newUser1.username = userId
                    newUser1.setPassword(userId)
                    newUser1.email = userEmail
                    newUser1.signUpInBackground { e1 ->

                        if(e1 == null){
                            //Success
                            editor.putBoolean("firstSave", false)
                            editor.commit()
                            Toast.makeText(this@CreateNote, "Sign Up Success", Toast.LENGTH_LONG).show()
                            createNoteProgBar.visibility = View.GONE
                            createNoteProgBar2.visibility = View.GONE
                            createNoteLoginButton.visibility = View.GONE
                            createNoteLogoutButton.visibility = View.VISIBLE
                           /* if(firstSave){
                                val i = Intent(this@CreateNote, MainActivity::class.java)
                                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(i)
                            }*/
                        }
                        else{
                            Toast.makeText(this@CreateNote, "Error: Unable to Sign you up.  " +e1.message, Toast.LENGTH_LONG).show()
                            createNoteProgBar.visibility = View.GONE
                            createNoteProgBar2.visibility = View.GONE
                           /* if(firstSave){
                                editor.putBoolean("firstSave", false)
                                editor.commit()
                                val i = Intent(this@CreateNote, MainActivity::class.java)
                                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(i)
                            }*/
                        }

                    }

                }

            } else {
                Toast.makeText(this@CreateNote, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                createNoteProgBar.visibility = View.GONE
                createNoteProgBar2.visibility = View.GONE
                /*if(firstSave){
                    editor.putBoolean("firstSave", false)
                    editor.commit()
                    val i = Intent(this@CreateNote, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }*/
            }
        }

    }*/

    /*private fun saveNote(user:ParseUser){
        val newNote = ParseObject("Notes")
        newNote.put("user", user)
        newNote.put("title", createNoteTitle.text.toString())
        newNote.put("body", createNoteBody.text.toString())
        newNote.saveInBackground()

        val intent1 = Intent(this@CreateNote, MainActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent1)
    }*/

    /*private fun updateNote(user:ParseUser){
        val query:ParseQuery<ParseObject> = ParseQuery.getQuery("Notes")
        //query.whereEqualTo("objectId", objectId)


        val newNote = ParseObject("Notes")
        newNote.put("user", user)
        newNote.put("title", createNoteTitle.text.toString())
        newNote.put("body", createNoteBody.text.toString())
        newNote.saveInBackground()

        val intent1 = Intent(this@CreateNote, MainActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent1)
    }*/

    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    private fun initializeUI() {

        //userFirstLogin = pref.getBoolean("firstSave", true)

        val intent = intent
        noteType = intent.getStringExtra("type")
        if (noteType == "new") {
            createNoteDelete.visibility = View.GONE
            createNoteDeleteLayout.visibility = View.GONE
        } else {
            titleString = intent.getStringExtra("title")
            bodyString = intent.getStringExtra("body")
            dateString = intent.getStringExtra("date")
            noteId = intent.getStringExtra("id")
            createNoteTitle.setText(titleString)
            createNoteBody.setText(bodyString)
            createNoteDateText.text = "Last Updated $dateString"
            hideKeyboard()
            createNoteBody.requestFocus()
        }

       /* if (ParseUser.getCurrentUser() != null) {
            createNoteProgBar.visibility = View.GONE
            createNoteLogoutButton.visibility = View.VISIBLE
            createNoteLoginButton.visibility = View.GONE
        } else {*/
            createNoteProgBar.visibility = View.GONE
            createNoteLogoutButton.visibility = View.GONE
            createNoteLoginButton.visibility = View.VISIBLE
       // }

    }

    private fun headerFooterListeners() {

        createNoteMainBar.setOnClickListener {
            //Hides Keyboard When Header Bar is clicked
            val inputMethodManager = getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            if(inputMethodManager.isAcceptingText){
                inputMethodManager.hideSoftInputFromWindow(
                        Objects.requireNonNull(currentFocus).windowToken, 0)
            }
        }

        createNoteTitle2.setOnClickListener {
            if (createNoteTitle.text.toString() == "") {
                Toast.makeText(this@CreateNote, "Title Cannot Be Empty.", Toast.LENGTH_LONG).show()
            } else if (createNoteBody.text.toString() == "") {
                Toast.makeText(this@CreateNote, "Body Cannot Be Empty.", Toast.LENGTH_LONG).show()
            } else {
                if (noteType == "new") {
                    addRow()
                } else {
                    updateRow()
                }
            }
        }

        createNoteTitle2.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    createNoteTitle2.background = getDrawable(R.drawable.roundedcornerpressed)
                    createNoteTitle2.setTextColor(Color.parseColor("#2261b4"))
                    createNoteTitle2.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_UP -> {
                    createNoteTitle2.background = getDrawable(R.drawable.roundedcorner)
                    createNoteTitle2.setTextColor(Color.WHITE)
                    createNoteTitle2.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_CANCEL -> {
                    createNoteTitle2.background = getDrawable(R.drawable.roundedcorner)
                    createNoteTitle2.setTextColor(Color.WHITE)
                    createNoteTitle2.setPadding(15, 15, 15, 15)
                }
            }


            false
        }

        createNoteBack.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    createNoteBack.background = getDrawable(R.drawable.roundedcornerpressed)
                    createNoteBack.setTextColor(Color.parseColor("#2261b4"))
                    createNoteBack.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_UP -> {
                    createNoteBack.background = getDrawable(R.drawable.roundedcorner)
                    createNoteBack.setTextColor(Color.WHITE)
                    createNoteBack.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_CANCEL -> {
                    createNoteBack.background = getDrawable(R.drawable.roundedcorner)
                    createNoteBack.setTextColor(Color.WHITE)
                    createNoteBack.setPadding(15, 15, 15, 15)
                }
            }


            false
        }

        createNoteBack.setOnClickListener {
            onBackPressed()
        }

        createNoteDeleteLayout.setOnClickListener {
            val builder = AlertDialog.Builder(this@CreateNote)

            builder.setTitle("")
            builder.setMessage("Are you sure you want to delete this note?")

            builder.setPositiveButton("YES") { _, _ ->
                try {
                    //MainActivity.noteSQLiteDB.delete("notes", "title='$titleString' and body='$bodyString'", null)
                    MainActivity.noteSQLiteDB.delete("notes", "Id='${Integer.valueOf(noteId)}'", null)
                    Toast.makeText(this@CreateNote, "Note Deleted.", Toast.LENGTH_SHORT).show()
                    MainActivity.noteSQLiteDB.close()

                   // ConnectToParse.showAd()
                    val i = Intent(this@CreateNote, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    ConnectToParse.showAd()

                } catch (e: Exception) {
                    Toast.makeText(this@CreateNote, "Something went wrong. " + e.message, Toast.LENGTH_LONG).show()
                }
            }

            builder.setNegativeButton("NO") { dialog, _ ->

                dialog.dismiss()
            }

            val alert = builder.create()
            alert.show()
        }

        createNoteLoginButton.setOnClickListener {

            Toast.makeText(this@CreateNote, "Backup Notes with Gmail: COMING SOON!", Toast.LENGTH_LONG).show()
            //TODO: IMPLEMENT BELOW
            //createNoteProgBar.visibility = View.VISIBLE
            //signIn()
        }
        createNoteLogoutButton.setOnClickListener {
            createNoteProgBar.visibility = View.VISIBLE
            /*if(ParseUser.getCurrentUser() != null){
                ParseUser.logOutInBackground {e ->

                    if(e == null){
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                            Toast.makeText(this@CreateNote, "Logout Success", Toast.LENGTH_SHORT).show()
                            createNoteProgBar.visibility = View.GONE
                            createNoteLogoutButton.visibility = View.GONE
                            createNoteLoginButton.visibility = View.VISIBLE
                        }
                    }
                    else{
                        Toast.makeText(this@CreateNote, "Logout Failed", Toast.LENGTH_SHORT).show()
                        createNoteProgBar.visibility = View.GONE
                    }
                }
            }
            else{*/
                /*Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                    Toast.makeText(this@CreateNote, "Logout Success", Toast.LENGTH_SHORT).show()
                    createNoteProgBar.visibility = View.GONE
                    createNoteLogoutButton.visibility = View.GONE
                    createNoteLoginButton.visibility = View.VISIBLE
                }*/
            //}
        }

        createNoteTerms.setOnClickListener {

            val uri = Uri.parse("https://github.com/grantespo/SkyNote/blob/master/skynoteterms.pdf") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }

        createNoteprivacy.setOnClickListener {

            val uri = Uri.parse("https://github.com/grantespo/SkyNote/blob/master/skynoteprivacy.pdf") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    private fun addRow() {

        try {
            val currentDateMili = Calendar.getInstance().timeInMillis
            val contentValues = ContentValues()
            contentValues.put("title", createNoteTitle.text.toString())
            contentValues.put("body", createNoteBody.text.toString())
            contentValues.put("updatedAt", currentDateMili)
            contentValues.put("objectId", "NULL")
            //MainActivity.noteSQLiteDB.execSQL("INSERT INTO notes(title, body, updatedAt) VALUES ('" + createNoteTitle.text.toString() + "', '" + createNoteBody.text.toString() + "', '" + currentDateMili + "')")
            MainActivity.noteSQLiteDB.insert("notes", null, contentValues)
            Toast.makeText(applicationContext, "Note Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Something Went Wrong. Try Again. " + e.message, Toast.LENGTH_LONG).show()
            Log.i("LongAddBug", e.message)
        }

        MainActivity.noteSQLiteDB.close()

       /* if(ParseUser.getCurrentUser() != null){

            //TODO: IMPLEMENT BELOW
            //saveNote(ParseUser.getCurrentUser())

        }
        else{*/
            //ConnectToParse.showAd()
            val i = Intent(this@CreateNote, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            ConnectToParse.showAd()
       // }

    }

    private fun updateRow() {

        try {
            val currentDateMili = Calendar.getInstance().timeInMillis
            val contentValues = ContentValues()
            contentValues.put("title", createNoteTitle.text.toString())
            contentValues.put("body", createNoteBody.text.toString())
            contentValues.put("updatedAt", currentDateMili)
            contentValues.put("objectId", "NULL")
            //MainActivity.noteSQLiteDB.update("notes", contentValues, "title='$titleString' and body='$bodyString'", null)
            MainActivity.noteSQLiteDB.update("notes", contentValues, "Id='${Integer.valueOf(noteId)}'", null)
            Toast.makeText(applicationContext, "Note Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Something Went Wrong. Try Again.", Toast.LENGTH_LONG).show()
            Log.i("UPDATEERROR", e.message)
        }

        MainActivity.noteSQLiteDB.close()

        //ConnectToParse.showAd()

        val i = Intent(this@CreateNote, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        ConnectToParse.showAd()

    }

    private fun hideKeyboard() {
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
    }

    @SuppressLint("CommitPrefEdits")
    private fun themeSet(){
        pref = applicationContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        editor = pref.edit()
        themeName = pref.getString("themeName", "Sky")

        when(themeName){
            getString(R.string.sky) ->{
                setTheme(R.style.SkyTheme)
            }
            getString(R.string.jungle) ->{
                setTheme(R.style.JungleTheme)
            }
            getString(R.string.rose) ->{
                setTheme(R.style.RoseTheme)
            }
            getString(R.string.lemon) ->{
                setTheme(R.style.LemonTheme)
            }
            getString(R.string.violet) ->{
                setTheme(R.style.PurpleTheme)
            }
            getString(R.string.tiger) ->{
                setTheme(R.style.TigerTheme)
            }
            getString(R.string.taffy) ->{
                setTheme(R.style.TaffyTheme)
            }
            getString(R.string.lagoon) ->{
                setTheme(R.style.LagoonTheme)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ConnectToParse.showAd()
    }




    /* if(userFirstLogin){

                val builder1 = AlertDialog.Builder(this@CreateNote)
                builder1.setMessage("Would you like to Sign into Google To Backup your Notes?")
                builder1.setCancelable(true)

                builder1.setPositiveButton(
                        "Yes"
                ) { dialog, _ ->
                    dialog.cancel()
                    //TODO LOGIN TO GOOGLE, Save Note TO PARSE, Save Note, Start Activity
                    createNoteProgBar2.visibility = View.VISIBLE
                    signIn(true)

                  }

                builder1.setNegativeButton(
                        "No"
                ) { _, _ ->

                    editor.putBoolean("firstSave", false)
                    editor.commit()
                    val i = Intent(this@CreateNote, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                   }

                val alert11 = builder1.create()
                alert11.show()

            }
            else{
                editor.putBoolean("firstSave", false)
                editor.commit()
                val i = Intent(this@CreateNote, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }*/

}