package com.example.pvzdatabase.data.model

import com.example.pvzdatabase.R

object FakePlantDataSource {
    val dummyPlants = listOf(
        Plants(
            1,
            "PEASHOOTER",
            "Peashooters are your first line of defense. They shoot peas at attacking zombies.",
            "100",
            "Fast",
            "Normal",
            "Normal",
            "Medium",
            R.drawable.peashooter
            ),
        Plants(
            2,
            "SUNFLOWER",
            "Sunflowers are essential for you to produce extra sun. Try planting as many as you can!",
            "50",
            "Fast",
            "Normal",
            "Normal",
            "-",
            R.drawable.sunflower
        ),
        Plants(
            3,
            "CHERRY BOMB",
            "Cherry Bombs can blow up all zombies in an area. They have a short fuse so plant them near zombies.",
            "150",
            "Very Slow",
            "Massive",
            "-",
            "All zombies in a medium area",
            R.drawable.cherry_bomb
        ),
        Plants(
            4,
            "WALL-NUT",
            "Wall-nuts have hard shells which you can use to protect your other plants.",
            "50",
            "Slow",
            "-",
            "High",
            "-",
            R.drawable.wall_nut
        ),
        Plants(
            5,
            "POTATO MINE",
            "Potato Mines pack a powerful punch, but they need a while to arm themselves. You should plant them ahead of zombies. They will explode on contact.",
            "25",
            "Slow",
            "Massive",
            "-",
            "All zombies in a small area",
            R.drawable.potato_mine
        ),
        Plants(
            6,
            "SNOW PEA",
            "Snow Peas shoot frozen peas that damage and slow the enemy.",
            "175",
            "Fast",
            "Normal, slows zombies",
            "Normal",
            "-",
            R.drawable.snow_pea
        ),
        Plants(
            7,
            "CHOMPER",
            "Chompers can devour a zombie whole, but they are vulnerable while chewing.",
            "150",
            "Fast",
            "Massive",
            "Normal",
            "Two Tiles",
            R.drawable.chomper
        ),
        Plants(
            8,
            "REPEATER",
            "Repeater fires two peas at a time.",
            "200",
            "Fast",
            "Normal (for each pea)",
            "Normal",
            "-",
            R.drawable.repeater
        ),
        Plants(
            9,
            "CACTUS",
            "Cactuses shoot spikes that can hit both ground and air targets.",
            "125",
            "Fast",
            "Normal",
            "Normal",
            "Ground and Air",
            R.drawable.cactus
        ),
        Plants(
            10,
            "SQUASH",
            "Squashes will smash the first zombie that gets close to it.",
            "50",
            "Slow",
            "Massive",
            "-",
            "Short range, hits all zombies that it lands on",
            R.drawable.squash
        )
    )
}