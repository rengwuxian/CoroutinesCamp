package com.hencoder.coroutinescamp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hencoder.coroutinescamp.arch.RengViewModel
import com.hencoder.coroutinescamp.model.Repo
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /*GlobalScope.launch {
      println("Coroutines Camp 1 ${Thread.currentThread().name}")
    }

    Thread {
      println("Coroutines Camp 2 ${Thread.currentThread().name}")
    }.start()

    thread {
      println("Coroutines Camp 3 ${Thread.currentThread().name}")
    }

    thread {
      ioCode1()
      runOnUiThread {
        uiCode1()
        thread {
          ioCode2()
          runOnUiThread {
            uiCode2()
            thread {
              ioCode3()
              runOnUiThread {
                uiCode3()
              }
            }
          }
        }
      }
    }*/

    GlobalScope.launch(Dispatchers.Main) {
      ioCode1()
      ioCode1()
      uiCode1()
      ioCode2()
      uiCode2()
      ioCode3()
      uiCode3()
    }

    classicIoCode1(false) {
      classicIoCode1 {
        uiCode1()
      }
    }

    ioCode1()
    uiCode1()
  }

  suspend fun ioCode1() {
    withContext(Dispatchers.IO) {
      println("Coroutines Camp io1 ${Thread.currentThread().name}")
    }
  }

  fun uiCode1() {
    println("Coroutines Camp ui1 ${Thread.currentThread().name}")
  }

  suspend fun ioCode2() {
    withContext(Dispatchers.IO) {
      println("Coroutines Camp io2 ${Thread.currentThread().name}")
    }
  }

  fun uiCode2() {
    println("Coroutines Camp ui2 ${Thread.currentThread().name}")
  }

  suspend fun ioCode3() {
    withContext(Dispatchers.IO) {
      println("Coroutines Camp io3 ${Thread.currentThread().name}")
    }
  }

  fun uiCode3() {
    println("Coroutines Camp ui3 ${Thread.currentThread().name}")
  }

  private val executor = ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, LinkedBlockingDeque())

  private fun classicIoCode1(uiThread: Boolean = true, block: () -> Unit) {
    executor.execute {
      println("hahahaha classic io1 ${Thread.currentThread().name}")
      if (uiThread) {
        runOnUiThread {
          block()
        }
      } else {
        block()
      }
    }
  }
}