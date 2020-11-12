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

        try (Connection conn = defaultDataSource.getConnection()) {
            List<Submission> submissions = new ArrayList<>();
            PreparedStatement ps = this.runSQL(conn, "SELECT * FROM submissions;");
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int id = rs.getInt(1);
                String imageUrl = rs.getString(2);
                String comment = rs.getString(3);
                String uploader = rs.getString(4);
                String token = rs.getString(5);

                Submission submission = new Submission(id, imageUrl, comment, uploader, token);
                submissions.add(submission);
            }
            return submissions;
        }


    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Submission submission) throws SQLException {
        try {
            validateSubmission(submission, "POST");
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        try (Connection conn = defaultDataSource.getConnection()) {
            String token = Token.generateToken();
            submission.setToken(token);
            PreparedStatement ps = this.runSQL(conn,
                    "INSERT INTO submissions (imageUrl, comment, uploader, token) VALUES (?,?,?,?)",
                    new String[]{
                            submission.getImageUrl(),
                            submission.getComment(),
                            submission.getUploader(),
                            submission.getToken()
                    });
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int index = rs.getInt(1);
            submission.setSubmissionId(index);
            return Response.status(201).entity(submission).build();
        }

    }

    @GET
    @Consumes("*/*")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response view(@PathParam("id") int submissionId) throws SQLException {
        try (Connection conn = defaultDataSource.getConnection()) {
            PreparedStatement ps = this.runSQL(conn, "SELECT * FROM submissions WHERE id = ?;", new String[]{String.valueOf(submissionId)});
            ResultSet rs = ps.getResultSet();
            if (!rs.next()) {
                return Response.status(404).build();
            }
            int id = rs.getInt(1);
            String imageUrl = rs.getString(2);
            String comment = rs.getString(3);
            String uploader = rs.getString(4);

            return Response.status(200).entity(
                    new Submission(id, imageUrl, comment, uploader, null)
            ).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Submission submission, @PathParam("id") int submissionId) throws SQLException {
        try {
            validateSubmission(submission, "PUT");
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        try (Connection conn = defaultDataSource.getConnection()) {
            if (submissionId != submission.getSubmissionId()) {
                return Response.status(400).type("text/plain")
                        .entity("Bad request; Path ID does not match body ID").build();
            }

            PreparedStatement checkToken = this.runSQL(conn,
                    "SELECT token from submissions WHERE id = ?",
                    new String[]{
                            String.valueOf(submission.getSubmissionId())
                    }
            );

            ResultSet rsTokenCheck = checkToken.getResultSet();

            if (!rsTokenCheck.next()) {
                return Response.status(404).build();
            }

            if (!rsTokenCheck.getString(1).equals(submission.getToken())) {
                return Response.status(403).build();
            }

            PreparedStatement psInsert = this.runSQL(conn,
                    "UPDATE submissions SET imageUrl = ?, comment = ?, uploader = ? WHERE id = ? AND token = ?",
                    new String[]{
                            submission.getImageUrl(),
                            submission.getComment(),
                            submission.getUploader(),
                            String.valueOf(submission.getSubmissionId()),
                            submission.getToken()
                    }
            );

            return Response.status(200).entity(submission).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Submission submission, @PathParam("id") int submissionId) throws SQLException {
        try {
            validateSubmission(submission, "DELETE");
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        try (Connection conn = defaultDataSource.getConnection()) {
            if (submissionId != submission.getSubmissionId()) {
                return Response.status(400).type("text/plain")
                        .entity("Bad request; Path ID does not match body ID").build();
            }

            PreparedStatement checkToken = this.runSQL(conn,
                    "SELECT token from submissions WHERE id = ?",
                    new String[]{
                            String.valueOf(submission.getSubmissionId())
                    }
            );

            ResultSet rsTokenCheck = checkToken.getResultSet();

            if (!rsTokenCheck.next()) {
                return Response.status(404).build();
            }

            if (!rsTokenCheck.getString(1).equals(submission.getToken())) {
                return Response.status(403).build();
            }

            PreparedStatement psDelete = this.runSQL(conn,
                    "DELETE FROM submissions WHERE id = ?",
                    new String[]{
                            String.valueOf(submission.getSubmissionId()),
                    }
            );
            return Response.status(200).entity(submission).build();
        }

    }

    public void validateSubmission(Submission submission, String method) throws Exception {
        List<String> errors = new ArrayList<>();
        if (!(method.equals("GET") || method.equals("POST"))) {
            if (submission.getToken() == null || submission.getToken().equals("")) {
                errors.add("token must be set");
            }
        }

        if (submission.getImageUrl() == null || submission.getImageUrl().equals("")) {
            errors.add("imageUrl must be set");
        }
        if (submission.getUploader() == null || submission.getUploader().equals("")) {
            errors.add("uploader must be set");
        }
        if (submission.getComment() == null || submission.getComment().equals("")) {
            errors.add("comment must be set");
        }
        if (errors.size() > 0) {
            String errorMessages = errors.stream().reduce("", (s1, s2) -> {return s1 + "\n" + s2;});
            throw new Exception(errorMessages);
        }
    }

    public PreparedStatement runSQL(Connection conn, String sql) throws SQLException {
        return this.runSQL(conn, sql, new String[0]);
    }

    public PreparedStatement runSQL(Connection conn, String sql, String[] params) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i+1, params[i]);
            System.out.println(params[i]);
        }

        preparedStatement.executeQuery();
        return preparedStatement;
    }

}