package com.bumbumapps.khiardle

import android.content.Context
import android.widget.Toast
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.reward.Reward
import com.huawei.hms.ads.reward.RewardAd
import com.huawei.hms.ads.reward.RewardAdLoadListener
import com.huawei.hms.ads.reward.RewardAdStatusListener

/**
 * Huawei AppGallery build: rewarded ad (Huawei Ads) used to reveal a letter, same API as the Play build.
 * Loaded once (after init) and reloaded after each show.
 */
object Loadads {

    /** Becomes true once ads can be requested (drives the Compose banner). */
    val adsReady = androidx.compose.runtime.mutableStateOf(false)

    var rewardedAd: RewardAd? = null
    private var loadingRewarded = false

    fun loadRewardAds(context: Context) {
        if (rewardedAd != null || loadingRewarded || !AdsConsent.canRequestAds(context)) return
        loadingRewarded = true
        val app = context.applicationContext
        val slot = app.getString(if (BuildConfig.DEBUG) R.string.hms_test_rewarded_id else R.string.hms_rewarded_id)
        val ad = RewardAd(app, slot)
        ad.loadAd(AdParam.Builder().build(), object : RewardAdLoadListener() {
            override fun onRewardAdFailedToLoad(errorCode: Int) {
                loadingRewarded = false
                rewardedAd = null
            }

            override fun onRewardedLoaded() {
                loadingRewarded = false
                rewardedAd = ad
            }
        })
    }

    /** Shows the rewarded ad; [onReward] runs only when the user earned the reward. */
    fun showRewarded(context: Context, onReward: () -> Unit) {
        val activity = context.findActivity()
        val ad = rewardedAd
        if (ad == null || !ad.isLoaded || activity == null) {
            Toast.makeText(context, R.string.ad_not_ready, Toast.LENGTH_SHORT).show()
            loadRewardAds(context)
            return
        }
        rewardedAd = null
        ad.show(activity, object : RewardAdStatusListener() {
            override fun onRewarded(reward: Reward) = onReward()
            override fun onRewardAdClosed() = loadRewardAds(activity)
            override fun onRewardAdFailedToShow(errorCode: Int) = loadRewardAds(activity)
        })
    }
}
