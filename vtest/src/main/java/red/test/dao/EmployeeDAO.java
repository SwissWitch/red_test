package red.test.dao;

import red.test.model.Department;
import red.test.model.Employee;
import red.test.model.Position;
import red.test.util.DataSource;

import java.util.List;

public interface EmployeeDAO {
    public static final String POS_NAME = "POS_NAME";
    public static final String EMP_NAME = "EMP_NAME";
    public static final String DEP_NAME = "DEP_NAME";

    public static final String SELECT_ALL_EMPLOYEES =
            "SELECT " + Employee.ID_COLUMN + ", "
                    + Employee.TABLE_NAME + "." + Employee.NAME_COLUMN + " AS " + EMP_NAME + ", "
                    + Employee.SURNAME_COLUMN + ", "
                    + Employee.FAMILYNAME_COLUMN + ", "
                    + Department.TABLE_NAME + "." + Department.ID_COLUMN + ", "
                    + Department.TABLE_NAME + "." + Department.NAME_COLUMN + " AS " + DEP_NAME + ", "
                    + Position.TABLE_NAME + "." + Position.ID_COLUMN + ", "
                    + Position.TABLE_NAME + "." + Position.NAME_COLUMN + " AS " + POS_NAME
                    + " FROM " + Employee.TABLE_NAME
                    + " JOIN " + Department.TABLE_NAME
                    + " ON " + Employee.TABLE_NAME + "." + Employee.DEPARTMENT_COLUMN
                    + " = " + Department.TABLE_NAME + "." + Department.ID_COLUMN
                    + " JOIN " + Position.TABLE_NAME
                    + " ON " + Employee.TABLE_NAME + "." + Employee.POSITION_COLUMN
                    + " = " + Position.TABLE_NAME + "." + Position.ID_COLUMN;

    public static final String SELECT_EMPLOYEE_BY_ID =
            SELECT_ALL_EMPLOYEES + " WHERE " +Employee.ID_COLUMN + "=?";

    public static final String SELECT_NEXT_ID = "SELECT NEXT VALUE FOR PK_SEQ FROM RDB$DATABASE";
    public static final String INSERT_EMPLOYEE =
            "INSERT INTO " + Employee.TABLE_NAME +"(" + Employee.ID_COLUMN +
                    ", " + Employee.NAME_COLUMN +
                    ", " + Employee.SURNAME_COLUMN +
                    ", " + Employee.FAMILYNAME_COLUMN +
                    ", " + Employee.DEPARTMENT_COLUMN +
                    ", " + Employee.POSITION_COLUMN
                    + ") VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_EMPLOYEE =
            "UPDATE " + Employee.TABLE_NAME + " SET "
                    + Employee.NAME_COLUMN + "= ?, "
                    + Employee.SURNAME_COLUMN + "= ?, "
                    + Employee.FAMILYNAME_COLUMN + "= ?, "
                    + Employee.DEPARTMENT_COLUMN + "= ?, "
                    + Employee.POSITION_COLUMN + "= ? "
                    + "WHERE " +
                    Employee.ID_COLUMN + "= ?";
    public static final String DELETE_FROM_EMPLOYEE =
            "DELETE FROM " + Employee.TABLE_NAME + " WHERE " + Employee.ID_COLUMN + "= ?";

    public void setDataSource(DataSource dataSource);

    Employee getEmployee(int id);
    List<Employee> getAllEmployees();
//    List<Employee> getEmployeesByDepartment();
//    List<Employee> getEmployeesByDepartmentPosition();
    void insertEmployee(Employee emp);
    void updateEmployee(Employee emp);
    void deleteEmployee(Employee emp);
}


