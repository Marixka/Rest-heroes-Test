
import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.qameta.allure.*;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Validatable;
import io.restassured.response.Response;
import org.junit.*;


import java.util.List;

import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import model.Superheroes;
import static org.junit.Assert.assertEquals;


import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GetSuperheroes extends BaseRest {
    private static final String basePath = "/superheroes/7";
    Faker faker = new Faker();
    public Superheroes expected;



    @Test
    @Feature("GET superheroes")
    @Story("Передан ID")
    @DisplayName("getSuperheroe - позитивный сценарий")
    public void Get0001() throws Exception {
        expected  = generateSuperheroes(
                true,true,true,true,true,true,true);
        postHeroes();
        //GET запрос и проверка
        Superheroes actual = REQUEST.get("/superheroes/"+expected.id).as(Superheroes.class);
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    @Test
    @Feature("GET superheroes")
    @Story("Не передан phone")
    @DisplayName("getSuperheroe - позитивный сценарий")
    public void Get0002() throws Exception {
        expected  = generateSuperheroes(
                true, true, true, true, true, true, false);
        postHeroes();
        //GET запрос и проверка
        Superheroes actual = REQUEST.get("/superheroes/"+expected.id).as(Superheroes.class);
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }




    private Superheroes generateSuperheroes(
            boolean idB, boolean fN,
            boolean bD, boolean cityB, boolean mS,
            boolean gB, boolean phoneB)
    {
        Integer id = null;
        String fullName= null;
        String birthDate= null;
        String city= null;
        String mainSkill= null;
        String gender= null;
        String phone= null;
        if (idB) id = faker.number().randomDigitNotZero();
        if (fN) fullName = faker.superhero().name();
        if (bD) birthDate = "2019-02-21";
        if (cityB) city = faker.address().city();
        if (mS) mainSkill = faker.superhero().power();
        if (gB) gender = faker.demographic().sex();
        if (phoneB) phone = "+74998884433";

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
        Thread.sleep(1000);
    }

}
