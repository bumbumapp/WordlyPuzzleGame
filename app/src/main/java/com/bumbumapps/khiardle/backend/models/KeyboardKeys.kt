package com.bumbumapps.khiardle.backend.models

import androidx.annotation.Keep
import com.bumbumapps.khiardle.backend.models.KeyboardKeys.Key.Companion.englishKeys
import com.bumbumapps.khiardle.backend.models.KeyboardKeys.Key.Companion.turkishKeys


enum class Language(val keys: List<Char>) {
    English(englishKeys),
    Turkish(turkishKeys)

}


abstract class KeyboardKeys(
    open var keys: List<Key>,
    val language: Language,
) {

    abstract fun withUpdatedButton(keys: List<Key>): KeyboardKeys
    data class Key(
        val button: Char,
        val equalityStatus: EqualityStatus?,
    ) {
        val enabled = equalityStatus != EqualityStatus.Incorrect

        @Keep
        companion object {
            val turkishKeys= listOf(
                'Ç',
                'Ü',
                'E',
                'R',
                'T',
                'Y',
                'U',
                'I',
                'O',
                'P',
                'A',
                'S',
                'D',
                'F',
                'G',
                'H',
                'İ',
                'K',
                'L',
                'Z',
                'Ş',
                'C',
                'V',
                'B',
                'N',
                'M'
            )
            val englishKeys = listOf(
                'Q',
                'W',
                'E',
                'R',
                'T',
                'Y',
                'U',
                'I',
                'O',
                'P',
                'A',
                'S',
                'D',
                'F',
                'G',
                'H',
                'J',
                'K',
                'L',
                'Z',
                'X',
                'C',
                'V',
                'B',
                'N',
                'M')
        }
    }

    data class English(
        override var keys: List<Key> = englishKeys.map {
            Key(it, null)
        }.toList(),


        ) : KeyboardKeys(keys, Language.English) {
        override fun withUpdatedButton(keys: List<Key>): KeyboardKeys {
            return copy(keys = keys)
        }
    }
    data class Turkish(
        override var keys: List<Key> = turkishKeys.map {
            Key(it, null)
        }.toList(),


        ) : KeyboardKeys(keys, Language.Turkish) {
        override fun withUpdatedButton(keys: List<Key>): KeyboardKeys {
            return copy(keys = keys)
        }
    }
}