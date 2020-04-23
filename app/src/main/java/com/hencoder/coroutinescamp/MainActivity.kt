package com.hencoder.coroutinescamp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hencoder.coroutinescamp.model.Repo
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

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
    }*/

    /*thread {
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
                ioCode3()
              }
            }
          }
        }
      }
    }*/

    GlobalScope.launch(Dispatchers.Main) {
      ioCode1()
      uiCode1()
      ioCode2()
      uiCode2()
      ioCode3()
      uiCode3()
    }

    thread {
      println("Coroutines Camp classic 1 ${Thread.currentThread().name}")
      classicIoCode1(block = ::uiCode1)
      println("Coroutines Camp classic 3 ${Thread.currentThread().name}")
    }

    ioCode1()
  }

  private fun classicIoCode1(toUiThread: Boolean = true, block: () -> Unit) {
    val executor = ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, LinkedBlockingQueue(1000))
    executor.execute {
      Thread.sleep(1000)
      println("Coroutines Camp classic io1 ${Thread.currentThread().name}")
      if (toUiThread) {
        runOnUiThread {
          block()
        }
      } else {
        block()
      }
    }
  }

  private suspend fun ioCode1() {
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
      println("Coroutines Camp io1 ${Thread.currentThread().name}")
    }
  }

  private suspend fun ioCode2() {
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
      println("Coroutines Camp io2 ${Thread.currentThread().name}")
    }
  }

  private suspend fun ioCode3() {
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
      println("Coroutines Camp io3 ${Thread.currentThread().name}")
    }
  }

  private fun uiCode1() {
    println("Coroutines Camp ui1 ${Thread.currentThread().name}")
  }

  private fun uiCode2() {
    println("Coroutines Camp ui2 ${Thread.currentThread().name}")
  }

  private fun uiCode3() {
    println("Coroutines Camp ui3 ${Thread.currentThread().name}")
  }
}