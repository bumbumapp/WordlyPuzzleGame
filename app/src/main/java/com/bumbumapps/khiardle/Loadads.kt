package com.bumbumapps.khiardle

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object Loadads {

     var mInterstitialAd:InterstitialAd? = null
     var rewardedAd:RewardedAd? = null
    fun loadGoogleInterstitialAd(context: Context) {
        mInterstitialAd = null
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, context.getString(R.string.interstial_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                   Log.d("loadError",loadAdError.toString())
                }
            })
    }

    fun loadRewardAds(context: Context){
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context,context.getString(R.string.rewarded_id), adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.toString())
                rewardedAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                rewardedAd = ad
            }
        })
    }
}