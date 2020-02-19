package model;

import java.util.Date;

public class Superheroes {


        public Integer id;
        public String fullName;
        public String birthDate;
        public String city;
        public String mainSkill;
        public String gender;
        public String phone;

        public Superheroes(){
        }

        public Superheroes(Integer id, String fName, String bDate, String city, String mainSkill, String gender, String phone ){
            this.id = id;
            if (fName!= null) this.fullName = fName;
            if (bDate!= null) this.birthDate = bDate;
            if (city!= null) this.city = city;
            if (mainSkill!= null) this.mainSkill = mainSkill;
            if (gender!= null) this.gender = gender;
            if (phone!= null) this.phone = phone;

        }




}
