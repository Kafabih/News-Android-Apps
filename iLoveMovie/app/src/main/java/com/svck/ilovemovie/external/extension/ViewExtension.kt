package com.svck.ilovemovie.external.extension

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.svck.ilovemovie.BuildConfig
import com.svck.ilovemovie.R
import com.svck.ilovemovie.domain.adapter.GeneralAdapter
import java.io.File

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun View.setVisibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

/**
 * Helper function to detect scroll / idle state of scrollview
 */
@SuppressLint("ClickableViewAccessibility")
inline fun NestedScrollView.scrollState(
    crossinline onIdle: () -> Unit,
    crossinline onScrolled: () -> Unit
) {
    val handler = Handler()
    var isScrolled = false
    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_SCROLL,
            MotionEvent.ACTION_DOWN -> {
                handler.removeCallbacksAndMessages(null)
                onScrolled()
                isScrolled = true
            }
            MotionEvent.ACTION_MOVE -> {
                handler.removeCallbacksAndMessages(null)
                if (!isScrolled) {
                    onScrolled()
                    isScrolled = true
                }
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                handler.postDelayed({ onIdle() }, 500)
                isScrolled = false
            }
        }
        false
    }
}

fun String.toUnderline(inputString: String = ""): SpannableString {
    val spannable = SpannableString(this)

    if(inputString.isNotEmpty()) {
        val startIndex = this.indexOf(inputString, 0)

        if(startIndex >= 0) {
            spannable.setSpan(UnderlineSpan(), startIndex, inputString.length, 0)
        } else {
            spannable.setSpan(UnderlineSpan(), 0, this.length, 0)
        }

    } else {
        spannable.setSpan(UnderlineSpan(), 0, this.length, 0)
    }

    return spannable
}

private val shimmer = Shimmer.AlphaHighlightBuilder()
    .setDuration(1800)
    .setBaseAlpha(0.7f)
    .setHighlightAlpha(0.6f)
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setAutoStart(true)
    .build()

val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(shimmer)
}

fun ImageView.loadImageFromResource(@DrawableRes resource: Int) {
    try {
        Glide
            .with(this)
            .load(resource)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_noimage).fitCenter())
            .into(this)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

fun ImageView.loadBitmapImage(image: Bitmap?) {
    try {
        Glide
            .with(this)
            .asBitmap()
            .load(image)
            .placeholder(shimmerDrawable)
            .into(this)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

fun ImageView.loadImageFromFile(image: File) {
    try {
        Glide
            .with(this)
            .asBitmap()
            .load(image)
            .placeholder(shimmerDrawable)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_noimage).fitCenter())
            .into(this)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

fun ImageView.loadGifFromResource(@DrawableRes resource: Int) {
    try {
        Glide
            .with(this)
            .asGif()
            .load(resource)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_noimage).fitCenter())
            .into(this)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

fun EditText.hideSoftKeyboard(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showSoftKeyboard(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.SHOW_IMPLICIT)
}


fun ImageView.loadImageFromUrl(url: String?) {
    try {
        Glide.with(this).load(url).apply(
            RequestOptions.placeholderOf(R.drawable.ic_noimage)
                .fitCenter()
        ).listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImageFromUrl.setImageDrawable(resource)
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImageFromUrl.setImageResource(R.drawable.ic_noimage)
                return false
            }
        }).into(this)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

fun ImageView.loadImageFromUrlWithLoading(url: String?, loadingView: View?, placeholder: Int = R.drawable.ic_noimage) {
    try {
        Glide.with(this).load(BuildConfig.IMAGE_URL + url).apply(
            RequestOptions.placeholderOf(placeholder)
                .fitCenter()
        ).listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadingView?.setVisibleIf(false)
                this@loadImageFromUrlWithLoading.setImageDrawable(resource)
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadingView?.setVisibleIf(false)
                return false
            }
        }).into(this)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

inline fun <T : ViewBinding> ViewGroup.viewBinding(binding: (LayoutInflater, ViewGroup, Boolean) -> T): T {
    return binding(LayoutInflater.from(context), this, false)
}

fun <T : ViewBinding, ITEM> RecyclerView.setup(
    items: List<ITEM>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
    bindHolder: View.(T?, ITEM) -> Unit,
    itemClick: View.(ITEM) -> Unit = {},
    manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
): GeneralAdapter<T, ITEM> {
    val generalAdapter by lazy {
        GeneralAdapter(items, bindingClass,
            { binding: T?, item: ITEM ->
                bindHolder(binding, item)
            }, { item ->
                itemClick(item)
            }
        )
    }

    layoutManager = manager
    adapter = generalAdapter
    (adapter as GeneralAdapter<*, *>).notifyDataSetChanged()
    return generalAdapter
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1

    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }

        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun String.makeColor(context: Context, color: Int, vararg strings: String): Spannable {
    val spannableString = SpannableString(this)
    var startIndexOfLink = -1

    for (value in strings) {
        startIndexOfLink = this.indexOf(value, startIndexOfLink + 1)
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)),
            startIndexOfLink,
            startIndexOfLink + value.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return spannableString
}

fun Dialog?.setBackgroundDialog() {
    val window = this?.window
    window.notNull {
        window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        window?.setGravity(Gravity.CENTER)
    }
}