package com.example.user.stackoverflow.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public final class User {
    @Embedded
    @SerializedName("badge_counts")
    private BadgeCounts badgeCounts;
    @ColumnInfo(name = "account_id")
    @SerializedName("account_id")
    private String accountId;
    @ColumnInfo(name = "is_employee")
    @SerializedName("is_employee")
    private boolean isEmployee;
    @ColumnInfo(name = "last_modified_date")
    @SerializedName("last_modified_date")
    private long lastModifiedDate;
    @ColumnInfo(name = "last_access_date")
    @SerializedName("last_access_date")
    private long lastAccessDate;
    @ColumnInfo(name = "reputation_change_year")
    @SerializedName("reputation_change_year")
    private int reputationChangeYear;
    @ColumnInfo(name = "reputation_change_quarter")
    @SerializedName("reputation_change_quarter")
    private int reputationChangeQuarter;
    @ColumnInfo(name = "reputation_change_month")
    @SerializedName("reputation_change_month")
    private int reputationChangeMonth;
    @ColumnInfo(name = "reputation_change_week")
    @SerializedName("reputation_change_week")
    private int reputationChangeWeek;
    @ColumnInfo(name = "reputation_change_day")
    @SerializedName("reputation_change_day")
    private int reputationChangeDay;
    @ColumnInfo(name = "reputation")
    @SerializedName("reputation")
    private int reputation;
    @ColumnInfo(name = "creation_date")
    @SerializedName("creation_date")
    private long creationDate;
    @ColumnInfo(name = "user_type")
    @SerializedName("user_type")
    private String userType;
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    private String userId;
    @ColumnInfo(name = "accept_rate")
    @SerializedName("accept_rate")
    private float acceptRate;
    @ColumnInfo(name = "location")
    @SerializedName("location")
    private String location;
    @ColumnInfo(name = "website_url")
    @SerializedName("website_url")
    private String websiteUrl;
    @ColumnInfo(name = "link")
    @SerializedName("link")
    private String link;
    @ColumnInfo(name = "profile_image")
    @SerializedName("profile_image")
    private String profileImage;
    @ColumnInfo(name = "display_name")
    @SerializedName("display_name")
    private String displayName;

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }

    public void setBadgeCounts(BadgeCounts badgeCounts) {
        this.badgeCounts = badgeCounts;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(long lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public int getReputationChangeYear() {
        return reputationChangeYear;
    }

    public void setReputationChangeYear(int reputationChangeYear) {
        this.reputationChangeYear = reputationChangeYear;
    }

    public int getReputationChangeQuarter() {
        return reputationChangeQuarter;
    }

    public void setReputationChangeQuarter(int reputationChangeQuarter) {
        this.reputationChangeQuarter = reputationChangeQuarter;
    }

    public int getReputationChangeMonth() {
        return reputationChangeMonth;
    }

    public void setReputationChangeMonth(int reputationChangeMonth) {
        this.reputationChangeMonth = reputationChangeMonth;
    }

    public int getReputationChangeWeek() {
        return reputationChangeWeek;
    }

    public void setReputationChangeWeek(int reputationChangeWeek) {
        this.reputationChangeWeek = reputationChangeWeek;
    }

    public int getReputationChangeDay() {
        return reputationChangeDay;
    }

    public void setReputationChangeDay(int reputationChangeDay) {
        this.reputationChangeDay = reputationChangeDay;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getAcceptRate() {
        return acceptRate;
    }

    public void setAcceptRate(float acceptRate) {
        this.acceptRate = acceptRate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
