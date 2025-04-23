package io.nology.postcode_backend.postcode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;

import io.nology.postcode_backend.common.BaseEndToEndTest;
import io.nology.postcode_backend.fixtures.PostcodeFixture;
import io.nology.postcode_backend.suburb.Suburb;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(OutputCaptureExtension.class)
public class PostcodeEndToEndTest extends BaseEndToEndTest<PostcodeFixture> {

    @Autowired
    public PostcodeEndToEndTest(PostcodeFixture fixture) {
        super(fixture);
    }

    @Test
    public void getAllReturnsListOfPostcodes() {
        given()
                .when()
                .get("/postcode")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/postcode-array-schema.json"));

    }

    @Test
    public void getByPostcodeReturnsPostcode() {
        String postcodeString = getFixture().getPostcodeWithSuburb().getPostcode();
        given()
                .when()
                .get("/postcode/" + postcodeString)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/postcode-schema.json"));
    }

    @Test
    public void getByPostcodeDoesNotWorkWithAnInvalidPostcode() {
        String postcodeString = "abcd";
        given()
                .when()
                .get("/postcode/" + postcodeString)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void createPostcodeCannotBeAccessedWithNoAuth() {
        CreatePostcodeDTO body = new CreatePostcodeDTO();
        body.setPostcode("1234");
        given()
                .header("Authorization", "Bearer ")
                .contentType(ContentType.JSON)
                .body(body)
                .post("/admin/postcode")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void createPostcodeCreatesPostcode() {

        Response res = given()
                .auth()
                .preemptive()
                .basic("testuser", "password")
                .post("/auth/login");

        CreatePostcodeDTO body = new CreatePostcodeDTO();
        body.setPostcode("1234");
        given()
                .header("Authorization", "Bearer " + res.asString())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/admin/postcode")
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void deletePostcodeCannotBeAccessedWithNoAuth() {
        String postcodeString = getFixture().getPostcodeWithSuburb().getPostcode();
        given()
                .header("Authorization", "Bearer ")
                .contentType(ContentType.JSON)
                .delete("/admin/postcode/" + postcodeString)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void deletePostcodeRemovesPostcode() {
        String postcodeString = getFixture().getPostcodeWithSuburb().getPostcode();
        Response res = given()
                .auth()
                .preemptive()
                .basic("testuser", "password")
                .post("/auth/login");
        String token = res.asString();

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .delete("/admin/postcode/" + postcodeString)
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void updatePostcodeCannotBeAccessedWithNoAuth() {
        String postcodeString = getFixture().getPostcodeWithSuburb().getPostcode();
        given()
                .header("Authorization", "Bearer ")
                .contentType(ContentType.JSON)
                .post("/admin/postcode/" + postcodeString)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updatePostcodeUpdatesPostcode() {
        String currPostcode = getFixture().getPostcodeWithSuburb().getPostcode();
        Long currSuburbId = getFixture().getSuburb().getId();
        Set<Long> currIds = new HashSet<>(Arrays.asList(currSuburbId));
        String newPostcode = "0000".equals(currPostcode) ? "0001" : "0000";
        String token = given()
                .auth()
                .preemptive()
                .basic("testuser", "password")
                .post("/auth/login").asString();
        UpdatePostcodeDTO body = new UpdatePostcodeDTO();
        body.setPostcode(newPostcode);
        body.setSuburbIds(currIds);
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/admin/postcode/" + currPostcode)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("postcode", equalTo(newPostcode));
    }

    @Test
    public void updatePostcodeReplacesSuburbs() {
        String currPostcode = getFixture().getPostcodeWithSuburb().getPostcode();
        Long newSuburbId = getFixture().getAnotherSuburb().getId();
        Set<Long> currIds = new HashSet<>(Arrays.asList(newSuburbId));
        String token = given()
                .auth()
                .preemptive()
                .basic("testuser", "password")
                .post("/auth/login").asString();
        UpdatePostcodeDTO body = new UpdatePostcodeDTO();
        body.setPostcode(currPostcode);
        body.setSuburbIds(currIds);

        List<Map<String, String>> suburbs = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/admin/postcode/" + currPostcode)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body(matchesJsonSchemaInClasspath("schemas/postcode-schema.json"))
                .body("postcode", equalTo(currPostcode))
                .extract().path("suburbs");
        assertThat(suburbs)
                .extracting("suburbId")
                .contains((int) newSuburbId.longValue());
    }

    @Test
    public void updatePostcodeWithAnEmptySuburbIdsFieldRemovesSuburbs() {
        String currPostcode = getFixture().getPostcodeWithSuburb().getPostcode();
        Set<Long> currIds = new HashSet<>();

        String token = given()
                .auth()
                .preemptive()
                .basic("testuser", "password")
                .post("/auth/login").asString();
        UpdatePostcodeDTO body = new UpdatePostcodeDTO();
        body.setPostcode(currPostcode);
        body.setSuburbIds(currIds);

        List<Map<String, String>> suburbs = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/admin/postcode/" + currPostcode)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body(matchesJsonSchemaInClasspath("schemas/postcode-schema.json"))
                .body("postcode", equalTo(currPostcode))
                .extract().path("suburbs");
        assertThat(suburbs.isEmpty());
    }
}
