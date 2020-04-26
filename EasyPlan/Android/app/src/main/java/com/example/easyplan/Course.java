package com.example.easyplan;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {

    private String id;
    private String catalog;
    private String subject, acadOrg, facilId, friday, monday, mtgEnd, mtgStart, prof, saturday, section, sunday, thursday, tuesday, wednesday;

    /*public Course(String id, String catalog, String subject) {
        this.id = id;
        this.catalog = catalog;
        this.subject = subject;
    }*/

    public Course(String id, String catalog, String subject, String acad_org, String facil_id, String friday, String monday, String mtg_end, String mtg_start, String prof, String saturday, String section, String sunday, String thursday, String tuesday, String wednesday) {
        this.id = id;
        this.catalog = catalog;
        this.subject = subject;
        this.acadOrg = acad_org;
        this.facilId = facil_id;
        this.friday = friday;
        this.monday = monday;
        this.mtgEnd = mtg_end;
        this.mtgStart = mtg_start;
        this.prof = prof;
        this.saturday = saturday;
        this.section = section;
        this.sunday = sunday;
        this.thursday = thursday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
    }

    public Course() {
    }

    public String getAcad_org() {
        return acadOrg;
    }

    public void setAcad_org(String acadOrg) {
        this.acadOrg = acadOrg;
    }

    public String getFacil_id() {
        return facilId;
    }

    public void setFacil_id(String facil_id) {
        this.facilId = facil_id;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getMtg_end() {
        return mtgEnd;
    }

    public void setMtg_end(String mtg_end) {
        this.mtgEnd = mtg_end;
    }

    public String getMtg_start() {
        return mtgStart;
    }

    public void setMtg_start(String mtg_start) {
        this.mtgStart = mtg_start;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    protected Course(Parcel in) {
        id = in.readString();
        catalog = in.readString();
        subject = in.readString();
        acadOrg = in.readString();
        facilId = in.readString();
        friday = in.readString();
        monday = in.readString();
        mtgEnd = in.readString();
        mtgStart = in.readString();
        prof = in.readString();
        saturday = in.readString();
        section = in.readString();
        sunday = in.readString();
        thursday = in.readString();
        tuesday = in.readString();
        wednesday = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(catalog);
        dest.writeString(subject);
        dest.writeString(acadOrg);
        dest.writeString(facilId);
        dest.writeString(friday);
        dest.writeString(monday);
        dest.writeString(mtgEnd);
        dest.writeString(mtgStart);
        dest.writeString(prof);
        dest.writeString(saturday);
        dest.writeString(section);
        dest.writeString(sunday);
        dest.writeString(thursday);
        dest.writeString(tuesday);
        dest.writeString(wednesday);
    }
}
