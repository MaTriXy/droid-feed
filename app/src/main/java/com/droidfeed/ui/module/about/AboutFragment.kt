package com.droidfeed.ui.module.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.droidfeed.BuildConfig
import com.droidfeed.databinding.FragmentAboutBinding
import com.droidfeed.ui.common.BaseFragment
import com.droidfeed.util.AnalyticsUtil
import com.droidfeed.util.CustomTab
import com.droidfeed.util.extention.startActivity
import javax.inject.Inject

@SuppressLint("ValidFragment")

class AboutFragment : BaseFragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var viewModel: AboutViewModel

    @Inject
    lateinit var customTab: CustomTab

    @Inject
    lateinit var analytics: AnalyticsUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(AboutViewModel::class.java)

        binding.viewModel = viewModel

        init()
        initObservers()
    }

    private fun init() {
        binding.txtAppVersion.text = BuildConfig.VERSION_NAME
    }

    @Suppress("UNCHECKED_CAST")
    private fun initObservers() {
        viewModel.rateAppEvent.observe(this, Observer {
            activity?.let { it1 -> it?.startActivity(it1) }
        })

        viewModel.contactDevEvent.observe(this, Observer {
            activity?.let { it1 -> it?.startActivity(it1) }
        })

        viewModel.shareAppEvent.observe(this, Observer {
            activity?.let { it1 ->
                it?.startActivity(it1)
                analytics.logAppShare()
            }
        })

        viewModel.openLinkEvent.observe(this, Observer {
            it?.let { it1 -> customTab.showTab(it1) }
        })

        viewModel.openLibrariesEvent.observe(this, Observer {
            showLicencesDialog()
        })
    }

    private fun showLicencesDialog() {
        val ft = fragmentManager?.beginTransaction()
        val prev = fragmentManager?.findFragmentByTag("dialog")
        if (prev != null) {
            ft?.remove(prev)
        }
        ft?.addToBackStack(null)

        val newFragment = LicencesFragment()
        newFragment.show(ft, "dialog")
    }
}