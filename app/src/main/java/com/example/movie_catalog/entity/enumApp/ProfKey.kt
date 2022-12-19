package com.example.movie_catalog.entity.enumApp

import com.example.movie_catalog.App
import com.example.movie_catalog.R

enum class ProfKey( var nameView: String) {
    ACTOR ( App.context.getString(R.string.ACTOR)),
    HERSELF ( App.context.getString(R.string.HERSELF)),
    HIMSELF ( App.context.getString(R.string.HIMSELF)),
    PRODUCER ( App.context.getString(R.string.PRODUCER)),
    DIRECTOR ( App.context.getString(R.string.DIRECTOR)),
    WRITER ( App.context.getString(R.string.WRITER)),
    OPERATOR ( App.context.getString(R.string.OPERATOR)),
    DESIGN ( App.context.getString(R.string.DESIGN)),
    EDITOR ( App.context.getString(R.string.EDITOR)),
    COMPOSER ( App.context.getString(R.string.COMPOSER)),
    VOICE_DIRECTOR (App.context.getString(R.string.VOICE_DIRECTOR)),
    HRONO_TITR_MALE (App.context.getString(R.string.HRONO_TITR_MALE)),
    VOICE_MALE (App.context.getString(R.string.VOICE_MALE)),
    VOICE_FEMALE(App.context.getString(R.string.VOICE_FEMALE)),
    HRONO_TITR_FEMALE(App.context.getString(R.string.HRONO_TITR_FEMALE)),
}