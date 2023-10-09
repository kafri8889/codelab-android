package com.anafthdev.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.anafthdev.artspace.data.LocalArtDataProvider
import com.anafthdev.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb())
        )

        super.onCreate(savedInstanceState)

        setContent {
            ArtSpaceTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceAppContent(
                        windowSizeClass = calculateWindowSizeClass(activity = this)
                    )
                }
            }
        }
    }
}

@Composable
private fun ArtSpaceAppContent(
    windowSizeClass: WindowSizeClass
) {

    var currentArtIndex by rememberSaveable { mutableIntStateOf(0) }

    val currentArt = remember(currentArtIndex) {
        LocalArtDataProvider.values[currentArtIndex]
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(0.9f)
        ) {
            Crossfade(
                label = "bitmap",
                targetState = currentArt.rawResId,
                animationSpec = tween(1024)
            ) { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .widthIn(max = 360.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8))
                )
            }

            Card(
                modifier = Modifier
                    .widthIn(
                        max = 360.dp,
                        // if width is Medium or Expanded, set width to 360.dp
                        // else wrap content size
                        min = if (
                            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium ||
                            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
                        ) 360.dp else 0.dp
                    )
                    .animateContentSize(tween(1024))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = currentArt.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "${currentArt.artist} (${currentArt.year})",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // If currentArtIndex is 0 and user click previous button,
                        // change to last index
                        if (currentArtIndex <= 0) {
                            currentArtIndex = LocalArtDataProvider.values.lastIndex
                            return@Button
                        }

                        currentArtIndex--
                    }
                ) {
                    Text(text = "Previous")
                }

                Button(
                    onClick = {
                        // If currentArtIndex is last index of art values and user click next button,
                        // change to 0
                        if (currentArtIndex >= LocalArtDataProvider.values.lastIndex) {
                            currentArtIndex = 0
                            return@Button
                        }

                        currentArtIndex++
                    }
                ) {
                    Text(text = "Next")
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun ArtSpaceAppContentPreview_Compat() {
    ArtSpaceTheme(darkTheme = false) {
        ArtSpaceAppContent(WindowSizeClass.calculateFromSize(DpSize(411.dp, 891.dp)))
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showSystemUi = true, device = "spec:width=599dp,height=899dp")
@Composable
private fun ArtSpaceAppContentPreview_Medium() {
    ArtSpaceTheme(darkTheme = false) {
        ArtSpaceAppContent(WindowSizeClass.calculateFromSize(DpSize(599.dp, 899.dp)))
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showSystemUi = true, device = "spec:width=841dp,height=1000dp")
@Composable
private fun ArtSpaceAppContentPreview_Expanded() {
    ArtSpaceTheme(darkTheme = false) {
        ArtSpaceAppContent(WindowSizeClass.calculateFromSize(DpSize(841.dp, 1000.dp)))
    }
}
