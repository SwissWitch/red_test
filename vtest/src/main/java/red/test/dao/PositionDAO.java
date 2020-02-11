package red.test.dao;

import red.test.model.Department;
import red.test.model.Employee;
import red.test.model.Position;
import red.test.util.DataSource;

import java.util.List;

public interface PositionDAO {
    public static final String SELECT_POSITION_BY_ID =
            "SELECT * FROM " + Position.TABLE_NAME + " WHERE " +Position.ID_COLUMN + "=?";
    public static final String SELECT_ALL_POSITIONS = "SELECT * FROM " + Position.TABLE_NAME;
    public static final String SELECT_NEXT_ID = "SELECT NEXT VALUE FOR PK_SEQ FROM RDB$DATABASE";
    public static final String INSERT_POSITION =
            "INSERT INTO " + Position.TABLE_NAME +"(" + Position.ID_COLUMN + ", " + Position.NAME_COLUMN +
                    ", " + Position.SALARY_COLUMN +
                    ", " + Position.CHIEF_COLUMN +
                    ") VALUES (?, ?, ?, ?)";
    public static final String UPDATE_POSITION =
            "UPDATE " + Position.TABLE_NAME + " SET "
                    + Position.NAME_COLUMN + "= ?, " +
                    Position.SALARY_COLUMN + "= ?, " +
                    Position.CHIEF_COLUMN + "=? WHERE " +
                    Position.ID_COLUMN + "= ?";
    public static final String DELETE_FROM_POSITION =
            "DELETE FROM " + Position.TABLE_NAME + " WHERE " + Position.ID_COLUMN + "= ?";

    public static final String CHECK_CHIEF = "SELECT * FROM " + Employee.TABLE_NAME +
            " JOIN " + Position.TABLE_NAME +
            " ON " + Position.TABLE_NAME + "." + Position.ID_COLUMN + "="
            + Employee.TABLE_NAME + "." + Employee.POSITION_COLUMN
            + " AND " + Position.CHIEF_COLUMN + "='Y'"
            + " AND " + Position.TABLE_NAME + "." + Position.ID_COLUMN + "=?"
            + " JOIN " + Department.TABLE_NAME +
            " ON " + Department.TABLE_NAME + "." + Department.ID_COLUMN + "="
            + Employee.TABLE_NAME + "." + Employee.DEPARTMENT_COLUMN
            + " AND " + Department.TABLE_NAME + "." + Department.ID_COLUMN + "=?";


    public void setDataSource(DataSource dataSource);

    Position getPosition(int id);
    List<Position> getAllPositions();
    void insertPosition(Position pos);
    void updatePosition(Position pos);
    void deletePosition(Position pos);
    boolean checkChief(Employee emp);

}
