package com.modulesoftware.viewpagerwithlistadaptercurrentpagechangeonsubmitbug

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.modulesoftware.viewpagerwithlistadaptercurrentpagechangeonsubmitbug.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * This showcasts an issue where ViewPager2 with RecyclerView.ListAdapter changes a page automatically
 * if we submit data to it 2 times. The key is the data set and ids of the data.
 * If submitted listed contains only one same item from old list then index of that item in new new list
 * will became as position selected page for the view pager.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val simpleDataAdapter = SimpleDataListAdapter()

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initUI()

        subscribeUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {

        binding.pagerState.text = "PagerState -> None. Click Start"

        binding.listOneContents.text = "List1: $SIMPLE_DATA_LIST_1"
        binding.listTwoContents.text = "List2: $SIMPLE_DATA_LIST_2"

        binding.start.setOnClickListener {
            submitLists()
//            viewModel.fillLists()
        }

        setupSimpleDataAdapter()
    }

    private fun setupSimpleDataAdapter() {

        simpleDataAdapter.setHasStableIds(true)

        binding.viewPager.adapter = simpleDataAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val itemId = simpleDataAdapter.getItemId(position)

                Log.d("myLogs", "registerOnPageChangeCallback -> pageSelected:$position itemId:$itemId")

                binding.pagerState.text = "PagerState -> selectedPosition:$position itemId:$itemId"
            }
        })
    }

    private fun subscribeUI() {

        viewModel.simpleData.observe(this) {
            submitList(it)
        }
    }

    private fun submitList(simpleDatas: List<SimpleData>) {
        Log.d("myLogs", "submitList:$simpleDatas")
        simpleDataAdapter.submitList(simpleDatas)
    }

    private fun submitLists() {

        lifecycleScope.launch {
            submitList(SIMPLE_DATA_LIST_1)

            delay(3000)

            submitList(SIMPLE_DATA_LIST_2)
        }
    }
}