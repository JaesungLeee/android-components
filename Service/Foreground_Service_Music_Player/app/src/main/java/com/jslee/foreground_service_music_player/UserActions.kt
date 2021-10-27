package com.jslee.foreground_service_music_player

object UserActions {
    private const val prefix = "com.jslee.foreground_service_music_player.action."
    const val MAIN = prefix + "main"
    const val PREV = prefix + "prev"
    const val NEXT = prefix + "next"
    const val PLAY = prefix + "play"
    const val START_FOREGROUND = prefix + "startforeground"
    const val STOP_FOREGROUND = prefix + "stopforeground"
}