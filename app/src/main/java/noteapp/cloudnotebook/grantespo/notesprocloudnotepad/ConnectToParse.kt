package noteapp.cloudnotebook.grantespo.notesprocloudnotepad

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
//import com.parse.Parse

@Suppress("unused")
class ConnectToParse : Application() {

    companion object {
        var adCounter = 6
        lateinit var mInterstitialAd: InterstitialAd

        fun showAd(){

            adCounter++
            Log.i("adChecker", "showAd(): adCounter = $adCounter")
            if(adCounter >= 8){
                if (mInterstitialAd.isLoaded) {
                    Log.i("adChecker", "mInterstitialAd.isLoaded")
                    mInterstitialAd.show()
                    adCounter = 0
                } else {
                    Log.i("adChecker", "mInterstitialAd NOT Loaded")
                    adCounter--
                    if(!mInterstitialAd.isLoading){
                        Log.i("adChecker", "mInterstitialAd NOT LOADING")
                        mInterstitialAd.loadAd(AdRequest.Builder().build())
                    }
                }
            }
            else if(adCounter == 5){
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

        }
    }

    override fun onCreate() {
        super.onCreate()

       /* Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId("xcbdvft463874810uhfdfhddvbfhyt343892")
                .server("http://skynote1.herokuapp.com/parse/")
                .build()
        )*/

        if(adCounter == 6){
            Log.i("adChecker", "onCreate(): adCounter = $adCounter")
            MobileAds.initialize(this, "ca-app-pub-1903987913186752~1045432050")
            mInterstitialAd =  InterstitialAd(this@ConnectToParse)
            //mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
            mInterstitialAd.adUnitId = "ca-app-pub-1903987913186752/2134974830"
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }
    }
}



