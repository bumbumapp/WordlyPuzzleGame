package com.bumbumapps.khiardle

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

/** Rewarded ad used to reveal a letter. Loaded once (after consent) and reloaded after each show. */
object Loadads {

    /** Becomes true once UMP consent allows ad requests (drives the Compose banner). */
    val adsReady = androidx.compose.runtime.mutableStateOf(false)

    var rewardedAd: RewardedAd? = null
    private var loadingRewarded = false

    fun loadRewardAds(context: Context) {
        if (rewardedAd != null || loadingRewarded || !AdsConsent.canRequestAds(context)) return
        loadingRewarded = true
        RewardedAd.load(
            context.applicationContext, context.getString(R.string.admob_rewarded_id),
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    loadingRewarded = false
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    loadingRewarded = false
                    rewardedAd = ad
                }
            })
    }

    /** Shows the rewarded ad; [onReward] runs only when the user earned the reward. */
    fun showRewarded(context: Context, onReward: () -> Unit) {
        val activity = context.findActivity()
        val ad = rewardedAd
        if (ad == null || activity == null) {
            Toast.makeText(context, R.string.ad_not_ready, Toast.LENGTH_SHORT).show()
            loadRewardAds(context)
            return
        }
        rewardedAd = null
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() = loadRewardAds(activity)
            override fun onAdFailedToShowFullScreenContent(e: AdError) = loadRewardAds(activity)
        }
        ad.show(activity) { onReward() }
    }
}

fun Context.findActivity(): Activity? {
    var c: Context? = this
    while (c is android.content.ContextWrapper) {
        if (c is Activity) return c
        c = c.baseContext
    }
    return null
}
