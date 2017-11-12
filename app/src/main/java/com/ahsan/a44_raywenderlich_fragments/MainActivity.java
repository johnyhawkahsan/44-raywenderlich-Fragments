//https://www.raywenderlich.com/149112/android-fragments-tutorial-introduction

package com.ahsan.a44_raywenderlich_fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements RageComicListFragment.OnRageComicSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Here you get RageComicListFragment into MainActivity. You ask your new friend, FragmentManager, to add it.
        //FragmentManager helped achieve this awesomeness through FragmentTransactions, which are basically fragment operations such as, add, remove, etc.
        //First you grab the FragmentManager by calling getSupportFragmentManager(), as opposed to getFragmentManager since you are using support fragments.
        //Then you ask that FragmentManager to start a new transaction by calling beginTransaction() -- you probably figured that out yourself. Next you specify the add operation that you want by calling add and passing in:
        //The view ID of a container for the fragment's view hierarchy in the activity's layout. If you sneak a quick peek at activity_main.xml, you'll find @+id/root_layout.
        //The fragment instance to be added. A string that acts as a tag/identifier for the fragment instance. This allows the FragmentManager to later retrieve the fragment for you.
        //Finally, you ask the FragmentManager to execute the transaction by calling commit(). And with that, the fragment is added!
        //An if block contains the code that displays the fragment and checks that the activity doesn't have saved state. When an activity is saved, all of its active fragments are also saved.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, RageComicListFragment.newInstance(), "rageComicList")
                    .commit();
        }

    }



    @Override
    public void onRageComicSelected(int imageResId, String name, String description, String url) {
        final RageComicDetailsFragment detailsFragment = RageComicDetailsFragment.newInstance(imageResId, name, description, url);//Passing 4 values to constructor, that we received from implemented listener method
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_layout, detailsFragment, "rageComicDetails")
                .addToBackStack(null)
                .commit();
    }


}
