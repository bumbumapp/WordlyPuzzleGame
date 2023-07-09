package com.bumbumapps.khiardle.ui

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumbumapps.khiardle.Globals
import com.bumbumapps.khiardle.Loadads
import com.bumbumapps.khiardle.Loadads.mInterstitialAd
import com.bumbumapps.khiardle.R
import com.bumbumapps.khiardle.Timers
import com.bumbumapps.khiardle.backend.models.Game
import com.bumbumapps.khiardle.backend.models.Languages
import com.bumbumapps.khiardle.backend.models.Level
import com.bumbumapps.khiardle.backend.usecase.GetWordStatus
import com.bumbumapps.khiardle.backend.viewmodel.GameViewModel
import com.bumbumapps.khiardle.backend.viewmodel.LevelsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd

@Composable
internal fun WordScreen(
    level: Level,
    context:Context,
    getWordStatus: GetWordStatus,
    language: Languages,
    levelsViewModel: LevelsViewModel,
    levelCompleted: () -> Unit,
) {
    val word = level.word
    val viewModel = remember(word) {
        val initialGame = Game(word, listOf(
        ), 5)
        GameViewModel(initialGame, getWordStatus)
    }

    val state by viewModel.state().collectAsState()
    GameScreen(level,context,state,language, onKey = {
        viewModel.characterEntered(it)
    },
        onBackspace = {
            viewModel.backspacePressed()
        },
        onSubmit = {
            if (Globals.TIMER_FINISHED){
                mInterstitialAd?.show(context as Activity)
                mInterstitialAd?.fullScreenContentCallback=object: FullScreenContentCallback(){
                    override fun onAdDismissedFullScreenContent() {
                        Globals.TIMER_FINISHED=false
                        Timers.timer().start()
                        viewModel.submit()
                        Loadads.loadGoogleInterstitialAd(context)
                    }
                }
            }else{
                viewModel.submit()
            }
        }, shownError = {
            viewModel.shownNotExists()
        },
        shownWon = levelCompleted,
        shownLost = {
            viewModel.shownLost()
        },
        onChangeLanguage = {
           levelsViewModel.levelPassed(true)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameScreen(
    level: Level,
    context: Context,
    state: GameViewModel.State,
    language: Languages,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    shownError: () -> Unit,
    shownWon: () -> Unit,
    onChangeLanguage:()->Unit,
    shownLost: () -> Unit,
) {

    Box(
        Modifier
            .fillMaxSize()) {

        Column {
            GameHeader(level,context,onChangeLanguage,language )

            GameGrid(state,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
                    .fillMaxWidth(0.6f)
                    .align(CenterHorizontally))
            Spacer(modifier = Modifier.size(16.dp))
            GameKeyboard(
                state,
                language,
                onKey = onKey,
                onBackspace = onBackspace,
                onSubmit = onSubmit,
            )
            Spacer(modifier = Modifier.size(16.dp))

            Column(verticalArrangement = Arrangement.Bottom) {
                AndroidView(
                    factory = { context ->
                        AdView(context).apply {
                            adUnitId = context.getString(R.string.banner_id)
                            setAdSize(AdSize.FULL_BANNER)
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            loadAd(AdRequest.Builder().build())
                        }
                    }
                )
            }
        }
        ErrorScreen(state, shownError)
        WonScreen(state, shownWon)
        GameOverScreen(state, shownLost)
    }
}
