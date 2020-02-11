package red.test.dao.impl;

import red.test.dao.EmployeeDAO;
import red.test.model.Department;
import red.test.model.Employee;
import red.test.model.Position;
import red.test.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private DataSource dataSource;

    private Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmpId( rs.getInt(Employee.ID_COLUMN) );
        emp.setName( rs.getString(EMP_NAME) );
        emp.setSurname( rs.getString(Employee.SURNAME_COLUMN) );
        emp.setFamilyname( rs.getString(Employee.FAMILYNAME_COLUMN) );
        emp.setDepartmentname(rs.getString(DEP_NAME));
        emp.setPositionname(rs.getString(POS_NAME));
        Department dep = new Department();
        dep.setDepId(rs.getInt(Employee.DEPARTMENT_COLUMN));
        dep.setName( rs.getString(DEP_NAME) );
        emp.setDepartment(dep);
        Position pos = new Position();
        pos.setPosId(rs.getInt(Employee.POSITION_COLUMN));
        pos.setName( rs.getString(POS_NAME) );
        emp.setPosition(pos);
        return emp;
    }

    private int getNewId() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_NEXT_ID);
            if(rs.next())
            {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Employee getEmployee(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return getEmployeeFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    };

    public List<Employee> getAllEmployees() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_EMPLOYEES);
            List<Employee> emps = new ArrayList<>();
            while(rs.next())
            {
                Employee emp = getEmployeeFromResultSet(rs);
                emps.add(emp);
            }
            return emps;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    };

    public void insertEmployee(Employee emp) {
        System.out.println("insert");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_EMPLOYEE);
            int id = getNewId();
            ps.setInt(1, id);
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getSurname());
            ps.setString(4, emp.getFamilyname());
            ps.setInt(5, emp.getDepartment().getDepId());
            ps.setInt(6, emp.getPosition().getPosId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void updateEmployee(Employee emp) {
        System.out.println("update");
        System.out.println(UPDATE_EMPLOYEE);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_EMPLOYEE);
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getSurname());
            ps.setString(3, emp.getFamilyname());
            ps.setInt(4, emp.getDepartment().getDepId());
            ps.setInt(5, emp.getPosition().getPosId());
            ps.setInt(6, emp.getEmpId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void deleteEmployee(Employee emp) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_FROM_EMPLOYEE);
            ps.setInt(1, emp.getEmpId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public String getSql() {
        return SELECT_ALL_EMPLOYEES;
    };

}
