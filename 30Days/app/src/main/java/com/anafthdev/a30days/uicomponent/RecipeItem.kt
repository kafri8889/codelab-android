package com.anafthdev.a30days.uicomponent

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anafthdev.a30days.data.datasource.LocalRecipeDataProvider
import com.anafthdev.a30days.data.model.Recipe
import com.anafthdev.a30days.theme._30DaysTheme

@Preview
@Composable
private fun RecipeItemPreview() {

    _30DaysTheme(darkTheme = false) {
        RecipeItem(
            recipe = LocalRecipeDataProvider.recipe1,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .animateContentSize(tween(256))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = recipe.imgResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Day ${recipe.days}",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            ExpandableTextButton(
                text = recipe.description,
                maxLines = 3,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
