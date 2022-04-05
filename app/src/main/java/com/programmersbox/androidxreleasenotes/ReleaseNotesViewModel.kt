package com.programmersbox.androidxreleasenotes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ReleaseNotesViewModel : ViewModel() {

    val notes = mutableStateListOf<ReleaseNotes>()

    var isRefreshing by mutableStateOf(false)
        private set

    fun refreshItems() {
        viewModelScope.launch(Dispatchers.IO) {
            isRefreshing = true
            notes.clear()
            notes.addAll(
                Jsoup.connect("https://developer.android.com/feeds/androidx-release-notes.xml").get()
                    .select("entry")
                    .map {
                        ReleaseNotes(
                            date = it.select("title").text(),
                            updated = it.select("updated").text(),
                            link = it.select("link").attr("href"),
                            content = it.select("content").text()
                        )
                    }
            )
            isRefreshing = false
        }
    }

}

data class ReleaseNotes(val date: String, val updated: String, val link: String, val content: String)