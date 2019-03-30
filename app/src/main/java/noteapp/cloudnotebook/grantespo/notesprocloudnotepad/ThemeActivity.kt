package noteapp.cloudnotebook.grantespo.notesprocloudnotepad

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_theme.*
import java.util.ArrayList
import java.util.HashMap

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ThemeActivity : AppCompatActivity(), ThemeRecyclerview.ItemClickListener {


    private lateinit var arrayFeedList: ArrayList<HashMap<String, String>>
    private lateinit var feedData: HashMap<String, String>

    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var themeName:String = "Sky"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeSet()
        setContentView(R.layout.activity_theme)

        initList()

    }
    override fun onItemClick(view: View, position: Int) {

        when (position) {

            0 -> {
                showAlert(getString(R.string.sky))
            }
            1 -> {
                showAlert(getString(R.string.jungle))
            }
            2 -> {
                showAlert(getString(R.string.rose))
            }
            3 -> {
                showAlert(getString(R.string.lemon))
            }
            4 -> {
                showAlert(getString(R.string.violet))
            }
            5 -> {
                showAlert(getString(R.string.tiger))
            }
            6 -> {
                showAlert(getString(R.string.taffy))
            }
            7 -> {
                showAlert(getString(R.string.lagoon))
            }
        }
    }

    private fun showAlert(name:String){



        val builder1 = AlertDialog.Builder(this@ThemeActivity)
        builder1.setMessage("Would you like to Enable the $name Theme?")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
                "Yes"
        ) { dialog, _ ->
            editor.putString("themeName", name)
            editor.commit()
            dialog.cancel()
            Toast.makeText(this@ThemeActivity, "$name Theme activated!", Toast.LENGTH_LONG).show()
            //ConnectToParse.showAd()
            val i = Intent(this@ThemeActivity, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            ConnectToParse.showAd()

        }

        builder1.setNegativeButton(
                "No"
        ) { dialog, _ ->

            dialog.cancel()
        }

        val alert11 = builder1.create()
        alert11.show()

    }


    private fun initList(){
        val dividerItemDecoration = DividerItemDecoration(themeRecyclerView.context,
                DividerItemDecoration.VERTICAL)
        themeRecyclerView.addItemDecoration(dividerItemDecoration)

        arrayFeedList = ArrayList()

        feedData = HashMap()
        feedData["name"] = getString(R.string.sky)
        feedData["color"] = "#2971f5"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.jungle)
        feedData["color"] = "#3BA71C"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.rose)
        feedData["color"] = "#A71C21"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.lemon)
        feedData["color"] = "#F0EC27"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.violet)
        feedData["color"] = "#721CA7"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.tiger)
        feedData["color"] = "#EC7F0A"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.taffy)
        feedData["color"] = "#E220D8"
        arrayFeedList.add(feedData)

        feedData = HashMap()
        feedData["name"] = getString(R.string.lagoon)
        feedData["color"] = "#20E2B5"
        arrayFeedList.add(feedData)

        //Set RecyclerView Adapter
        themeRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ThemeRecyclerview(this, arrayFeedList)
        adapter.setClickListener(this)
        themeRecyclerView.adapter = adapter


        themeBack.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    themeBack.background = getDrawable(R.drawable.roundedcornerpressed)
                    themeBack.setTextColor(Color.parseColor("#2261b4"))
                    themeBack.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_UP -> {
                    themeBack.background = getDrawable(R.drawable.roundedcorner)
                    themeBack.setTextColor(Color.WHITE)
                    themeBack.setPadding(15, 15, 15, 15)
                }
                MotionEvent.ACTION_CANCEL -> {
                    themeBack.background = getDrawable(R.drawable.roundedcorner)
                    themeBack.setTextColor(Color.WHITE)
                    themeBack.setPadding(15, 15, 15, 15)
                }
            }


            false
        }

        themeBack.setOnClickListener {
            onBackPressed()
        }



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
}
