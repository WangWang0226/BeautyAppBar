package com.example.test1

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    val adapter = mAdapter()
    var mStateBarHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = adapter

        //從android的dimen檔取得狀態列高度，單位為pixel
        //此方法跟view生成與否無關，不會因為view還沒建立而拿到高度為0，穩定可靠
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)
        mStateBarHeight = statusBarHeight

        //因為xml裡面我們自定義的標題列topConstrainLayout的位置是跟著guildeline跑的，所以這裡要把guildeline位置設為與statusBar下緣相同
        m_guideline.setGuidelineBegin(mStateBarHeight)

        nestedScrollView.post(Runnable() {
            nestedScrollView.fling(0);
            nestedScrollView.smoothScrollTo(0, 0);
        })
        setFullScreen()
        appbarSetting()


    }


    private fun appbarSetting() {
           //statusBar設為30%透明，黑色
           window?.statusBarColor = Color.parseColor("#4D000000")
           //標題列設定 上緣為30%透明黑色，下緣為全透明，上至下漸層
           topConstraintLayout.setBackgroundResource(R.drawable.shape_banner_gradient)

           //滑動監聽器
           nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

               if (scrollY == 0) {

                   window?.statusBarColor = Color.parseColor("#4D000000")
                   topConstraintLayout.setBackgroundResource(R.drawable.shape_banner_gradient)

               } else if (scrollY <= bannerConstraintLayout.height - topConstraintLayout.height - mStateBarHeight) {
                   //用float單位去計算精準度較高，最後算完再轉為int即可
                   val scale = scrollY * 1F / bannerConstraintLayout.height
                   val alpha = (255 * scale).toInt()
                   topConstraintLayout.setBackgroundColor(Color.argb(alpha, 255, 255, 255))

                   searchImageView.isHovered = false
                   tvTitle.setTextColor(resources.getColor(R.color.colorFFFFFF))
                   window?.statusBarColor = Color.argb(alpha, 223, 223, 223)

               } else if (scrollY > bannerConstraintLayout.height - topConstraintLayout.height - mStateBarHeight) {
                   topConstraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))

                   searchImageView.isHovered = true
                   tvTitle.setTextColor(resources.getColor(R.color.color000000))
                   window?.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
               }
           }
    }

    private fun setFullScreen() {

        //若SDK版本>=23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) // 確認取消半透明設置。
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_VISIBLE or
                //將布局內容延伸至螢幕頂端並放到statusBar後面
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                //保持布局與statusBar穩定
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) // 跟系統表示要渲染 system bar 背景。
    }
}