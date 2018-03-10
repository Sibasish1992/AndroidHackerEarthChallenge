package com.payu.hackerearth.kickstart.Listners;

import android.content.Context;
import android.util.Pair;
import android.view.View;

import com.payu.hackerearth.kickstart.Models.Project;

/**
 * Created by Sibasish Mohanty on 12/08/17.
 */

public interface AdaptorclickListner {

    public void onClickAdaptor(android.support.v4.util.Pair<View,String> pair1, android.support.v4.util.Pair<View,String> pair2, Project project, int img);

}