package red.test.dao;

import red.test.util.DataSource;
import red.test.model.Department;
import java.util.List;

public interface DepartmentDAO {
    public static final String SELECT_DEPARTMENT_BY_ID =
            "SELECT * FROM " + Department.TABLE_NAME + " WHERE " +Department.ID_COLUMN + "=?";
    public static final String SELECT_ALL_DEPARTMENTS = "SELECT * FROM " + Department.TABLE_NAME;
    public static final String SELECT_NEXT_ID = "SELECT NEXT VALUE FOR PK_SEQ FROM RDB$DATABASE";
    public static final String INSERT_DEPARTMENT =
            "INSERT INTO " + Department.TABLE_NAME +"(" + Department.ID_COLUMN + ", " + Department.NAME_COLUMN +
                    ", " + Department.PHONE_COLUMN + ", " + Department.EMAIL_COLUMN + ") VALUES (?, ?, ?, ?)";
    public static final String UPDATE_DEPARTMENT =
            "UPDATE " + Department.TABLE_NAME + " SET " + Department.NAME_COLUMN + "= ?, " +
                    Department.PHONE_COLUMN + "= ?, " + Department.EMAIL_COLUMN + "=? WHERE " +
                    Department.ID_COLUMN + "= ?";
    public static final String DELETE_FROM_DEPARTMENT =
            "DELETE FROM " + Department.TABLE_NAME + " WHERE " + Department.ID_COLUMN + "= ?";

    public void setDataSource(DataSource dataSource);

    Department getDepartment(int id);
    List<Department> getAllDepartments();
    void insertDepartment(Department dep);
    void updateDepartment(Department dep);
    void deleteDepartment(Department dep);
}
