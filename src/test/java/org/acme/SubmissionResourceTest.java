package org.acme;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SubmissionResourceTest {

    @Inject
    @DataSource("test")
    AgroalDataSource testDataSource;

    @Test
    public void testDatabaseConnection() throws SQLException {
        Connection connection = testDataSource.getConnection();
        assertThat(true).isTrue().as("Database connection established");
    }

    @Test
    public void testIndexEndpoint() {
        given()
          .when().get("/")
          .then()
             .statusCode(200);
    }

    @Test
    public void testIndexSubmissionEndpoint() {
        given()
                .when().get("/submissions")
                .then()
                .statusCode(200)
                .body(is("[{\"comment\":\"This is a comment\",\"imageUrl\":\"google.de\",\"submissionId\":1,\"uploader\":\"van der Zee\"}]"));
    }

    @Test
    public void testShowEndpoint() {
        given()
                .when().get("/submissions/1")
                .then()
                .statusCode(200)
                .body(is("{\"comment\":\"This is a comment\",\"imageUrl\":\"google.de\",\"submissionId\":1,\"uploader\":\"van der Zee\"}"));
    }

    @Test
    public void testCreateEndpoint() {
        RequestSpecification request = RestAssured.given();

        JSONObject submission = new JSONObject();
        submission.put("imageUrl", "amazon.com");
        submission.put("comment", "this is a sample comment");
        submission.put("uploader", "test uploader");

        request.header("Content-Type", "application/json");
        request.body(submission.toJSONString());
        Response response = request.post("/submissions");
        assertThat(response.getStatusCode()).isEqualTo(201);
    }

    @Test
    public void testUpdateEndpoint() {
        // TODO implement
    }

    @Test
    public void testDeleteEndpoint() {
        // TODO implement
    }

}