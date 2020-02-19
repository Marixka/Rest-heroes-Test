package model;

import java.util.Date;

public class Superheroes {


        public Integer id;
        public String fullName;
        public Date birthDate;
        public String city;
        public String mainSkill;
        public String gender;
        public String phone;

        public Superheroes(){
        }

        public Superheroes(Integer id, String fName, Date bDate, String city, String mainSkill, String gender, String phone ){
            this.id = id;
            this.fullName = fName;
            this.birthDate = bDate;
            this.city = city;
            this.mainSkill = mainSkill;
            this.gender = gender;
            this.phone = phone;

        }




}
