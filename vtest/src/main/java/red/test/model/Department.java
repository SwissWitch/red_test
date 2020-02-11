package red.test.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Department {
    public static final String TABLE_NAME = "DEPARTMENT";
    public static final String ID_COLUMN = "DEP_ID";
    public static final String NAME_COLUMN = "NAME";
    public static final String PHONE_COLUMN = "PHONE";
    public static final String EMAIL_COLUMN = "EMAIL";

    private int depId = 0;
    @NotEmpty
    private String name;
    @NotEmpty
    private String phone;
    @NotEmpty
    @Email
    private String email;

    public Department() {
    }

    public Department(int depId, String name, String phone, String email) {
        this.depId = depId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
