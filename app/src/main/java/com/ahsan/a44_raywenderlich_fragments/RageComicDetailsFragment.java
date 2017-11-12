
package com.ahsan.a44_raywenderlich_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//1 //Declares RageComicDetailsFragment as a subclass of Fragment
public class RageComicDetailsFragment extends Fragment {

    private static final String ARGUMENT_IMAGE_RES_ID = "imageResId";
    private static final String ARGUMENT_NAME = "name";
    private static final String ARGUMENT_DESCRIPTION = "description";
    private static final String ARGUMENT_URL = "url";


    //2 //Provides a method for creating new instances of the fragment, a factory method.
    //You’ll notice that while RageComicDetailsFragment has a factory instance method, newInstance(), it does not have any constructors.
    //This is because you did not define any constructors, the compiler automatically generates an empty, default constructor that takes no arguments. This is all that you should have for a fragment: no other constructors.
    public static RageComicDetailsFragment newInstance(int imageResId, String name, String description, String url) {

        //TODO: NOTE By Ahsan: In this default constructor, we get those 4 values from MainActivity, and we put them in Bundle Arguments so that we can get those argumment values back in onCreateView
        //I think we could also get those values here and could save them to Global variables instead of this arguments method.
        final Bundle args = new Bundle();
        args.putInt(ARGUMENT_IMAGE_RES_ID, imageResId);
        args.putString(ARGUMENT_NAME, name);
        args.putString(ARGUMENT_DESCRIPTION, description);
        args.putString(ARGUMENT_URL, url);
        final RageComicDetailsFragment fragment = new RageComicDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }



    //3 Creates the view hierarchy controlled by the fragment.
    //Activities use setContentView() to specify the XML file that defines their layouts, but fragments create their view hierarchy in onCreateView().
    //Here you called LayoutInflater.inflate to create the hierarchy of RageComicDetailsFragment.
    //The third parameter of inflate specifies whether the inflated fragment should be added to the container. The container is the parent view that will hold the fragment’s view hierarchy. You should always set this to false: the FragmentManager will take care of adding the fragment to the container.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rage_comic_details, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.comic_image);
        final TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

        final Bundle args = getArguments();//Get Arguments for this Fragment
        imageView.setImageResource(args.getInt(ARGUMENT_IMAGE_RES_ID));
        nameTextView.setText(args.getString(ARGUMENT_NAME));
        final String text = String.format(getString(R.string.description_format), args.getString
                (ARGUMENT_DESCRIPTION), args.getString(ARGUMENT_URL));
        descriptionTextView.setText(text);
        return view;
    }
}
