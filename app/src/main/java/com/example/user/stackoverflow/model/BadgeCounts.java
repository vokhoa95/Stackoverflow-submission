package com.example.user.stackoverflow.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "badge_counts", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "user_id",
        childColumns = "userId"))
public class BadgeCounts {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private String userId;
    @ColumnInfo(name = "bronze")
    @SerializedName("bronze")
    private float bronze;
    @ColumnInfo(name = "silver")
    @SerializedName("silver")
    private float silver;
    @ColumnInfo(name = "gold")
    @SerializedName("gold")
    private float gold;


    // Getter Methods

    public String getUserId() {
        return userId;
    }

    public float getBronze() {
        return bronze;
    }

    public float getSilver() {
        return silver;
    }

    public float getGold() {
        return gold;
    }

    // Setter Methods

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBronze(float bronze) {
        this.bronze = bronze;
    }

    public void setSilver(float silver) {
        this.silver = silver;
    }

    public void setGold(float gold) {
        this.silver = gold;
    }
}
