package org.acme;
public class Submission {

    private int submissionId;
    private String imageUrl;
    private String comment;
    private String uploader;
    private String token;

    public Submission() {
        this.submissionId = 0;
        this.imageUrl = "";
        this.comment = "";
        this.uploader = "";
        this.token = "";
    }

    public Submission(int submissionId, String imageUrl, String comment, String uploader, String token) {
        this.submissionId = submissionId;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.uploader = uploader;
        this.token = token;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int id) {
        this.submissionId = id;
    }

    public String getUploader() {
        return uploader;
    }

    public String getComment() {
        return comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
