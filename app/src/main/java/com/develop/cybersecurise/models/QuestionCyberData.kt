package com.develop.cybersecurise.models
data class QuestionCyberData(
    val title: String,
    val firstQuest: String,
    val secondQuest: String,
    val threeQuest: String,
    val fourQuest: String,
    var userAnswer: Int = 0,
    val correctAnswer: Int
)
