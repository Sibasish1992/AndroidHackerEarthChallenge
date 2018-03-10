package com.payu.hackerearth.kickstart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payu.hackerearth.kickstart.Activities.ProjectPage;
import com.payu.hackerearth.kickstart.Helper;
import com.payu.hackerearth.kickstart.Listners.AdaptorclickListner;
import com.payu.hackerearth.kickstart.Models.Project;
import com.payu.hackerearth.kickstart.R;

import java.util.ArrayList;

/**
 * Created by Sibasish Mohanty on 12/08/17.
 */

public class ProjectListingAdapter extends RecyclerView
        .Adapter<ProjectListingAdapter.DataObjectHolder> implements Filterable {


    public static int[] imageArray = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h};
    private static String LOG_TAG = "ProjectListingAdapter";
    private ArrayList<Project> mDataset;
    private ArrayList<Project> mFilteredList;
    private AdaptorclickListner adaptorclickListner;


    public static class DataObjectHolder extends RecyclerView.ViewHolder  {
        TextView title;
        ImageView back;
        TextView pleadge;
        TextView backers;
        TextView lastdate;
        Project project;
        int img;



        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title_rendered);
            pleadge = (TextView) itemView.findViewById(R.id.tv_backers);
            backers = (TextView) itemView.findViewById(R.id.pleadge);
            lastdate = (TextView) itemView.findViewById(R.id.tv_date);
            back = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }
    public void setAdaptorclickListner(AdaptorclickListner dt){
        this.adaptorclickListner = dt;
    }
    public ProjectListingAdapter(ArrayList<Project> myDataset) {
        mDataset = myDataset;
        mFilteredList = myDataset;

    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_element_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }



    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        // final SubjectHomeResponseVO.Topic topic = mDataset.get(position);
        final Project project = mFilteredList.get(position);

        holder.project = project;
        holder.title.setText(project.title);
        holder.pleadge.setText("Pleadge: $"+project.amt_pledged);


        holder.backers.setText("Backers: "+project.num_backers);

        holder.lastdate.setText("Ends On: "+Helper.dateToString(project.end_time));

        holder.back.setImageResource(imageArray[project.s_no%8]);
        holder.img = imageArray[project.s_no%8];
        final int img = imageArray[project.s_no%8];

        final View image = holder.back;
        final View title =holder.title;

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Context c = v.getContext();
                Pair<View,String> pair1 = Pair.create(image,c.getResources().getString(R.string.img_tran));
                Pair<View,String> pair2 = Pair.create((View)title,c.getResources().getString(R.string.title_tran));

                adaptorclickListner.onClickAdaptor(pair1,pair2,project,img);
            }
        };

        holder.itemView.setOnClickListener(listener);

    }

    public void addItem(Project dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void setItems(ArrayList<Project> offers){
        mDataset = offers;
        mFilteredList = offers;
        notifyDataSetChanged();
    }
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                ArrayList<Project> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = mDataset;
                } else {
                    for (Project project : mDataset) {

                        if (project.title.toLowerCase().contains(charString.toLowerCase())) {
                            Log.e(LOG_TAG,charString.toLowerCase());
                            Log.e(LOG_TAG,project.title.toLowerCase());
                            filteredList.add(project);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Project>) filterResults.values;
                for (Project project:
                        mFilteredList) {
                    Log.e(LOG_TAG,project.title);
                }

                notifyDataSetChanged();
            }
        };

    }

}
