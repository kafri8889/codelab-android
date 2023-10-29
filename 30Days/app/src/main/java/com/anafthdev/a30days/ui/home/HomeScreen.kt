package com.anafthdev.a30days.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anafthdev.a30days.data.datasource.LocalRecipeDataProvider
import com.anafthdev.a30days.data.model.Recipe
import com.anafthdev.a30days.uicomponent.RecipeItem

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("30 Days Of Clean Eating")
                }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            item {
                Spacer(Modifier.height(16.dp))
            }

            items(
                items = LocalRecipeDataProvider.values,
                key = { item: Recipe -> item.days }
            ) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
