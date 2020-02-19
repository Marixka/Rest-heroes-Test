
import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.qameta.allure.*;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Validatable;
import io.restassured.response.Response;
import org.junit.*;


import java.util.List;

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

    //create new Heroes
    Superheroes expected  = generateSuperheroes();
    //Send POST
    Response createHeroes = REQUEST
            .body(expected)
            .post("/superheroes");
    createHeroes
            .then()
            .assertThat().statusCode(200);
    //Extract created user ID from the response
    expected.id = createHeroes.jsonPath().getInt("id");

// Get Superheroes


    @Test
    @Feature("GET superheroes")
    @Story("Передан ID")
    @DisplayName("getSuperheroe - позитивный сценарий")
    public void Get0001() throws Exception {
        Superheroes actual = REQUEST.get("/superheroes/"+expected.id).as(Superheroes.class);
        assertThat(expected).isEqualToComparingFieldByField(actual);


    }



    private Superheroes generateSuperheroes(){
        Integer id = faker.number().randomDigitNotZero();
        String fullName = faker.superhero().name();
        Date birthDate = faker.date().birthday();
        String city = faker.address().city();
        String mainSkill = faker.superhero().power();
        String gender = faker.demographic().sex();
        String phone = faker.phoneNumber().phoneNumber();

        return new Superheroes(id, fullName, birthDate, city, mainSkill, gender, phone);

    }
}
