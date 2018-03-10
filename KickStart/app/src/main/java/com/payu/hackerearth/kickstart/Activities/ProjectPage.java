package com.payu.hackerearth.kickstart.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.payu.hackerearth.kickstart.Helper;
import com.payu.hackerearth.kickstart.Models.Project;
import com.payu.hackerearth.kickstart.R;

import org.w3c.dom.Text;

/**
 * Created by Sibasish Mohanty on 12/08/17.
 */

public class ProjectPage extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    ImageView feature_imge;

    TextView header_title;// Title
    TextView header_subtitle_one;//By
    TextView header_subtitle_two;// pleadge


    TextView end_date;
    TextView by;
    TextView pleadge;
    TextView blurb;
    TextView country;
    TextView s_no;
    TextView currency;
    TextView location;
    TextView percentage_funded;
    TextView num_backers;
    TextView state;
    TextView type;
    TextView title;
    private static String LOG_TAG = "ProjectPage";
    Project project;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        feature_imge = (ImageView) findViewById(R.id.backdrop);

        Bundle extras = getIntent().getExtras();

        project = extras.getParcelable("project");
        Integer img = extras.getInt("img");

        if (img!=null){
            feature_imge.setImageResource(img);
        }

        header_title = (TextView)findViewById(R.id.header);
        header_subtitle_one = (TextView)findViewById(R.id.creator);
        header_subtitle_two = (TextView)findViewById(R.id.amount);

        end_date= (TextView)findViewById(R.id.end_time);
        by = (TextView)findViewById(R.id.by);
        pleadge = (TextView)findViewById(R.id.pleadge);
        location = (TextView)findViewById(R.id.location);
        state = (TextView)findViewById(R.id.state);
        type = (TextView)findViewById(R.id.type);
        blurb = (TextView)findViewById(R.id.blurb);
        country=(TextView)findViewById(R.id.country);
        s_no = (TextView)findViewById(R.id.sno);
        currency=(TextView)findViewById(R.id.currency);
        percentage_funded = (TextView)findViewById(R.id.percentage_funded);
        num_backers =(TextView)findViewById(R.id.num_backers);
        title = (TextView)findViewById(R.id.title);
        button = (Button)findViewById(R.id.redirect);


        // Set values
        header_title.setText(project.title);
        header_subtitle_one.setText("By: "+project.by);
        header_subtitle_two.setText("Pleadge: $"+project.amt_pledged);

        end_date.setText("Ends On: "+ Helper.dateToString(project.end_time)); // fix the date
        by.setText("By: "+project.by);
        pleadge.setText("Pleadge: $"+project.amt_pledged);
        location.setText("Location: "+project.location);
        state.setText("State: "+project.state);
        type.setText("Type: "+ project.type);
        blurb.setText("Blurb: "+project.blurb);
        country.setText("Country: "+project.country);
        s_no.setText("S.No: "+project.s_no);
        currency.setText("Currency: "+project.currency);
        percentage_funded.setText("Percentage Funded: "+project.percentage_funded);
        num_backers.setText("Backers: "+project.num_backers);
        title.setText(project.title);


        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(project.title);
                    header_title.setVisibility(View.GONE);
                    header_subtitle_one.setVisibility(View.GONE);
                    header_subtitle_two.setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    header_title.setVisibility(View.VISIBLE);
                    header_subtitle_one.setVisibility(View.VISIBLE);
                    header_subtitle_two.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProjectPage.this, ProjectSiteActivity.class);

                intent.putExtra("project",project.url);
                startActivity(intent);
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }



}
