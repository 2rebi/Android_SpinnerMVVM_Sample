package io.github.rebirthlee.spinnermvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.github.rebirthlee.spinnermvvm.databinding.ActivityMainBinding;
import io.github.rebirthlee.spinnermvvm.databinding.ItemViewBinding;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener<Person> {

    private ActivityMainBinding dataBinding;
    private SimpleSpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        List<SimpleItemViewModel> items = new ArrayList<>();
        items.add(PersonMapper.map(new Person("https://cdn.pixabay.com/photo/2015/09/18/11/46/man-945482_1280.jpg", "Michael"),
                this));
        items.add(PersonMapper.map(new Person("https://cdn.pixabay.com/photo/2017/08/10/20/42/camel-2627624_1280.jpg", "William"),
                this));
        items.add(PersonMapper.map(new Person("https://cdn.pixabay.com/photo/2019/04/11/11/43/summer-4119561_1280.jpg", "Matthew"),
                this));
        items.add(PersonMapper.map(new Person("https://cdn.pixabay.com/photo/2015/02/19/11/36/person-641989_1280.jpg", "Steven"),
                this));
        items.add(PersonMapper.map(new Person("https://cdn.pixabay.com/photo/2018/08/24/11/50/girl-3627800_1280.jpg", "Linda"),
                this));

        adapter = new SimpleSpinnerAdapter<ItemViewBinding, SimpleItemViewModel>(this, R.layout.item_view, items) {
            @Override
            protected int getViewModelVariable() {
                return BR.viewModel;
            }
        };
        dataBinding.profileList.setAdapter(adapter);
    }

    @Override
    public void onClick(Person model) {
        dataBinding.clickItem.setText(String.format("Click item, %s.", model.name));

    }
}
