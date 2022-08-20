package com.cutetech.memorymatchup.presentation.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cutetech.memorymatchup.ui.theme.MemoryMatchupTheme
import com.cutetech.memorymatchup.utils.PARAMS
import com.cutetech.memorymatchup.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class GameActivity : ComponentActivity() {

    private lateinit var params: Params

    private fun readParams(intent: Intent) {
        val receivedParams: Params? = when {
            intent.hasExtra(PARAMS) -> {
                intent.parcelable(PARAMS)
            }
            else -> Params()
        }
        params = requireNotNull(receivedParams)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readParams(intent)
        setContent {
            MemoryMatchupTheme {
                GameScreen(params.gameMode)
            }
        }
    }

    @Parcelize
    data class Params(
        val gameMode: GameMode = GameMode.EASY
    ) : Parcelable

    companion object {
        fun launch(context: Context, params: Params = Params()) {
            context.startActivity(getLaunchIntent(context, params))
        }

        private fun getLaunchIntent(context: Context, params: Params, vararg flags: Int): Intent {
            return with(Intent(context, GameActivity::class.java)) {
                flags.forEach { addFlags(it) }
                putExtra(PARAMS, params)
            }
        }
    }
}