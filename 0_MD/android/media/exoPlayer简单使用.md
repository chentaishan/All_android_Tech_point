# 1.简单使用

[ExoPlayer](https://exoplayer.dev/) 是一款基于 Android 中的低层级媒体 API 构建的应用级媒体播放器。与 Android 内置的 [MediaPlayer](https://developer.android.com/reference/android/media/MediaPlayer) 相比，ExoPlayer 具有多项优势。它[支持 ](https://exoplayer.dev/supported-formats.html)[MediaPlayer](https://developer.android.com/reference/android/media/MediaPlayer) 支持的许多媒体格式，还支持 DASH 和 SmoothStreaming 等自适应格式。ExoPlayer 具有高度的可定制性和可扩展性，因此能够用于许多高级用例。它是 Google 应用（包括 YouTube 和 Google Play 影视）使用的[开源项目](https://github.com/google/ExoPlayer)。

与 Android 内置的 MediaPlayer 相比，ExoPlayer 有很多优势：

更少的设备特定问题以及不同设备和 Android 版本之间的行为差异。
能够随您的应用程序一起更新播放器。 因为 ExoPlayer 是一个包含在应用程序 apk 中的库，所以您可以控制使用哪个版本，并且可以作为常规应用程序更新的一部分轻松更新到较新的版本。
自定义和扩展播放器以适合您的用例的能力。 ExoPlayer 是专门为这一点而设计的，并允许用自定义实现替换许多组件。
支持播放列表。
支持 DASH 和 SmoothStreaming，MediaPlayer 均不支持。 还支持许多其他格式。 有关详细信息，请参阅支持的格式页面。
支持高级 HLS 功能，例如正确处理#EXT-X-DISCONTINUITY 标签。
支持 Android 4.4（API 级别 19）及更高版本上的 Widevine 通用加密。
能够使用官方扩展快速与许多其他库集成。 例如，IMA 扩展可以让您使用互动媒体广告 SDK 轻松地通过您的内容获利。

重要的是要注意也有一些缺点：

对于在某些设备上仅播放音频，ExoPlayer 可能比 MediaPlayer 消耗更多的电池。 有关详细信息，请参阅电池消耗页面。
在您的应用中包含 ExoPlayer 会使 APK 大小增加几百 KB。 这可能只是极轻量级应用程序的一个问题。 收缩 ExoPlayer 的指南可以在 APK 收缩页面上找到。

https://exoplayer.dev/pros-and-cons.html

### 1.依赖

```

    implementation 'com.google.android.exoplayer:exoplayer-core:2.12.0'
    implementation 'com.google.android.exoplayer:exoplayer-dash:2.12.0'
    implementation 'com.google.android.exoplayer:exoplayer-ui:2.12.0'
```



### 2.布局

```xMl
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#000000">

    <com.google.android.exoplayer2.ui.PlayerView
        android:id="@+id/video_view"
        android:layout_width="500dp"
        android:layout_height="match_parent"
        app:show_timeout="5000" />
</FrameLayout>
```

### 3.代码

```kotlin

/**
 * A fullscreen activity to play audio or video streams.
 */
class PlayerActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer
    private val TAG = "PlayerActivity"
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }
    private val playbackStateListener: Player.EventListener = playbackStateListener()

    private fun playbackStateListener() = object : Player.EventListener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.d(TAG, "changed state to $stateString")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

    }

    private fun initializePlayer() {
//        val trackSelector = DefaultTrackSelector(this).apply {
//            setParameters(buildUponParameters().setMaxVideoSizeSd())
//        }
        player = SimpleExoPlayer.Builder(this)
//            .setTrackSelector(trackSelector)
            .build()
            .also { exoPlayer ->
                viewBinding.videoView.player = exoPlayer
                // 视频流
//                val mediaItem = MediaItem.Builder()
//                    .setUri(getString(R.string.media_url_dash))
//                    .setMimeType(MimeTypes.APPLICATION_MPD)
//                    .build()
                // mp4格式
                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4_2))
                //添加资源
                exoPlayer.addMediaItem(mediaItem)

                // mp3格式
//                val secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3));
//                exoPlayer.addMediaItem(secondMediaItem);
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                // 添加监听器
                exoPlayer.addListener(playbackStateListener)
                //预准备
                exoPlayer.prepare()
            }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer()
        }
    }

    private fun hideSystemUi() {
        viewBinding.videoView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            removeListener(playbackStateListener)
            release()
        }
//        player = null
    }
}
```



# 2.自定义播控布局

### 1.app:show_timeout="5000"

处理播控布局多长时间隐藏

### 2.自定义播控布局

自定义播控布局有两种方式，可以根据自己需求定制布局

#### a.使用app:controller_layout_id

1. 在文件夹  /res/layout/` 中创建新的布局文件 `custom_player_control_view.xml`。
2. 从布局文件夹的上下文菜单中，依次选择 **New - Layout resource file** 并将文件命名为 `custom_player_control_view.xml`。

```xml
<com.google.android.exoplayer2.ui.PlayerView
   android:id="@+id/video_view"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   app:controller_layout_id="@layout/custom_player_control_view"/>
```

PlayerView是播放的组件，但是播控的组件叫什么呢？我们可以通过controller_layout_id来引入一个布局，来定制播控布局，但是内部的各个view的id要和源码内部一致，否则会找不到组件的。

eg:   exo_play  exo_pause都是内部定义好的id

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- Copyright (C) 2016 The Android Open Source Project

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom"
    android:layoutDirection="ltr"

    android:orientation="vertical"
    tools:targetApi="28">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingTop="4dp"
        android:orientation="horizontal">


        <ImageButton android:id="@id/exo_shuffle"
            style="@style/ExoMediaButton"/>

        <ImageButton android:id="@id/exo_repeat_toggle"
            style="@style/ExoMediaButton"/>

        <ImageButton android:id="@id/exo_play"
            style="@style/ExoMediaButton.Play"/>

        <ImageButton android:id="@id/exo_pause"
            style="@style/ExoMediaButton.Pause"/>


        <ImageButton android:id="@id/exo_vr"
            style="@style/ExoMediaButton.VR"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        android:gravity="center_vertical"
        android:orientation="horizontal">

        <TextView android:id="@id/exo_position"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="14sp"
            android:textStyle="bold"
            android:paddingLeft="4dp"
            android:paddingRight="4dp"
            android:includeFontPadding="false"
            android:textColor="#FFBEBEBE"/>

        <View android:id="@id/exo_progress_placeholder"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="26dp"/>

        <TextView android:id="@id/exo_duration"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="14sp"
            android:textStyle="bold"
            android:paddingLeft="4dp"
            android:paddingRight="4dp"
            android:includeFontPadding="false"
            android:textColor="#FFBEBEBE"/>

    </LinearLayout>

</LinearLayout>
```





#### b.使用exo_player_control_view.xml

PlayerView是整体的播放view,内部包含了视频绘制view和播控view,通过源码知道为exo_player_control_view.xml就是播控view.

我们通过复写这个view.来实现定制的逻辑。

但是这样也有弊端，定制的功能数量不能大于原本的逻辑，否则需要自己想办法实现。

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom"
    android:layoutDirection="ltr"
    android:background="#CC000000"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingTop="4dp"
        android:orientation="horizontal">

        <ImageButton android:id="@id/exo_play"
            style="@style/ExoMediaButton.Play"/>

        <ImageButton android:id="@id/exo_pause"
            style="@style/ExoMediaButton.Pause"/>

    </LinearLayout>

    <ImageView
        android:contentDescription="@string/logo"
        android:src="@drawable/google_logo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

</LinearLayout>
```

# 3.[监听事件](https://developer.android.com/codelabs/exoplayer-intro#5)

实现 [`Player.EventListener`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/Player.EventListener.html) 接口。这用于通知您重要的播放器事件，其中包括错误和播放状态变化。

| **状态**                    | **说明**                                                     |
| --------------------------- | ------------------------------------------------------------ |
| `ExoPlayer.STATE_IDLE`      | 播放器已实例化，但尚未准备就绪。                             |
| `ExoPlayer.STATE_BUFFERING` | 播放器无法从当前位置开始播放，因为已缓冲的数据不足。         |
| `ExoPlayer.STATE_READY`     | 播放器可以立即从当前位置开始播放。这意味着如果播放器的 [playWhenReady](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/Player.html#getPlayWhenReady--) 属性为 `true`，播放器将自动开始播放媒体。如果该属性为 `false`，播放器会暂停播放。 |
| `ExoPlayer.STATE_ENDED`     | 播放器已完成媒体播放。                                       |

>
>
>**注意**：如何确定播放器是否真的在播放媒体？必须满足以下所有条件：
>
>- 播放状态为 `STATE_READY.`
>- `playWhenReady` 为 `true.`
>- 播放未因某些其他原因（例如，[音频焦点](https://developer.android.com/guide/topics/media-apps/audio-focus)丢失）而停止。
>
>幸运的是，ExoPlayer 专门为此提供了一个便捷方法 `ExoPlayer.isPlaying`。或者，如果您想在 `isPlaying` 发生变化时收到通知，可以监听 [`onIsPlayingChanged`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/Player.EventListener.html#onIsPlayingChanged-boolean-)。

```java
//注册监听器
exoPlayer.addListener(playbackStateListener)
//取消注册监听器   
exoPlayer.removeListener(playbackStateListener)   
```

ExoPlayer 提供了许多其他监听器，有助于您了解用户的播放体验。其中包括[音频](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/audio/AudioListener.html)和[视频](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/video/VideoListener.html)的监听器，以及 [`AnalyticsListener`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/analytics/AnalyticsListener.html)（其中包含来自所有监听器的回调）。以下是一些最重要的方法：

- 当视频的第一帧呈现时，系统会调用 [`onRenderedFirstFrame`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/analytics/AnalyticsListener.html#onRenderedFirstFrame-com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime-android.view.Surface-)。根据这项信息，您可以计算用户必须等待多长时间才能在屏幕上看到有意义的内容。
- 当视频丢帧时，系统会调用 [`onDroppedVideoFrames`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/analytics/AnalyticsListener.html#onDroppedVideoFrames-com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime-int-long-)。丢帧表示播放不流畅，且用户体验可能很差。
- 当发生音频欠载时，系统会调用 [`onAudioUnderrun`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/analytics/AnalyticsListener.html#onAudioUnderrun-com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime-int-long-long-)。欠载会导致出现声音故障，并且比视频丢帧更明显。

可以使用 [`addAnalyticsListener`](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/SimpleExoPlayer.html#addAnalyticsListener-com.google.android.exoplayer2.analytics.AnalyticsListener-) 将 `AnalyticsListener` 添加到 `player`。[音频](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/audio/AudioListener.html)和[视频](https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/video/VideoListener.html)的监听器也有对应的方法
