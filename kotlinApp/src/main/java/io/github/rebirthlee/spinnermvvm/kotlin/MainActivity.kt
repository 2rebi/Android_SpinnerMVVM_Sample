package io.github.rebirthlee.spinnermvvm.kotlin

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.rebirthlee.spinnermvvm.kotlin.databinding.ActivityMainBinding
import io.github.rebirthlee.spinnermvvm.kotlin.databinding.ItemViewBinding

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var adapter: SimpleSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val items = ArrayList<SimpleItemViewModel>()
        val click = { model: Person ->
            clickItem.text = String.format("Click item, %s.", model.name)
        }
        items.add(
                PersonMapper.map(Person("https://cdn.pixabay.com/photo/2015/09/18/11/46/man-945482_1280.jpg", "Michael"), click))
        items.add(
                PersonMapper.map(Person("https://cdn.pixabay.com/photo/2017/08/10/20/42/camel-2627624_1280.jpg", "William"), click))
        items.add(
                PersonMapper.map(Person("https://cdn.pixabay.com/photo/2019/04/11/11/43/summer-4119561_1280.jpg", "Matthew"), click))
        items.add(
                PersonMapper.map(Person("https://cdn.pixabay.com/photo/2015/02/19/11/36/person-641989_1280.jpg", "Steven"), click))
        items.add(
                PersonMapper.map(Person("https://cdn.pixabay.com/photo/2018/08/24/11/50/girl-3627800_1280.jpg", "Linda"), click))

        adapter = SimpleSpinnerAdapter(this, R.layout.item_view, BR.viewModel, items)
        dataBinding.profileList.adapter = this.adapter
    }
}
