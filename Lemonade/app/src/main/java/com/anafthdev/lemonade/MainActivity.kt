package com.anafthdev.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anafthdev.lemonade.ui.theme.LemonadeTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb())
        )

        super.onCreate(savedInstanceState)

        setContent {
            LemonadeTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeAppContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeAppContent() {

    val coroutineScope = rememberCoroutineScope()

    val rotateAnimatable = remember { Animatable(0f) }
    val scaleAnimatable = remember { Animatable(1f) }

    var lemonProgress by rememberSaveable { mutableStateOf(LemonProgress.Tree) }

    // Image resource for current lemonProgress
    // Why not use lemonProgress.drawableRes directly in composable Image()?,
    // because we want to change image when box rotated
    var imageResource by rememberSaveable { mutableIntStateOf(lemonProgress.drawableRes) }

    // how many times should a lemon be squeezed
    var squeezeCount by rememberSaveable { mutableIntStateOf(0) }

    // Rotate the image when lemonProgress has changed
    LaunchedEffect(lemonProgress) {
        // Scale in the box
        scaleAnimatable.animateTo(0.5f, tween(128))

        // Rotate image
        rotateAnimatable.animateTo(targetValue = 1080f, animationSpec = tween(512))

        // Change image
        imageResource = lemonProgress.drawableRes

        // Rotate back
        rotateAnimatable.animateTo(targetValue = 0f, animationSpec = tween(512))

        // Scale out the box
        scaleAnimatable.animateTo(1f)
    }

    // Use scaffold to implements the basic material design visual layout structure.
    Scaffold(
        topBar = {
            // Add the top app bar
            CenterAlignedTopAppBar(
                title = {
                    Text("Lemonade")
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { scaffoldPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Use box to add background and corner
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .aspectRatio(1f / 1f)
                        // Why we use lambda graphicsLayer?
                        // because to prevent multiple recomposition when animating
                        .graphicsLayer {
                            rotationY = rotateAnimatable.value
                            scaleX = scaleAnimatable.value
                            scaleY = scaleAnimatable.value
                        }
                        .clip(RoundedCornerShape(25))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .clickable {
                            // prevent click if animation is running
                            if (rotateAnimatable.isRunning || scaleAnimatable.isRunning) return@clickable

                            // check if squeezeCount is greater than zero,
                            // reduce it and animate the scale
                            if (squeezeCount > 0) {
                                squeezeCount--

                                // Scale in and out the image if lemonProgress is LemonProgress.Squeeze and squeezeCount > 0
                                coroutineScope.launch {
                                    scaleAnimatable.animateTo(0.8f)
                                    scaleAnimatable.animateTo(1f)
                                }

                                return@clickable
                            }

                            lemonProgress = lemonProgress
                                .next()
                                .also { nextLemonProgress ->
                                    // if next lemon progress is squeeze
                                    // update squeezeCount (randomly)
                                    if (nextLemonProgress == LemonProgress.Squeeze) {
                                        squeezeCount = Random.nextInt(1, 5)
                                    }
                                }
                        }
                ) {
                    // animate image when imageResource has changed
                    AnimatedContent(
                        label = "image",
                        targetState = imageResource,
                        transitionSpec = {
                            scaleIn(tween(512)) togetherWith scaleOut(tween(256))
                        }
                    ) { drawableRes ->
                        // Image to show
                        Image(
                            painter = painterResource(id = drawableRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .aspectRatio(1f / 1f)
                        )
                    }
                }

                Text(
                    text = lemonProgress.text,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun LemonadeAppContentPreview() {
    LemonadeTheme(darkTheme = false) {
        LemonadeAppContent()
    }
}
