package com.examsample.common

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : AppCompatActivity() {

    lateinit var binding: B
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
    }

    fun showToast(msg: String?) = Toast.makeText(
        this,
        msg,
        Toast.LENGTH_SHORT
    ).show()

    override fun onDestroy() {
        compositeDisposable.clear()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}