package com.anafthdev.lemonade

enum class LemonProgress {
    Tree,
    Squeeze,
    Drink,
    EmptyGlass;

    val text: String
        get() = when (this) {
            Tree -> "Tap the lemon tree to select a lemon"
            Squeeze -> "Keep tapping the lemon to squeeze it"
            Drink -> "Tap the lemondae to drink it"
            EmptyGlass -> "Tap the empty glass to start again"
        }

    val drawableRes: Int
        get() = when (this) {
            Tree -> R.drawable.lemon_tree
            Squeeze -> R.drawable.lemon_squeeze
            Drink -> R.drawable.lemon_drink
            EmptyGlass -> R.drawable.lemon_restart
        }

    fun next(): LemonProgress = when (this) {
        Tree -> Squeeze
        Squeeze -> Drink
        Drink -> EmptyGlass
        EmptyGlass -> Tree
    }
}