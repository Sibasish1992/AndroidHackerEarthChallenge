package com.payu.hackerearth.kickstart.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Sibasish Mohanty on 12/08/17.
 */

public class Project implements Parcelable {
    public int s_no;
    public int amt_pledged;
    public String blurb;
    public String by;
    public String  country;
    public String currency;
    public Date end_time;
    public String location;
    public int percentage_funded;
    public String num_backers;
    public String state;
    public String title;
    public String type;
    public String url;


    public Project(){

    }

    public Project(int s_no,int amt_pledged,String blurb,String by,String  country,String currency,Date end_time,String location,int percentage_funded,String num_backers,String state,String title,String type,String url){
        this.s_no =s_no;
        this.amt_pledged=amt_pledged;
        this.blurb=blurb;
        this.by = by;
        this.country =country;
        this.currency = currency;
        this.end_time = end_time;
        this.location = location;
        this.percentage_funded =percentage_funded;
        this.num_backers=num_backers;
        this.state=state;
        this.title = title;
        this.type =type;
        this.url = url;


    }

    public Project(Parcel in) {
        this.s_no = in.readInt();
        this.amt_pledged = in.readInt();
        this.blurb = in.readString();
        this.by = in.readString();
        this.country =  in.readString();
        this.currency = in.readString();
        //date
        this.end_time = new Date(in.readLong());
        this.location = in.readString();
        this.percentage_funded = in.readInt();
        this.num_backers = in.readString();
        this.state =  in.readString();
        this.title = in.readString();
        this.type =  in.readString();
        this.url = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(s_no);
        out.writeInt(amt_pledged);
        out.writeString(blurb);
        out.writeString(by);
        out.writeString(country);
        out.writeString(currency);
        //date
        out.writeLong(end_time.getTime());
        out.writeString(location);
        out.writeInt(percentage_funded);
        out.writeString(num_backers);
        out.writeString(state);
        out.writeString(title);
        out.writeString(type);
        out.writeString(url);

    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
