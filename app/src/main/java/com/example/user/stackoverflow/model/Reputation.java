package com.example.user.stackoverflow.model;

import com.google.gson.annotations.SerializedName;

public final class Reputation {
    @SerializedName("reputation_history_type")
    private String reputationHistoryType;
    @SerializedName("reputation_change")
    private long reputationChange;
    @SerializedName("post_id")
    private String postId;
    @SerializedName("creation_date")
    private long creationDate;
    @SerializedName("user_id")
    private String userId;

    public String getReputationHistoryType() {
        return reputationHistoryType;
    }

    public void setReputationHistoryType(String reputationHistoryType) {
        this.reputationHistoryType = reputationHistoryType;
    }

    public long getReputationChange() {
        return reputationChange;
    }

    public void setReputationChange(long reputationChange) {
        this.reputationChange = reputationChange;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
