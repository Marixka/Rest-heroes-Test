
import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.qameta.allure.*;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;


import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;

import model.Superheroes;
import static org.junit.Assert.assertEquals;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GetSuperheroes extends BaseRest {
    @Before public void initialize() throws InterruptedException {
        expected  = generateSuperheroes();
        postHeroes();
    }

    public static final String basePath = "/superheroes/";
    Faker faker = new Faker();
    public Superheroes expected;


    @Test
    @Feature("GET superheroes")
    @Story("Передан ID")
    @DisplayName("getSuperheroe - позитивный сценарий")
    public void Get0001() throws Exception {
        //GET запрос и проверка
        Superheroes actual = REQUEST.get(basePath + expected.id).as(Superheroes.class);
        assertThat(expected).isEqualToIgnoringNullFields(actual);
        assertThat(actual)
                .extracting("fullName", "birthDate", "city", "mainSkill","gender");
    }

    @Test
    @Feature("GET superheroes")
    @Story("Несуществущий герой")
    @DisplayName("getSuperheroe - негативный сценарий")
    public void Get0002() throws Exception {
        //GET запрос и проверка
        String heroId = "6565fff";
        REQUEST.get(basePath + heroId)
                .then()
                .statusCode(404)
                .and()
                .body("message", equalTo("Superhero with id '" + heroId + "' was not found"))
                .body("code", equalTo("NOT_FOUND"));
    }
    //При условии, что будет аутентификация
    @Test
    @Feature("GET superheroes")
    @Story("Отказ в авторизации")
    @DisplayName("getSuperheroe - негативный сценарий")
    public void Get0003() throws Exception {
        //GET запрос и проверка

        given().header("Authorization", "MyAppName").when().get("/superheroes/1")
                .then().statusCode(401)
                .and()
                .body("code", equalTo("UNAUTHORIZED"));
    }

    //-----------------------------

    private Superheroes generateSuperheroes()
    {
        Integer id = null;
        String fullName = faker.superhero().name();
        String birthDate = "2019-02-21";
        String city = faker.address().city();
        String mainSkill = faker.superhero().power();
        String gender = faker.demographic().sex();
        String phone = "+74998884433";
        return new Superheroes(id, fullName, birthDate, city, mainSkill, gender, phone);

    }

    private void postHeroes() throws InterruptedException {
        //Send POST
        Response createHeroes = REQUEST
                .body(expected)
                .post("/superheroes");
        createHeroes
                .then()
                .assertThat().statusCode(200);
        //Extract created heroe ID from the response
        expected.id = createHeroes.jsonPath().getInt("id");

    }

}
