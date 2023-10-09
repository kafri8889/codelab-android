package com.anafthdev.artspace.data

import com.anafthdev.artspace.R
import com.anafthdev.artspace.model.Art

object LocalArtDataProvider {

    val tallBuildings = Art(
        title = "Tall Buildings",
        rawResId = R.raw.tall_buildings,
        artist = "Claudio Schwarz",
        year = "2023"
    )

    val snowMountain = Art(
        title = "Snow Mountain",
        rawResId = R.raw.snow_mountain,
        artist = "Ashok Sharma",
        year = "2022"
    )

    val mountain = Art(
        title = "Mountain",
        rawResId = R.raw.mountain,
        artist = "Huey Sze Chan",
        year = "2022"
    )

    val mushroom = Art(
        title = "Mushrooms",
        rawResId = R.raw.mushroom,
        artist = "Joyce G",
        year = "2020"
    )

    val road = Art(
        title = "Road",
        rawResId = R.raw.road,
        artist = "Mauro Lima",
        year = "2023"
    )

    val values = arrayOf(
        tallBuildings,
        snowMountain,
        mountain,
        mushroom,
        road
    )

}