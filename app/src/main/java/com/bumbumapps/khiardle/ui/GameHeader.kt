package com.bumbumapps.khiardle.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumbumapps.khiardle.Loadads
import com.bumbumapps.khiardle.Loadads.rewardedAd
import com.bumbumapps.khiardle.backend.models.Languages
import com.bumbumapps.khiardle.backend.models.Level
import com.bumbumapps.khiardle.ui.theme.*
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ColumnScope.GameHeader(level: Level,context: Context,onChangeLanguages: () -> Unit,language: Languages,modifier: Modifier = Modifier) {
    var revealing1 by remember(level) { mutableStateOf(false) }
    var revealing2 by remember(level) { mutableStateOf(false) }
    var revealing3 by remember(level) { mutableStateOf(false) }
    var revealing4 by remember(level) { mutableStateOf(false) }
    var revealing5 by remember(level) { mutableStateOf(false) }

    GameHeader(modifier) {
        LevelHeaderContent(level,context,language,revealing1,revealing2,revealing3,revealing4,revealing5,onChangeLanguages) {x,y,z,a,b->
            revealing1 = x
            revealing2 =y
            revealing3 =z
            revealing4 =a
            revealing5 =b
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ColumnScope.GameHeader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    Column(modifier
        .clickable {


        }
        .align(Alignment.CenterHorizontally)) {

        Text(text = "Wordly",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color= Yellow,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.align(
                Alignment.CenterHorizontally))

        content()

    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ColumnScope.LevelHeaderContent(
    level: Level,
    context: Context,
    language: Languages,
    revealing1: Boolean,
    revealing2: Boolean,
    revealing3: Boolean,
    revealing4: Boolean,
    revealing5: Boolean,
    onChangeLanguages:()->Unit,
    onRevealChanged: (Boolean, Boolean, Boolean, Boolean, Boolean) -> Unit,

    ) {
    val languages = listOf("English","German","Dutch","French","Italian","Turkish","Portuguese","Catalan","Spanish")
    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.Companion
            .align(
                Alignment.CenterHorizontally
            )
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Level ${level.number}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            color = Yellow,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .weight(0.4f)
                .padding(start = 10.dp, end = 10.dp)

        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            modifier = Modifier.weight(0.5f),

            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                readOnly = true,
                value =language.getString().toString(),
                onValueChange = {},
                label = { Text("Languages", fontSize = 15.sp) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Color.White,
                    backgroundColor =Color.Transparent, focusedIndicatorColor = Color.Transparent,
                    trailingIconColor =Color.White, leadingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.background(color = Pink)
            ) {
                languages.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            if (selectionOption!=language.getString()){
                                language.setString(selectionOption)
                                onChangeLanguages()
                            }
                            expanded = false
                        }
                    ) {
                        Text(text =selectionOption )
                    }
                }
            }
        }

        val openDialog = remember { mutableStateOf(false)  }
        val colors = listOf(MaterialTheme.colorScheme.correctBackground,
            MaterialTheme.colorScheme.wrongPositionBackground,
            MaterialTheme.colorScheme.incorrectBackground,
            MaterialTheme.colorScheme.keyboardDisabled)
        val texts= listOf("correct letter and correct position in word",
            "correct letter and wrong position in word",
            "wrong letter and wrong position in word",
            "wrong letter and wrong position show in keyboard")

        Column(modifier = Modifier.weight(0.1f),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color.Black)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center

            ){
                Text(text="?",
                    fontSize = 18.sp,
                    color= Color.White,
                    textAlign = TextAlign.Center,
                    modifier=Modifier.clickable{
                        openDialog.value=true
                    })
            }
        }

        if (openDialog.value) {

            AlertDialog(
                backgroundColor=Pink,
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Help", textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth(),
                    color= Color.White)
                },
                text = {


                        Column() {
                            repeat(4){
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Card(
                                    modifier = Modifier
                                        .size(30.dp),
                                    backgroundColor=colors[it],
                                    shape = CircleShape){}
                                Text(text = texts[it],
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 12.dp))
                            }
                        }

                    }

                },
                confirmButton = {
                },
                dismissButton = {

                        Text("OK",
                        color= Yellow,
                        modifier = Modifier
                            .clickable {
                                openDialog.value = false
                            }
                            .padding(15.dp))



                })
        }
    }


    Row(
        modifier = Modifier.Companion
            .align(
                Alignment.CenterHorizontally
            )
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp)
            ) {
                repeat(1) { row ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(3.dp))
                                .aspectRatio(1f)
                                .background(Color.Transparent)
                                .border(2.dp, Color.White),
                            contentAlignment = Alignment.Center

                        ){
                            if (!revealing1){
                                Text(text = "?",color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,  modifier = Modifier.clickable {
                                        rewardedAd.let { ad ->
                                            ad?.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                                                onRevealChanged(true,revealing2,revealing3,revealing4,revealing5)
                                                Loadads.loadRewardAds(context)

                                            })
                                        } ?: run {
                                            Log.d("TAG", "The rewarded ad wasn't ready yet.")
                                        }
                                    })
                            }
                            else{
                                Text(text = level.word.word[0].toString(),color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.Transparent)
                                .border(2.dp, Color.White),
                            contentAlignment = Alignment.Center

                        ){
                            if (!revealing2){
                                Text(text = "?",color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,  modifier = Modifier.clickable {
                                        rewardedAd.let { ad ->
                                            ad?.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                                                onRevealChanged(revealing1,true,revealing3,revealing4,revealing5)
                                                Loadads.loadRewardAds(context)

                                            })
                                        } ?: run {
                                            Log.d("TAG", "The rewarded ad wasn't ready yet.")
                                        }

                                    })
                            }
                            else{
                                Text(text = level.word.word[1].toString(),color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,  modifier = Modifier.clickable {

                                    } )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.Transparent)
                                .border(2.dp, Color.White),
                            contentAlignment = Alignment.Center

                        ){
                            if (!revealing3){
                                Text(text = "?",color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,  modifier = Modifier.clickable {
                                        rewardedAd?.let { ad ->
                                            ad.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                                                onRevealChanged(revealing1,revealing2,true,revealing4,revealing5)
                                                Loadads.loadRewardAds(context)

                                            })
                                        } ?: run {
                                            Log.d("TAG", "The rewarded ad wasn't ready yet.")
                                        }
                                    })
                            }
                            else{
                                Text(text = level.word.word[2].toString(),color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(3.dp))
                                .aspectRatio(1f)
                                .background(Color.Transparent)
                                .border(2.dp, Color.White),
                            contentAlignment = Alignment.Center

                        ){
                            if (!revealing4){
                                Text(text = "?",color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,  modifier = Modifier.clickable {
                                        rewardedAd?.let { ad ->
                                            ad.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                                                onRevealChanged(revealing1,revealing2,revealing3,true,revealing5)
                                                Loadads.loadRewardAds(context)
                                            })
                                        } ?: run {
                                            Log.d("TAG", "The rewarded ad wasn't ready yet.")
                                        }

                                    })
                            }
                            else{
                                Text(text = level.word.word[3].toString(),color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.Transparent)
                                .border(2.dp, Color.White),
                            contentAlignment = Alignment.Center

                        ){
                            if (!revealing5){
                                Text(text = "?",color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,  modifier = Modifier.clickable {
                                        rewardedAd?.let { ad ->
                                            ad.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                                                onRevealChanged(revealing1,revealing2,revealing3,revealing4,true)
                                                Loadads.loadRewardAds(context)

                                            })
                                        } ?: run {
                                            Log.d("TAG", "The rewarded ad wasn't ready yet.")
                                        }
                                    })
                            }
                            else{
                                Text(text = level.word.word[4].toString(),color = Color.White,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center)
                            }
                        }

                    }
                }
            }

        }
    }
}

