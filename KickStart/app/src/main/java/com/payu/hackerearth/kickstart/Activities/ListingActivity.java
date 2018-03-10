package com.payu.hackerearth.kickstart.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.payu.hackerearth.kickstart.Adapters.ProjectListingAdapter;
import com.payu.hackerearth.kickstart.Config;
import com.payu.hackerearth.kickstart.Helper;
import com.payu.hackerearth.kickstart.Listners.AdaptorclickListner;
import com.payu.hackerearth.kickstart.Models.Project;
import com.payu.hackerearth.kickstart.R;
import com.payu.hackerearth.kickstart.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.support.v7.widget.SearchView;

import static com.payu.hackerearth.kickstart.R.drawable.c;

public class ListingActivity extends AppCompatActivity implements AdaptorclickListner {
    View view;
    LinearLayout sort_view;
    LinearLayout filter_view;
    TextView sort_type;
    private static String LOG_TAG = "ListingActivity";
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ArrayList<Project> projectsToDispaly = new ArrayList<Project>();
    private ProjectListingAdapter mAdapter;
    private long mLastPress = 0;
    private static final long TIME_INTERVAL = 5000;
    int sortValue=5;
    int filtervalue = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);
        view = findViewById(android.R.id.content);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        sort_view = (LinearLayout) findViewById(R.id.sort_view);
        filter_view = (LinearLayout) findViewById(R.id.filter_view);
        sort_type = (TextView) findViewById(R.id.sortby);
        sort_type.setText("Original");

        mLayoutManager = new LinearLayoutManager(this);
        //Set the adaptor here
        mAdapter = new ProjectListingAdapter(new ArrayList<Project>());
        mAdapter.setAdaptorclickListner(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setTitle("Project Listing");


        sort_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySortDialog();
            }
        });

        filter_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFilterDialog();
            }
        });

        if (savedInstanceState!=null){
            ArrayList <Project> projects = savedInstanceState.getParcelableArrayList("projectArray");
            projectsToDispaly = projects;

            loadProjects(projects);
        }
        else {

            loadProjectFromServer(sortValue,filtervalue);
        }
    // Example of a call to a native method

    }

    private void loadProjects(ArrayList<Project> projects){
        mAdapter.setItems(new ArrayList<Project>(projects));
    }

    private void loadProjectFromServer(final int sortvalue,final int filtervalue){
        String url = Config.BASE_URL;
        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.TransparentProgressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading Offers");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            JSONArray ParentArray = new JSONArray(s);

                            for (int i = 0; i < ParentArray.length(); i++) {
                                JSONObject ParentObject = ParentArray.getJSONObject(i);
                                Project project =new Project();
                                project.s_no = ParentObject.getInt("s.no");
                                project.num_backers = ParentObject.getString("num.backers");
                                project.amt_pledged = ParentObject.getInt("amt.pledged");
                                project.blurb = ParentObject.getString("blurb");
                                project.by = ParentObject.getString("by");
                                project.country = ParentObject.getString("country");
                                project.currency  = ParentObject.getString("currency");
                                project.location = ParentObject.getString("location");
                                project.url = ParentObject.getString("url");
                                project.type = ParentObject.getString("type");
                                project.title = ParentObject.getString("title");
                                project.state = ParentObject.getString("state");
                                project.percentage_funded = ParentObject.getInt("percentage.funded");
                                //Date

                                project.end_time = Helper.stringToDate(ParentObject.getString("end.time"));

                                projectsToDispaly.add(project);
                            }

                            //Logic for sorting and filter////


                            //
                            if (sortvalue==0||sortvalue==1||sortvalue==2||filtervalue==0||filtervalue==1){
                                ArrayList<Project> projects = new ArrayList<Project>();
                                ArrayList<Project> toAdpaptorList = new ArrayList<Project>();
                                if (sortvalue==0){
                                    //sort the list by original

                                }
                                if (sortvalue == 1){
                                    //by endtime
                                }
                                if(sortvalue == 2){

                                }
                                if (filtervalue==0){
                                    if (!projects.isEmpty()){

                                        //use sorted list
                                        for (Project project:
                                                projects) {
                                            int numberBacker =  Integer.parseInt(project.num_backers);
                                            if (numberBacker>=0 && numberBacker<=50000){
                                                toAdpaptorList.add(project);
                                            }

                                        }
                                    }
                                    else{
                                        //use original list
                                        for (Project project:
                                                projectsToDispaly) {
                                            int numberBacker =  Integer.parseInt(project.num_backers);
                                            if (numberBacker>=0 && numberBacker<=50000){
                                                toAdpaptorList.add(project);
                                            }

                                        }
                                    }

                                }
                                if (filtervalue == 1){
                                    if(!projects.isEmpty()){
                                        //use sorted list
                                        for (Project project:
                                                projects) {
                                            int numberBacker =  Integer.parseInt(project.num_backers);
                                            if (numberBacker>=500001 && numberBacker<=100000){
                                                toAdpaptorList.add(project);
                                            }

                                        }

                                    }
                                    else{
                                        //use sorted list
                                        for (Project project:
                                                projectsToDispaly) {
                                            int numberBacker =  Integer.parseInt(project.num_backers);
                                            if (numberBacker>=500001 && numberBacker<=100000){
                                                toAdpaptorList.add(project);
                                            }

                                        }

                                    }


                                }
                                mAdapter.setItems(toAdpaptorList);

                            }
                            else{
                                mAdapter.setItems(projectsToDispaly);
                            }





                        } catch (JSONException e) {

                            Toast.makeText(ListingActivity.this, R.string.something_wrong, Toast.LENGTH_LONG).show();
                        } finally {

                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Snackbar snackbartest = Snackbar.make(view, "No Internet Connection.", Snackbar.LENGTH_LONG);
                        View snckViewtest = snackbartest.getView();
                        snckViewtest.setBackgroundColor(Color.parseColor("#f44336"));
                        snackbartest.show();
            }
        });

        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putParcelableArrayList("projectArray", new ArrayList<Project>(projectsToDispaly));
    }

    @Override
    public void onClickAdaptor(android.support.v4.util.Pair<View, String> pair1, android.support.v4.util.Pair<View, String> pair2, Project project, int img) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ListingActivity.this, pair1);

        Intent intent = new Intent(this, ProjectPage.class);
        intent.putExtra("img",img);
        intent.putExtra("project",project);
        startActivity(intent,optionsCompat.toBundle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(LOG_TAG,newText);
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public View retrieveView(){
        return view;
    }

    @Override
    public void onBackPressed() {
        if(isTaskRoot()){
            //Toast onBackPressedToast = Toast.makeText(this, R.string.press_once_again_to_exit, Toast.LENGTH_SHORT);
            Snackbar exitsnack=Snackbar.make(retrieveView(), R.string.press_once_again_to_exit, Snackbar.LENGTH_LONG);
            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastPress > TIME_INTERVAL) {
                // onBackPressedToast.show();
                exitsnack.show();
                mLastPress = currentTime;
            } else {
                //  onBackPressedToast.cancel();
                exitsnack.dismiss();
                super.onBackPressed();
            }

        }
        else{
            super.onBackPressed();
        }

    }


    private void displaySortDialog(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View instDialogView = factory.inflate(
                R.layout.sort_layout, null);

        final AlertDialog instDialog = new AlertDialog.Builder(this).create();
        instDialog.setView(instDialogView);


        RadioGroup rb = (RadioGroup) instDialogView.findViewById(R.id.radioSort);
        if (sortValue==0){
            //
            rb.check(R.id.radioOr);
        }
        else if(sortValue==1){
            rb.check(R.id.radioTime);
        }
        else if(sortValue==2){
            rb.check(R.id.radioAlpha);
        }
        else{
            rb.check(R.id.radioOr);
        }


        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioOr:
                        // Sort by orginal
                        sortValue = 0;
                        //loadProjectFromServer(sortValue,filtervalue);
                        instDialog.dismiss();
                        break;
                    case R.id.radioTime:

                        //sort by time
                        sortValue= 1;
                        instDialog.dismiss();
                        //loadProjectFromServer(sortValue,filtervalue);
                        break;
                    case R.id.radioAlpha:
                        //sort by alphbetically
                        instDialog.dismiss();
                        //loadProjectFromServer(sortValue,filtervalue);
                        sortValue = 2;
                        break;

                    default:
                        //Sortby orginal
                        sortValue= 0;
                        //loadProjectFromServer(sortValue,filtervalue);
                        instDialog.dismiss();
                        break;
                }
            }

        });

        instDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        instDialog.show();




    }
    private void displayFilterDialog(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View instDialogView = factory.inflate(
                R.layout.filter_layout, null);

        final AlertDialog instDialog = new AlertDialog.Builder(this).create();
        instDialog.setView(instDialogView);
        //0-original 1- End time 3 - Alphabetically
        final RadioGroup rb = (RadioGroup) instDialogView.findViewById(R.id.radioFilter);

        if (filtervalue==0){
            //
            rb.check(R.id.first_range);
        }
        else if(filtervalue==1){
            rb.check(R.id.second_range);
        }

        else{
            rb.clearCheck();
        }


        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.first_range://0-50000
                        // filter in that range
                        filtervalue =0;
                        //loadProjectFromServer(sortValue,filtervalue);
                        instDialog.dismiss();
                        break;
                    case R.id.second_range://50001-100000

                        //filter in that range
                        filtervalue = 1;
                        //loadProjectFromServer(sortValue,filtervalue);
                        instDialog.dismiss();

                        break;
                    default:
                        //Sortby orginal
                        //clear
                        filtervalue = 5;
                        //loadProjectFromServer(sortValue,filtervalue);
                        instDialog.dismiss();
                        break;
                }
            }

        });






        instDialogView.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // Reset the List
                rb.clearCheck();
                instDialog.dismiss();

            }
        });
        instDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        instDialog.show();


    }
}
