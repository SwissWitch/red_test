package red.test.model;

import javax.validation.constraints.NotEmpty;

public class Position {
    public static final String TABLE_NAME = "POSITIONS";
    public static final String ID_COLUMN = "POS_ID";
    public static final String NAME_COLUMN = "NAME";
    public static final String SALARY_COLUMN = "SALARY";
    public static final String CHIEF_COLUMN = "CHIEF";

    private int posId;
    @NotEmpty
    private String name;
    @NotEmpty
    private int salary;
    private boolean chief;

    public Position() {
    }

    public Position(int posId, String name, int salary, boolean chief) {
        this.posId = posId;
        this.name = name;
        this.salary = salary;
        this.chief = chief;
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChief() {
        return chief;
    }

    public void setChief(boolean chief) {
        this.chief = chief;
    }

}
