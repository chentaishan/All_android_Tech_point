# 1.mediaPlayer

MediaPlayer class can be used to control playback of audio/video files and streams.

MediaPlayer is not thread-safe. Creation of and all access to player instances should be on the same thread. If registering [callbacks](https://developer.android.com/reference/android/media/MediaPlayer#Callbacks), the thread must have a Looper.

Topics covered here are:

1. [State Diagram](https://developer.android.com/reference/android/media/MediaPlayer#StateDiagram)
2. [Valid and Invalid States](https://developer.android.com/reference/android/media/MediaPlayer#Valid_and_Invalid_States)
3. [Permissions](https://developer.android.com/reference/android/media/MediaPlayer#Permissions)
4. [Register informational and error callbacks](https://developer.android.com/reference/android/media/MediaPlayer#Callbacks)





## 1.1 State Diagram

Playback control of audio/video files and streams is managed as a state machine. The following diagram shows the life cycle and the states of a MediaPlayer object driven by the supported playback control operations. The ovals represent the states a MediaPlayer object may reside in. The arcs represent the playback control operations that drive the object state transition. There are two types of arcs. The arcs with a single arrow head represent synchronous method calls, while those with a double arrow head represent asynchronous method calls.

>
>
>音视频文件和流的播放控制被当做状态流程来管理。下图显示了由支持的播放控制操作驱动的 MediaPlayer 对象的生命周期和状态。
>
>椭圆表示 MediaPlayer 对象可能存在的状态。箭头表示驱动对象状态转换的播放控制操作
>
>有两种类型的箭头。带有单箭头的圆弧表示同步方法调用，而带有双箭头的圆弧表示异步方法调用。

![MediaPlayer State diagram](.\mediaplayer_state_diagram.gif)

From this state diagram, one can see that a MediaPlayer object has the following states:

- When a MediaPlayer object is just created using `new` or after `reset()` is called, it is in the *Idle* state; and after `release()` is called, it is in the *End* state. Between these two states is the life cycle of the MediaPlayer object.

  - There is a subtle but important difference between a newly constructed MediaPlayer object and the MediaPlayer object after `reset()` is called. It is a programming error to invoke methods such as `getCurrentPosition()`, `getDuration()`, `getVideoHeight()`, `getVideoWidth()`, `setAudioAttributes(android.media.AudioAttributes)`, `setLooping(boolean)`, `setVolume(float, float)`, `pause()`, `start()`, `stop()`, `seekTo(long, int)`, `prepare()` or `prepareAsync()` in the *Idle* state for both cases. If any of these methods is called right after a MediaPlayer object is constructed, the user supplied callback method OnErrorListener.onError() won't be called by the internal player engine and the object state remains unchanged; but if these methods are called right after `reset()`, the user supplied callback method OnErrorListener.onError() will be invoked by the internal player engine and the object will be transfered to the *Error* state.

  - You must call `release()` once you have finished using an instance to release acquired resources, such as memory and codecs. Once you have called `release()`, you must no longer interact with the released instance.

  - MediaPlayer objects created using `new` is in the *Idle* state, while those created with one of the overloaded convenient `create` methods are *NOT* in the *Idle* state. In fact, the objects are in the *Prepared* state if the creation using `create` method is successful.

    >当MediaPlayer对象刚用new创建或reset()调用后，处于Idle状态；调用 release() 后，处于 End 状态。在这两种状态之间是 MediaPlayer 对象的生命周期。
    >
    >新构造的 MediaPlayer 对象与调用 reset() 后的 MediaPlayer 对象之间存在细微但重要的区别。调用getCurrentPosition()、getDuration()、getVideoHeight()、getVideoWidth()、setAudioAttributes(android.media.AudioAttributes)、setLooping(boolean)、setVolume(float, float)、pause() , start(), stop(), seekTo(long, int), prepare() 或 prepareAsync() 等方法在这两种空闲状态情况下属于编程错误。 `如果在构造 MediaPlayer 对象后立即调用这些方法中的任何一个，则内部播放器引擎不会调用用户提供的回调方法 OnErrorListener.onError() 并且对象状态保持不变； 但如果这些方法在 reset() 之后立即调用，则用户提供的回调方法 OnErrorListener.onError() 将由内部播放器引擎调用，并且对象将转移到 Error 状态`。
    >
    >您必须在使用完实例后调用 release() 来释放获取的资源，例如内存和编解码器。一旦调用了 release()，就不能再与已释放的实例进行交互
    >
    >使用 new 创建的 MediaPlayer 对象处于空闲状态，而使用一种重载的便捷创建方法创建的对象则不处于空闲状态。 实际上，如果使用 create 方法创建成功，则对象处于 Prepared 状态。

- In general, some playback control operation may fail due to various reasons, such as unsupported audio/video format, poorly interleaved audio/video, resolution too high, streaming timeout, and the like. Thus, error reporting and recovery is an important concern under these circumstances. Sometimes, due to programming errors, invoking a playback control operation in an invalid state may also occur. Under all these error conditions, the internal player engine invokes a user supplied OnErrorListener.onError() method if an OnErrorListener has been registered beforehand via setOnErrorListener(android.media.MediaPlayer.OnErrorListener)
  
  - It is important to note that once an error occurs, the MediaPlayer object enters the *Error* state (except as noted above), even if an error listener has not been registered by the application.
  
  - In order to reuse a MediaPlayer object that is in the *Error* state and recover from the error, `reset()` can be called to restore the object to its *Idle* state.
  
  - It is good programming practice to have your application register a OnErrorListener to look out for error notifications from the internal player engine.
  
  - IllegalStateException is thrown to prevent programming errors such as calling `prepare()`, `prepareAsync()`, or one of the overloaded `setDataSource `methods in an invalid state.
  
    >一般情况下，某些播放控制操作可能会由于各种原因而失败，例如不支持的音频/视频格式、音频/视频交织不良、分辨率太高、流媒体超时等。 因此，在这些情况下，错误报告和恢复是一个重要的问题。 有时，由于编程错误，也可能出现在无效状态下调用播放控制操作。 在所有这些错误情况下，如果预先通过 setOnErrorListener(android.media.MediaPlayer.OnErrorListener) 注册了 OnErrorListener，则内部播放器引擎会调用用户提供的 OnErrorListener.onError() 方法
    >
    >需要注意的是，一旦发生错误，MediaPlayer 对象就会进入 Error 状态（除上述情况外），即使应用程序尚未注册错误侦听器也是如此
    >
    >为了重用处于 Error 状态的 MediaPlayer 对象并从错误中恢复，可以调用 reset() 将对象恢复到 Idle 状态
    >
    >让您的应用程序注册一个 OnErrorListener 以查找来自内部播放器引擎的错误通知是一种很好的编程习惯。
    >
    >抛出 IllegalStateException 是为了防止编程错误，例如调用 prepare()、prepareAsync() 或处于无效状态的重载 setDataSource 方法之一。
  
- Calling `setDataSource(java.io.FileDescriptor), or setDataSource(java.lang.String), or setDataSource(android.content.Context, android.net.Uri), or setDataSource(java.io.FileDescriptor, long, long), or setDataSource(android.media.MediaDataSource)` transfers a MediaPlayer object in the Idle state to the Initialized state.
An IllegalStateException is thrown if setDataSource() is called in any other state.
  It is good programming practice to always look out for IllegalArgumentException and IOException that may be thrown from the overloaded setDataSource methods.

  >调用setDataSource方法转换mediaplayer对象闲置状态到初始化状态
  >
  >如果调用setDataSource方法，在任何状态下都应该抛出IllegalStateException 
>
  >在重载setDataSource 的时候，一个好的开发习惯是要注意IllegalArgumentException 和IOException 可能会抛出。
>
  >

- A MediaPlayer object must first enter the *Prepared* state before playback can be started.

  - There are two ways (synchronous vs. asynchronous) that the *Prepared* state can be reached: either a call to `prepare()` (synchronous) which transfers the object to the *Prepared* state once the method call returns, or a call to `prepareAsync()` (asynchronous) which first transfers the object to the *Preparing* state after the call returns (which occurs almost right away) while the internal player engine continues working on the rest of preparation work until the preparation work completes. When the preparation completes or when `prepare()` call returns, the internal player engine then calls a user supplied callback method, onPrepared() of the OnPreparedListener interface, if an OnPreparedListener is registered beforehand via `setOnPreparedListener(android.media.MediaPlayer.OnPreparedListener)`.

  - It is important to note that the *Preparing* state is a transient state, and the behavior of calling any method with side effect while a MediaPlayer object is in the *Preparing* state is undefined.

  - An IllegalStateException is thrown if `prepare()` or `prepareAsync()` is called in any other state.

  - While in the *Prepared* state, properties such as audio/sound volume, screenOnWhilePlaying, looping can be adjusted by invoking the corresponding set methods

    >MediaPlayer 对象必须先进入 Prepared 状态才能开始播放
    >
    >一共两种形式可以触发prepared状态，一个prepared，是同步方法，一个是prepareAsync()，异步方法，可以在setOnPreparedListener接口接收到响应的结果。
    >
    >重要的是要注意 Preparing 状态是一个瞬态，当 MediaPlayer 对象处于 Preparing 状态时调用任何具有副作用的方法的行为是未定义的。
    >
    >如果在任何其他状态下调用 prepare() 或 prepareAsync() 则会抛出 IllegalStateException
    >
    >在Prepared状态下，可以通过调用相应的set方法来调整音频/音量、screenOnWhilePlaying、looping等属性。

- To start the playback, `start()` must be called. After `start()` returns successfully, the MediaPlayer object is in the *Started* state. `isPlaying()` can be called to test whether the MediaPlayer object is in the *Started* state.
  
    - While in the *Started* state, the internal player engine calls a user supplied OnBufferingUpdateListener.onBufferingUpdate() callback method if a OnBufferingUpdateListener has been registered beforehand via `setOnBufferingUpdateListener(android.media.MediaPlayer.OnBufferingUpdateListener)`. This callback allows applications to keep track of the buffering status while streaming audio/video.
  
    - Calling `start()` has no effect on a MediaPlayer object that is already in the *Started* state.
  
      >要开始播放，必须调用 start()。 start()返回成功后，MediaPlayer对象处于Started状态。可以调用 isPlaying() 来测试 MediaPlayer 对象是否处于 Started 状态。
      >
      >在 Started 状态下，如果 OnBufferingUpdateListener 已事先通过 setOnBufferingUpdateListener(android.media.MediaPlayer.OnBufferingUpdateListener) 注册，则内部播放器引擎会调用用户提供的 OnBufferingUpdateListener.onBufferingUpdate() 回调方法。 此回调允许应用程序在流式传输音频/视频时跟踪缓冲状态。
      >
      >调用 start() 对已经处于 Started 状态的 MediaPlayer 对象没有影响
  

-  Playback can be paused and stopped, and the current playback position can be adjusted. Playback can be paused via `pause()`. When the call to `pause()` returns, the MediaPlayer object enters the *Paused* state. Note that the transition from the *Started* state to the *Paused* state and vice versa happens asynchronously in the player engine. It may take some time before the state is updated in calls to `isPlaying()`, and it can be a number of seconds in the case of streamed content.

  - Calling `start()` to resume playback for a paused MediaPlayer object, and the resumed playback position is the same as where it was paused. When the call to `start()` returns, the paused MediaPlayer object goes back to the *Started* state.

  - Calling `pause()` has no effect on a MediaPlayer object that is already in the *Paused* state.
  
    >可以暂停和停止播放，可以调整当前播放位置。 可以通过 pause() 暂停播放。 当对 pause() 的调用返回时，MediaPlayer 对象进入暂停状态。 请注意，从 Started 状态到 Paused 状态的转换在播放器引擎中异步发生，反之亦然。 在对 isPlaying() 的调用中更新状态可能需要一些时间，对于流式内容可能需要几秒。
  >
    >调用 start() 恢复播放暂停的 MediaPlayer 对象，恢复播放位置与暂停位置相同。当对 start() 的调用返回时，暂停的 MediaPlayer 对象返回到 Started 状态
  >
    >调用 pause() 对已经处于暂停状态的 MediaPlayer 对象没有影响。

- Calling `stop()` stops playback and causes a MediaPlayer in the *Started*, *Paused*, *Prepared* or *PlaybackCompleted* state to enter the *Stopped* state.

  - Once in the *Stopped* state, playback cannot be started until `prepare()` or `prepareAsync()` are called to set the MediaPlayer object to the *Prepared* state again.

  - Calling `stop()` has no effect on a MediaPlayer object that is already in the *Stopped* state.
  
    >调用 stop() 停止播放并导致处于 Started、Paused、Prepared 或 PlaybackCompleted 状态的 MediaPlayer 进入 Stopped 状态。
  >
    >一旦处于 Stopped 状态，就无法开始播放，直到再次调用 prepare() 或 prepareAsync() 将 MediaPlayer 对象设置为 Prepared 状态。
  >
    >调用 stop() 对已经处于 Stopped 状态的 MediaPlayer 对象没有影响。
  >
    >

- The playback position can be adjusted with a call to `seekTo(long, int)`.

  - Although the asynchronuous `seekTo(long, int)` call returns right away, the actual seek operation may take a while to finish, especially for audio/video being streamed. When the actual seek operation completes, the internal player engine calls a user supplied OnSeekComplete.onSeekComplete() if an OnSeekCompleteListener has been registered beforehand via `setOnSeekCompleteListener(android.media.MediaPlayer.OnSeekCompleteListener)`.

  - Please note that `seekTo(long, int)` can also be called in the other states, such as *Prepared*, *Paused* and *PlaybackCompleted* state. When `seekTo(long, int)` is called in those states, one video frame will be displayed if the stream has video and the requested position is valid.

  - Furthermore, the actual current playback position can be retrieved with a call to `getCurrentPosition()`, which is helpful for applications such as a Music player that need to keep track of the playback progress.

    >可以通过调用 seekTo(long, int) 来调整播放位置
    >
    >尽管异步 seekTo(long, int) 调用会立即返回，但实际的查找操作可能需要一段时间才能完成，尤其是对于正在流式传输的音频/视频。 当实际的搜索操作完成时，如果预先通过 setOnSeekCompleteListener(android.media.MediaPlayer.OnSeekCompleteListener) 注册了 OnSeekCompleteListener，则内部播放器引擎会调用用户提供的 OnSeekComplete.onSeekComplete()。
    >
    >请注意，seekTo(long, int) 也可以在其他状态下调用，例如 Prepared、Paused 和 PlaybackCompleted 状态。 当在这些状态下调用 seekTo(long, int) 时，如果流中有视频并且请求的位置有效，将显示一个视频帧。
    >
    >此外，可以通过调用 getCurrentPosition() 来检索实际的当前播放位置，这对于需要跟踪播放进度的应用程序（例如音乐播放器）很有帮助。

- When the playback reaches the end of stream, the playback completes.

  - If the looping mode was being set to true with `setLooping(boolean)`, the MediaPlayer object shall remain in the *Started* state.
  - If the looping mode was set to false , the player engine calls a user supplied callback method, OnCompletion.onCompletion(), if a OnCompletionListener is registered beforehand via `setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener)`. The invoke of the callback signals that the object is now in the *PlaybackCompleted* state.
  - While in the *PlaybackCompleted* state, calling `start()` can restart the playback from the beginning of the audio/video source.

>当播放到流的末尾时，播放完成
>
>如果使用 setLooping(boolean) 将循环模式设置为 true，则 MediaPlayer 对象应保持 Started 状态。
>
>如果循环模式设置为 false ，播放器引擎调用用户提供的回调方法 OnCompletion.onCompletion()，前提是 OnCompletionListener 是通过 setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener) 预先注册的。 调用回调表示对象现在处于 PlaybackCompleted 状态。
>
>在 PlaybackCompleted 状态下，调用 start() 可以从音视频源的开头重新开始播放。

## 1.2 Valid and invalid states

| Method Name | Valid States | Invalid States | Comments |
| ----------- | ------------ | -------------- | -------- |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |
|             |              |                |          |



## 1.3 Permissions





## 1.4 Callbacks

