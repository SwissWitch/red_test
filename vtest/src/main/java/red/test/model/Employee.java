package red.test.model;

import javax.validation.constraints.NotEmpty;

public class Employee {
    public static final String TABLE_NAME = "EMPLOYEE";
    public static final String ID_COLUMN = "EMP_ID";
    public static final String NAME_COLUMN = "NAME";
    public static final String SURNAME_COLUMN = "SURNAME";
    public static final String FAMILYNAME_COLUMN = "FAMILYNAME";
    public static final String DEPARTMENT_COLUMN = "DEP_ID";
    public static final String POSITION_COLUMN = "POS_ID";

    private int empId;
    @NotEmpty
    private String name;
    private String surname;
    @NotEmpty
    private String familyname;
    @NotEmpty
    private Department department;
    @NotEmpty
    private Position position;

    private String departmentname;

    private String positionname;

    public Employee() {

    }

    public Employee(int empId, String name, String surname, String familyname, boolean chief,
                    Department department, Position position) {
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.familyname = familyname;
        this.department = department;
        this.position = position;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String name) {
        this.departmentname = name;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String name) {
        this.positionname = name;
    }
}
