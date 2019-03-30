package noteapp.cloudnotebook.grantespo.notesprocloudnotepad

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
//import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
/*import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.parse.ParseQuery
import com.parse.ParseUser*/
import kotlinx.android.synthetic.main.activity_main.*
import noteapp.cloudnotebook.grantespo.notesprocloudnotepad.ConnectToParse.Companion.showAd
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//class MainActivity : AppCompatActivity(), MainNotesRecyclerview.ItemClickListener, GoogleApiClient.OnConnectionFailedListener {
class MainActivity : AppCompatActivity(), MainNotesRecyclerview.ItemClickListener {


    companion object {
        lateinit var noteSQLiteDB: SQLiteDatabase
    }

    private lateinit var arrayFeedList: ArrayList<HashMap<String, String>>
    private lateinit var feedData: HashMap<String, String>

    //private lateinit var mGoogleApiClient: GoogleApiClient
    //private lateinit var mFirebaseAuth: FirebaseAuth

    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var themeName:String = "Sky"

    //private val permissionCode = 9999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeSet()
        setContentView(R.layout.activity_main)

        mainProgBar.visibility = View.VISIBLE
        //configureGoogleAPIClient()
        touchListeners()
        openSQLite()
    }

    public override fun onResume() {
        super.onResume()
        initializeUI()
    }

   /* private fun configureGoogleAPIClient(){
        val options: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
            val intent:Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(intent, permissionCode)
        }
        else{
            //Save To Parse
        }

    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == permissionCode) {
            val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                val account: GoogleSignInAccount? = result.signInAccount
                val idToken:String? = account!!.idToken

                val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuthWithGoogle(credential)
            }
            else{
                Toast.makeText(this, "Login UnSuccessful", Toast.LENGTH_SHORT).show()
                mainProgBar.visibility = View.GONE
            }
        }
        else{
            Toast.makeText(this, "Login UnSuccessful", Toast.LENGTH_SHORT).show()
            mainProgBar.visibility = View.GONE

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
                    configParseUser(userId, userEmail!!)

                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: " +e.message, Toast.LENGTH_LONG).show()
                    mainProgBar.visibility = View.GONE
                }
    }

    private fun configParseUser(userId: String, userEmail: String){

        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereEqualTo("username", userId)
        query.findInBackground { objects, e ->
            if (e == null) {

                if(objects!!.size > 0){
                    //Login
                    ParseUser.logInInBackground(userId, userId) { _, e1 ->

                        if(e1 == null){
                            //Success
                            Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_LONG).show()
                            mainProgBar.visibility = View.GONE
                            mainLoginButton.visibility = View.GONE
                            mainLogoutButton.visibility = View.VISIBLE


                        } else{
                            Toast.makeText(this@MainActivity, "Error: Unable to log you in.  " +e1.message, Toast.LENGTH_LONG).show()
                            mainProgBar.visibility = View.GONE
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
                            Toast.makeText(this@MainActivity, "Sign Up Success", Toast.LENGTH_LONG).show()
                            mainProgBar.visibility = View.GONE
                            mainLoginButton.visibility = View.GONE
                            mainLogoutButton.visibility = View.VISIBLE
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Error: Unable to Sign you up.  " +e1.message, Toast.LENGTH_LONG).show()
                            mainProgBar.visibility = View.GONE
                        }

                    }

                }

            } else {
                Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                mainProgBar.visibility = View.GONE
            }
        }

    }*/

    private fun initializeUI() {

        val dividerItemDecoration = DividerItemDecoration(mainRecyclerView.context,
                DividerItemDecoration.VERTICAL)
        mainRecyclerView.addItemDecoration(dividerItemDecoration)

      /*  if (ParseUser.getCurrentUser() != null) {
            mainProgBar.visibility = View.GONE
            mainLogoutButton.visibility = View.VISIBLE
            mainLoginButton.visibility = View.GONE
        } else {*/
            mainProgBar.visibility = View.GONE
            mainLogoutButton.visibility = View.GONE
            mainLoginButton.visibility = View.VISIBLE
       // }

    }

    private fun touchListeners() {
        mainTitle2Layout.setOnClickListener {

            //showAd()

            val i = Intent(this@MainActivity, CreateNote::class.java)
            i.putExtra("type", "new")
            startActivity(i)
            showAd()
        }

        mainTitle2Layout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mainTitle2.background = getDrawable(R.drawable.roundedcornerpressed)
                    mainTitle2.setTextColor(Color.parseColor("#2261b4"))
                    mainTitle2.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_UP -> {
                    mainTitle2.background = getDrawable(R.drawable.roundedcorner)
                    mainTitle2.setTextColor(Color.WHITE)
                    mainTitle2.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_CANCEL -> {
                    mainTitle2.background = getDrawable(R.drawable.roundedcorner)
                    mainTitle2.setTextColor(Color.WHITE)
                    mainTitle2.setPadding(15, 15, 15, 15)
                }
            }
            false
        }

        mainThemes.setOnClickListener {
            //showAd()
            val i = Intent(this@MainActivity, ThemeActivity::class.java)
            startActivity(i)
            showAd()
        }

        mainLoginButton.setOnClickListener {

           Toast.makeText(this@MainActivity, "Backup Notes with Gmail: COMING SOON!", Toast.LENGTH_LONG).show()

            //TODO:IMPLEMENT BELOW
            //mainProgBar.visibility = View.VISIBLE
            //signIn()
        }
        mainLogoutButton.setOnClickListener {
            mainProgBar.visibility = View.VISIBLE
          /*  if(ParseUser.getCurrentUser() != null){
                ParseUser.logOutInBackground {e ->

                    if(e == null){
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                            Toast.makeText(this@MainActivity, "Logout Success", Toast.LENGTH_SHORT).show()
                            mainProgBar.visibility = View.GONE
                            mainLogoutButton.visibility = View.GONE
                            mainLoginButton.visibility = View.VISIBLE
                        }
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Logout Failed", Toast.LENGTH_SHORT).show()
                        mainProgBar.visibility = View.GONE
                    }
                }
            }
            else{*/
               /* Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                    Toast.makeText(this@MainActivity, "Logout Success", Toast.LENGTH_SHORT).show()
                    mainProgBar.visibility = View.GONE
                    mainLogoutButton.visibility = View.GONE
                    mainLoginButton.visibility = View.VISIBLE
                }*/
           // }
        }

        mainTerms.setOnClickListener {

            val uri = Uri.parse("https://github.com/grantespo/SkyNote/blob/master/skynoteterms.pdf") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }

        mainprivacy.setOnClickListener {

            val uri = Uri.parse("https://github.com/grantespo/SkyNote/blob/master/skynoteprivacy.pdf") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    @SuppressLint("Recycle")
    private fun openSQLite() {

        noteSQLiteDB = this.openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
        noteSQLiteDB.execSQL("CREATE TABLE IF NOT EXISTS notes(Id INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR, body VARCHAR, updatedAt INTEGER, objectId VARCHAR)")

        val cursor = noteSQLiteDB.rawQuery("SELECT * FROM notes Order By updatedAt DESC", null)
        val noteId = cursor.getColumnIndex("Id")
        val noteTitle = cursor.getColumnIndex("title")
        val noteBody = cursor.getColumnIndex("body")
        val noteUpdatedAt = cursor.getColumnIndex("updatedAt")
        val noteObjectId = cursor.getColumnIndex("objectId")
        cursor.moveToFirst()
        if (cursor.count <= 0) {
            //TODO QUERY FROM PARSE-SERVER
        } else {
            arrayFeedList = ArrayList()
            while (!cursor.isAfterLast) {

                //Initialize HashMap
                feedData = HashMap()
                feedData["titleFormatted"] = formatTitle(cursor.getString(noteTitle))
                feedData["bodyFormatted"] = formatBody(cursor.getString(noteBody))
                feedData["title"] = cursor.getString(noteTitle)
                feedData["body"] = cursor.getString(noteBody)
                feedData["date"] = getDateString(cursor.getLong(noteUpdatedAt))
                feedData["id"] = cursor.getInt(noteId).toString()
                feedData["objectId"] = cursor.getString(noteObjectId)


                //Set HashMap to arrayList
                arrayFeedList.add(feedData)

                cursor.moveToNext()
            }

            //Set RecyclerView Adapter
            mainRecyclerView.layoutManager = LinearLayoutManager(this)
           val adapter = MainNotesRecyclerview(this, arrayFeedList)
            adapter.setClickListener(this)
            mainRecyclerView.adapter = adapter
        }


    }

    override fun onItemClick(view: View, position: Int) {

       // showAd()

        val i = Intent(this@MainActivity, CreateNote::class.java)
        i.putExtra("type", "edit")
        i.putExtra("title", arrayFeedList[position]["title"])
        i.putExtra("body", arrayFeedList[position]["body"])
        i.putExtra("date", arrayFeedList[position]["date"])
        i.putExtra("id", arrayFeedList[position]["id"])
        startActivity(i)
        showAd()
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateString(updatedAtMili: Long): String {

        val dateString: String
        //long currentMili = Calendar.getInstance().getTimeInMillis();
        val format: DateFormat
        if (DateUtils.isToday(updatedAtMili)) {
            format = SimpleDateFormat("hh:mm a")
            dateString = "Today " + format.format(Date(updatedAtMili))
        } else if (isYesterday(updatedAtMili)) {
            format = SimpleDateFormat("hh:mm a")
            dateString = "Yesterday " + format.format(Date(updatedAtMili))
        } else if (!isSameYear(updatedAtMili)) {
            format = SimpleDateFormat("MMM dd yyyy")
            dateString = format.format(Date(updatedAtMili))
        } else {
            format = SimpleDateFormat("MMM dd hh:mm a")
            dateString = format.format(Date(updatedAtMili))
        }

        return dateString
    }

    private fun isYesterday(updatedAtMili: Long): Boolean {
        val now = Calendar.getInstance()
        val cdate = Calendar.getInstance()
        cdate.timeInMillis = updatedAtMili

        now.add(Calendar.DATE, -1)

        return (now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
                && now.get(Calendar.DATE) == cdate.get(Calendar.DATE))
    }

    private fun isSameYear(updatedAtMili: Long): Boolean {
        var isSame = true
        val now = Calendar.getInstance()
        val cdate = Calendar.getInstance()
        cdate.timeInMillis = updatedAtMili

        if (now.get(Calendar.YEAR) != cdate.get(Calendar.YEAR)) {
            isSame = false
        }

        return isSame
    }

    private fun formatTitle(title: String): String {

        return if (title.length >= 33) {
            title.substring(0, 30) + "..."
        } else {
            title
        }
    }

    private fun formatBody(bodyS: String): String {
        var body = bodyS
        val noteSubStr: String
        val newline = System.getProperty("line.separator")!!
        val hasNewline = body.contains(newline)
        if (hasNewline) {
            body = body.replace(newline, " ")
        }

        noteSubStr = if (body.length >= 110) {
            body.substring(0, 107) + "..."
        } else {
            body
        }

        return noteSubStr
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

}