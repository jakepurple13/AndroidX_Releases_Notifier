package com.programmersbox.androidxreleasenotes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.programmersbox.androidxreleasenotes.ui.theme.AndroidXReleaseNotesTheme
import org.jsoup.Jsoup
import androidx.compose.material3.MaterialTheme as M3MaterialTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { M3MaterialTheme(currentColorScheme) { ReleaseNoteScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReleaseNoteScreen(vm: ReleaseNotesViewModel = viewModel()) {
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("AndroidX Release Notes") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { p ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = vm.isRefreshing),
            onRefresh = { vm.refreshItems() },
            modifier = Modifier
                .padding(p)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                vm.notes.fastForEach {
                    stickyHeader {
                        SmallTopAppBar(
                            title = { Text(it.date) },
                            scrollBehavior = scrollBehavior
                        )
                    }

                    item { ReleaseNoteItem(item = it) }
                }
            }
        }
    }

    LaunchedEffect(Unit) { vm.refreshItems() }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ReleaseNoteItem(item: ReleaseNotes) {
    val context = LocalContext.current
    ElevatedCard(
        onClick = {
            val url = item.link
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            Html.fromHtml(item.content.removePrefix("<![CDATA[ ").removeSuffix(" ]]>"), Html.FROM_HTML_MODE_COMPACT).toString(),
            style = M3MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun DefaultPreview() {

    val list = Jsoup.parse(
        """
            <feed xmlns="http://www.w3.org/2005/Atom">
            <id>tag:google.com,2016:androidx-release-notes</id>
            <title>AndroidX - Release Notes</title>
            <link rel="self" href="https://developer.android.com/feeds/androidx-release-notes.xml"/>
            <author>
            <name>Android Developers</name>
            </author>
            <updated>2022-03-23T16:17:49.121008+00:00</updated>
            <entry>
            <title>March 23, 2022</title>
            <id>tag:google.com,2016:androidx-release-notes#march_23_2022</id>
            <updated>2022-03-23T00:00:00+00:00</updated>
            <link rel="alternate" href="https://developer.android.com/jetpack/androidx/versions/all-channel#march_23_2022"/>
            <content type="html">
            <![CDATA[ <ul><li><a href="https://developer.android.com/jetpack/androidx/releases/activity#1.6.0-alpha01">Activity Version 1.6.0-alpha01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/activity#1.5.0-alpha04">Activity Version 1.5.0-alpha04</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/benchmark#1.1.0-beta05">Benchmark Version 1.1.0-beta05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/car-app#1.2.0-rc01">Car App Version 1.2.0-rc01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-animation#1.2.0-alpha06">Compose Animation Version 1.2.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-compiler#1.2.0-alpha06">Compose Compiler Version 1.2.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-foundation#1.2.0-alpha06">Compose Foundation Version 1.2.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-material#1.2.0-alpha06">Compose Material Version 1.2.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-material3#1.0.0-alpha08">Compose Material 3 Version 1.0.0-alpha08</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-runtime#1.2.0-alpha06">Compose Runtime Version 1.2.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-ui#1.2.0-alpha06">Compose UI Version 1.2.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/core#1.9.0-alpha02">Core Core-Ktx Version 1.9.0-alpha02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/core#core-1.8.0-alpha06">Core Version 1.8.0-alpha06</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/core#core-performance-1.0.0-alpha02">Core-Performance Version 1.0.0-alpha02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/core#core-splashscreen-1.0.0-beta02">Core-Splashscreen Version 1.0.0-beta02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/customview#customview-poolingcontainer-1.0.0-alpha01">CustomView-Poolingcontainer Version 1.0.0-alpha01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/draganddrop#1.0.0-beta01">Drag And Drop Version 1.0.0-beta01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/emoji2#1.2.0-alpha02">Emoji2 Version 1.2.0-alpha02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/fragment#1.5.0-alpha04">Fragment Version 1.5.0-alpha04</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/lifecycle#2.5.0-alpha05">Lifecycle Version 2.5.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/media#media-1.6.0-beta01">Media Version 1.6.0-beta01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/mediarouter#1.3.0-rc01">Mediarouter Version 1.3.0-rc01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#junit-gtest-1.0.0-alpha01">Junit-Gtest Version 1.0.0-alpha01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/textclassifier#1.0.0-alpha04">TextClassifier Version 1.0.0-alpha04</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/wear-compose#1.0.0-alpha19">Wear Compose Version 1.0.0-alpha19</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/wear-tiles#1.1.0-alpha04">Wear Tiles Version 1.1.0-alpha04</a></li></ul> ]]>
            </content>
            </entry>
            <entry>
            <title>March 21, 2022</title>
            <id>tag:google.com,2016:androidx-release-notes#march_21_2022</id>
            <updated>2022-03-21T00:00:00+00:00</updated>
            <link rel="alternate" href="https://developer.android.com/jetpack/androidx/versions/all-channel#march_21_2022"/>
            <content type="html">
            <![CDATA[ <ul><li><a href="https://developer.android.com/jetpack/androidx/releases/test#runner-1.5.0-alpha02">Test Runner Version 1.5.0-alpha02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#rules-1.4.1-alpha05">Test Rules Version 1.4.1-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#monitor-1.6.0-alpha02">Test Monitor Version 1.6.0-alpha02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#espresso-3.5.0-alpha05">Test Espresso Version 3.5.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#core-1.4.1-alpha05">Test Core Version 1.4.1-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#ext.junit-1.1.4-alpha05">Test Ext JUnit Version 1.1.4-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#ext.truth-1.5.0-alpha05">Test Ext Truth Version 1.5.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#services-1.4.2-alpha02">Test Services Version 1.4.2-alpha02</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/test#orchestrator-1.4.2-alpha02">Test Orchestrator Version 1.4.2-alpha02</a></li></ul> ]]>
            </content>
            </entry>
            <entry>
            <title>March 14, 2022</title>
            <id>tag:google.com,2016:androidx-release-notes#march_14_2022</id>
            <updated>2022-03-14T00:00:00+00:00</updated>
            <link rel="alternate" href="https://developer.android.com/jetpack/androidx/versions/all-channel#march_14_2022"/>
            <content type="html">
            <![CDATA[ <ul><li><a href="https://developer.android.com/jetpack/androidx/releases/media3#1.0.0-alpha03">Media3 Version 1.0.0-alpha03</a></li></ul> ]]>
            </content>
            </entry>
            <entry>
            <title>March 9, 2022</title>
            <id>tag:google.com,2016:androidx-release-notes#march_9_2022</id>
            <updated>2022-03-09T00:00:00+00:00</updated>
            <link rel="alternate" href="https://developer.android.com/jetpack/androidx/versions/all-channel#march_9_2022"/>
            <content type="html">
            <![CDATA[ <ul><li><a href="https://developer.android.com/jetpack/androidx/releases/compose-animation#1.2.0-alpha05">Compose Animation Version 1.2.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-compiler#1.2.0-alpha05">Compose Compiler Version 1.2.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-foundation#1.2.0-alpha05">Compose Foundation Version 1.2.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-material#1.2.0-alpha05">Compose Material Version 1.2.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-material3#1.0.0-alpha07">Compose Material 3 Version 1.0.0-alpha07</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-runtime#1.2.0-alpha05">Compose Runtime Version 1.2.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/compose-ui#1.2.0-alpha05">Compose UI Version 1.2.0-alpha05</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/games#games-memory-advice-1.0.0-beta01">Games-Memory-Advice Version 1.0.0-beta01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/lifecycle#2.5.0-alpha04">Lifecycle Version 2.5.0-alpha04</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/mediarouter#1.3.0-beta01">Mediarouter Version 1.3.0-beta01</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/paging#3.1.1">Paging Version 3.1.1</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/wear#wear-phone-interactions-1.1.0-alpha03">Wear-Phone-Interactions Version 1.1.0-alpha03</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/wear-compose#1.0.0-alpha18">Wear Compose Version 1.0.0-alpha18</a></li> <li><a href="https://developer.android.com/jetpack/androidx/releases/wear-watchface#1.1.0-alpha04">Wear Watchface Version 1.1.0-alpha04</a></li></ul> ]]>
            </content>
            </entry>
            </feed>
        """.trimIndent()
    )
        .select("entry")
        .map {
            ReleaseNotes(
                date = it.select("title").text(),
                updated = it.select("updated").text(),
                link = it.select("link").attr("href"),
                content = it.select("content").text()
            )
        }

    AndroidXReleaseNotesTheme {

        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            items(list) { ReleaseNoteItem(item = it) }
        }

    }
}