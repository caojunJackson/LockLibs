package caojun.com.logintest.face;

import java.util.List;

/**
 * Created by tiger on 2017/3/15.
 */

public class UserInfo {


    /**
     * phone : 18621309475
     * name : cj
     * email :
     * avatar : https://dn-spapi1.qbox.me/avatar/2017/03/15/dx2wbuuqfvy56gzq.jpg
     * regioncode : 86
     * persona : {"gender":"male","tags":["eyeglasses"],"location":{"country":"CN",
     * "province":"上海","city":"上海"},"generation":"10s","character":""}
     * group_uid : ff89a9cb5ea9f261d4743e444441e32c
     */

    private String phone;
    private String name;
    private String email;
    private String avatar;
    private String regioncode;
    /**
     * gender : male
     * tags : ["eyeglasses"]
     * location : {"country":"CN","province":"上海","city":"上海"}
     * generation : 10s
     * character :
     */

    private PersonaBean persona;
    private String group_uid;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public PersonaBean getPersona() {
        return persona;
    }

    public void setPersona(PersonaBean persona) {
        this.persona = persona;
    }

    public String getGroup_uid() {
        return group_uid;
    }

    public void setGroup_uid(String group_uid) {
        this.group_uid = group_uid;
    }

    public static class PersonaBean {
        private String gender;
        /**
         * country : CN
         * province : 上海
         * city : 上海
         */

        private LocationBean location;
        private String generation;
        private String character;
        private List<String> tags;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getGeneration() {
            return generation;
        }

        public void setGeneration(String generation) {
            this.generation = generation;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public static class LocationBean {
            private String country;
            private String province;
            private String city;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }
    }
}
