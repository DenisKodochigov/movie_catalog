package com.example.movie_catalog.entity.enumApp

import androidx.annotation.StringRes
import com.example.movie_catalog.R

enum class ProfKey(@StringRes var nameView: Int) {
    ACTOR ( R.string.ACTOR),
    HERSELF ( R.string.HERSELF),
    HIMSELF ( R.string.HIMSELF),
    PRODUCER ( R.string.PRODUCER),
    DIRECTOR ( R.string.DIRECTOR),
    WRITER ( R.string.WRITER),
    OPERATOR ( R.string.OPERATOR),
    DESIGN ( R.string.DESIGN),
    EDITOR ( R.string.EDITOR),
    COMPOSER ( R.string.COMPOSER),
    VOICE_DIRECTOR (R.string.VOICE_DIRECTOR),
    HRONO_TITR_MALE (R.string.HRONO_TITR_MALE),
    VOICE_MALE (R.string.VOICE_MALE),
    VOICE_FEMALE(R.string.VOICE_FEMALE),
    HRONO_TITR_FEMALE(R.string.HRONO_TITR_FEMALE),
}