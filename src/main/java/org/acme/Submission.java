package org.acme;
public class Submission {

    private final int submissionId;
    private String imageUrl;
    private String comment;
    private String uploader;

    public Submission() {
        this.submissionId = 0;
        this.imageUrl ="";
        this.comment = "";
        this.uploader ="";
    }

    public Submission(int submissionId, String imageUrl, String comment, String uploader) {
        this.submissionId = submissionId;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.uploader = uploader;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public Submission setSubmissionId(int id) {
        return new Submission(id, this.imageUrl, this.comment, this.uploader);
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
}
