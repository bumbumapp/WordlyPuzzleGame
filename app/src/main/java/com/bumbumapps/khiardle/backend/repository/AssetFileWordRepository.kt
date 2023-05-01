package com.bumbumapps.khiardle.backend.repository

import android.content.res.AssetManager
import com.bumbumapps.khiardle.backend.models.Languages
import com.bumbumapps.khiardle.backend.models.Word

class AssetFileWordRepository(assetManager: AssetManager,languages: Languages) : WordRepository {
     private var languages: Languages
    init {
        this.languages=languages
    }
    private val allWords =
        assetManager.open("words.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val wordsForLevels =
        assetManager.open("top.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()
    private val german_allwords =
        assetManager.open("German-long.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val german_wordsForLevels =
        assetManager.open("German.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()
    private val french_allWords =
        assetManager.open("French-long.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val french_wordsForLevels =
        assetManager.open("French.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()

  private val dutch_allWords =
        assetManager.open("Dutch.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val dutch_wordsForLevels =
        assetManager.open("Dutch.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()


  private val catalan_allWords =
        assetManager.open("Catalan.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val catalan_wordsForLevels =
        assetManager.open("Catalan.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()

private val italian_allWords =
        assetManager.open("Italian-long.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val italian_wordsForLevels =
        assetManager.open("Italian.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()

private val spanish_allWords =
        assetManager.open("Spanish-long.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val spanish_wordsForLevels =
        assetManager.open("Spanish.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()

private val portuguese_allWords =
        assetManager.open("Portuguese-long.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val portuguese_wordsForLevels =
        assetManager.open("Portuguese.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()
private val turkish_allWords =
        assetManager.open("Turkish.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val turkish_wordsForLevels =
        assetManager.open("Turkish.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()


    override fun find(word: Word): Boolean {
        val language=languages.getString()
        return if (language.equals("German"))
            german_allwords.contains(word.word.uppercase().trim())
        else if (language.equals("French"))
            french_allWords.contains(word.word.uppercase().trim())
        else if (language.equals("Dutch"))
            dutch_allWords.contains(word.word.uppercase().trim())
        else if (language.equals("Italian"))
            italian_allWords.contains(word.word.uppercase().trim())
        else if (language.equals("Catalan"))
            catalan_allWords.contains(word.word.uppercase().trim())
        else if (language.equals("Spanish"))
            spanish_allWords.contains(word.word.uppercase().trim())
        else if (language.equals("Portuguese"))
            portuguese_allWords.contains(word.word.uppercase().trim())
        else if (language.equals("Turkish"))
            turkish_allWords.contains(word.word.uppercase().trim())
        else
            allWords.contains(word.word.uppercase().trim())

    }

    override fun random(): Word {
        val language=languages.getString()
        return if (language.equals("German"))
            Word(german_allwords.random())
        else if(language.equals("French"))
            Word(french_allWords.random())
        else if(language.equals("Dutch"))
            Word(dutch_allWords.random())
        else if(language.equals("Italian"))
            Word(italian_allWords.random())
        else if(language.equals("Catalan"))
            Word(catalan_allWords.random())
        else if(language.equals("Spanish"))
            Word(spanish_allWords.random())
        else if(language.equals("Portuguese"))
            Word(portuguese_allWords.random())
        else if(language.equals("Turkish"))
            Word(turkish_allWords.random())
        else
            Word(allWords.random())
    }

    override fun getWordForLevel(currentLevelNumber: Long): Word {
        val language=languages.getString()
        return if (language.equals("German"))
            Word(german_wordsForLevels.random())
        else if(language.equals("French"))
            Word(french_wordsForLevels.random())
        else if(language.equals("Dutch"))
            Word(dutch_wordsForLevels.random())
        else if(language.equals("Italian"))
            Word(italian_wordsForLevels.random())
        else if(language.equals("Catalan"))
            Word(catalan_wordsForLevels.random())
        else if(language.equals("Spanish"))
            Word(spanish_wordsForLevels.random())
        else if(language.equals("Portuguese"))
            Word(portuguese_wordsForLevels.random())
        else if(language.equals("Turkish"))
            Word(turkish_wordsForLevels.random())
        else
            Word(wordsForLevels.random())
    }

    override val lastLevel: Long
        get() = 1024
}