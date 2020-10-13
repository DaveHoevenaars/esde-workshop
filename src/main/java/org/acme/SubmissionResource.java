package org.acme;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/submissions")
public class SubmissionResource {

    @Inject
    @DataSource("test")
    private AgroalDataSource defaultDataSource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Submission> index() throws SQLException {
        List<Submission> submissions = new ArrayList<>();
        PreparedStatement ps = this.runSQL("SELECT * FROM submissions;");
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            int id = rs.getInt(1);
            String imageUrl = rs.getString(2);
            String comment = rs.getString(3);
            String uploader = rs.getString(4);

            Submission submission = new Submission(id, imageUrl, comment, uploader);
            submissions.add(submission);
        }
        return submissions;
    }

    @GET
    @Consumes("text/plain")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Submission view(@PathParam("id") int submissionId) throws SQLException {
        PreparedStatement ps = this.runSQL("SELECT * FROM submissions WHERE id = ?;", new String[]{String.valueOf(submissionId)});
        ResultSet rs = ps.getResultSet();
        if (!rs.next()) {
            throw new SQLException();
        }
        int id = rs.getInt(1);
        String imageUrl = rs.getString(2);
        String comment = rs.getString(3);
        String uploader = rs.getString(4);

        return new Submission(id, imageUrl, comment, uploader);
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Submission submission) throws SQLException {
        PreparedStatement ps = this.runSQL("INSERT INTO submissions (imageUrl, comment, uploader) VALUES (?,?,?)", new String[]{submission.getImageUrl(), submission.getComment(), submission.getUploader()});
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int index = rs.getInt(1);
        return Response.status(201).entity(submission.setSubmissionId(index)).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(Submission submission) {
        // TODO implement update submission
        return null;
    }

    @DELETE
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(Submission submission) {
        // TODO implement delete submission
        return null;
    }

    public PreparedStatement runSQL(String sql) throws SQLException {
        return this.runSQL(sql, new String[0]);
    }

    public PreparedStatement runSQL(String sql, String[] params) throws SQLException {
        Connection conn = defaultDataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i+1, params[i]);
            System.out.println(params[i]);
        }

        preparedStatement.executeQuery();
        return preparedStatement;
    }

}