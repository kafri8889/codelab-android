package com.anafthdev.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anafthdev.diceroller.ui.theme.DiceRollerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb())
        )

        super.onCreate(savedInstanceState)

        setContent {
            DiceRollerTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerAppContent()
                }
            }
        }
    }
}

@Composable
private fun DiceRollerAppContent() {

    val coroutineScope = rememberCoroutineScope()

    val rollAnimatable = remember { Animatable(0f) }

    var currentDice by rememberSaveable { mutableStateOf(Dice.One) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = currentDice.drawableRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .aspectRatio(1f / 1f)
                // Why we use lambda graphicsLayer?
                // because to prevent multiple recomposition when animating
                .graphicsLayer {
                    rotationX = rollAnimatable.value
                    rotationY = rollAnimatable.value
                    rotationZ = rollAnimatable.value
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    // Animate roll
                    rollAnimatable.animateTo(targetValue = 720f, animationSpec = tween(256))
                    
                    // Random dice
                    currentDice = Dice.entries.random()

                    // Animate back
                    rollAnimatable.animateTo(targetValue = 0f, animationSpec = tween(256))
                }
            }
        ) {
            Text("Roll")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DiceRollerAppContentPreview() {
    DiceRollerTheme(darkTheme = false) {
        DiceRollerAppContent()
    }
}
