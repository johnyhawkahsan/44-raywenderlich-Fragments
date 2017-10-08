
package com.ahsan.a44_raywenderlich_fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.app.Activity;

public class RageComicListFragment extends Fragment {

  private int[] mImageResIds;
  private String[] mNames;
  private String[] mDescriptions;
  private String[] mUrls;
  private OnRageComicSelected mListener;//This field is a reference to the fragment's listener, which will be the activity.

  public static RageComicListFragment newInstance() {
    return new RageComicListFragment();
  }


  //onAttach() contains code that accesses the resources you need via the Context to which the fragment has attached.
  //Because the code is in onAttach(), you can rest assured that the fragment has a valid Context.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //This initializes the listener reference. You wait until onAttach() to ensure that the fragment actually attached itself.
        //Then you verify that the activity implements the OnRageComicSelected interface via instanceof.
        //If it doesn't, it throws an exception since you can't proceed. If it does, then you set the activity as the listener for RageComicListFragment.
        if (context instanceof OnRageComicSelected){
            mListener = (OnRageComicSelected) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnRageComicSelected.");
        }

        // Get rage face names and descriptions.
        final Resources resources = context.getResources();
        mNames = resources.getStringArray(R.array.names);//Get Array of names in arrays.xml
        mDescriptions = resources.getStringArray(R.array.descriptions);//Get Array of description in arrays.xml
        mUrls = resources.getStringArray(R.array.urls);//Get Array of urls in arrays.xml

        // Get rage face images.
        final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
        final int imageCount = mNames.length;
        mImageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
    }


    //In onCreateView(), you inflate the view hierarchy of RageComicListFragment, which contains a RecyclerView, and perform some setup.
    //Generally, if you have to poke and prod at a fragment's view, onCreateView() is a good place to start because you have the view right there.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rage_comic_list, container, false);

        final Activity activity = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(new RageComicAdapter(activity));
        return view;
    }



  class RageComicAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;

    public RageComicAdapter(Context context) {
      mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      return new ViewHolder(mLayoutInflater
          .inflate(R.layout.recycler_item_rage_comic, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

      final int imageResId = mImageResIds[position];
      final String name = mNames[position];
      final String description = mDescriptions[position];
      final String url = mUrls[position];
      viewHolder.setData(imageResId, name);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRageComicSelected(imageResId, name, description, url);
            }
        });
    }

    @Override
    public int getItemCount() {
      return mNames.length;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    private ImageView mImageView;
    private TextView mNameTextView;

    private ViewHolder(View itemView) {
      super(itemView);

      // Get references to image and name.
      mImageView = (ImageView) itemView.findViewById(R.id.comic_image);
      mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    private void setData(int imageResId, String name) {
      mImageView.setImageResource(imageResId);
      mNameTextView.setText(name);
    }
  }



    //This defines a listener interface for the activity to listen to the fragment. The activity will implement this interface, and the fragment will
    //invoke the onRageComicSelected() when an item is selected, passing the selection to the activity.
    public interface OnRageComicSelected {
        void onRageComicSelected(int imageResId, String name,
                                 String description, String url);
    }
}
