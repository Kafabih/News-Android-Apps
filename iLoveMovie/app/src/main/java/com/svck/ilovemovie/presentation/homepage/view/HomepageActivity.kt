package com.svck.ilovemovie.presentation.homepage.view

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.svck.ilovemovie.R
import com.svck.ilovemovie.databinding.ActivityHomeBinding
import com.svck.ilovemovie.domain.base.activity.BaseActivity
import com.svck.ilovemovie.external.extension.setVisibleIf
import com.svck.ilovemovie.presentation.homepage.viewmodel.HomepageViewModel


class HomepageActivity : BaseActivity() {

    private val binding: ActivityHomeBinding by viewBinding()
    private val viewModel: HomepageViewModel by viewModels()
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigationController()
        setupMainTab()
    }

    private fun setupNavigationController() {
        val host = supportFragmentManager.findFragmentById(binding.fcvMain.id) as NavHostFragment
        navController = host.navController
    }


    private fun setupMainTab() {
        binding.ibMenu.setOnClickListener {
            // load the animation
            val animeToRight: Animation =
                AnimationUtils.loadAnimation(this, R.anim.left_to_right)

            binding.bottomNav.startAnimation(animeToRight)

            animeToRight.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    binding.ibMenu.setVisibleIf(false)
                    binding.bottomNav.setVisibleIf(true)
                }

                override fun onAnimationEnd(p0: Animation?) {
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }

            })



        }
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController?.navigate(R.id.main_fragment)
                }

                R.id.search -> {
                    showLongMessage("Coming Soon")
                }

                R.id.favorite -> {
                    showLongMessage("Coming Soon")
                }

                R.id.close -> {

                    val animeToLeft: Animation =
                        AnimationUtils.loadAnimation(this, R.anim.right_to_left)

                    binding.bottomNav.startAnimation(animeToLeft)

                    animeToLeft.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {

                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            binding.bottomNav.setVisibleIf(false)
                            binding.ibMenu.setVisibleIf(true)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {

                        }

                    })


                }
            }
            true
        }
    }
}