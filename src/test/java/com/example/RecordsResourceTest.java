package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RecordsResourceTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "/records",
            "/records/with-type-instance/1",
            "/records/without-type-instance/1"}
    )
    public void test(String url) {
        given()
                .when().get(url)
                .then()
                .statusCode(200);
    }
}
