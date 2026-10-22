package com.bumbumapps.khiardle.ui

import com.bumbumapps.khiardle.InterstitialHelper
import com.bumbumapps.khiardle.findActivity

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
import com.bumbumapps.khiardle.R
import com.bumbumapps.khiardle.backend.models.Game
import com.bumbumapps.khiardle.backend.models.Languages
import com.bumbumapps.khiardle.backend.models.Level
import com.bumbumapps.khiardle.backend.usecase.GetWordStatus
import com.bumbumapps.khiardle.backend.viewmodel.GameViewModel
import com.bumbumapps.khiardle.backend.viewmodel.LevelsViewModel

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
            // Interstitial at most once per 60 s; the guess is always submitted (it used to be
            // swallowed when the timer had expired but no ad was loaded).
            InterstitialHelper.showIfReady(context.findActivity()) { viewModel.submit() }
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
                // Adaptive banner, loaded once consent allows ad requests.
                val adsReady = com.bumbumapps.khiardle.Loadads.adsReady.value
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { ctx -> android.widget.FrameLayout(ctx) },
                    update = { frame ->
                        if (adsReady && frame.childCount == 0) {
                            frame.context.findActivity()?.let { com.bumbumapps.khiardle.BannerHelper.load(it, frame) }
                        }
                    },
                )
            }
        }
        ErrorScreen(state, shownError)
        WonScreen(state, shownWon)
        GameOverScreen(state, shownLost)
    }
}
