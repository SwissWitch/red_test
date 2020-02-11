package red.test.dao.impl;

import red.test.dao.DepartmentDAO;
import red.test.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import red.test.util.DataSource;

public class DepartmentDAOImpl implements DepartmentDAO{
    private DataSource dataSource;

    private Department getDepartmenFromResultSet(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setDepId( rs.getInt(Department.ID_COLUMN) );
        dep.setName( rs.getString(Department.NAME_COLUMN) );
        dep.setPhone( rs.getString(Department.PHONE_COLUMN) );
        dep.setEmail( rs.getString(Department.EMAIL_COLUMN) );
        return dep;
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

    public Department getDepartment(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_DEPARTMENT_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return getDepartmenFromResultSet(rs);
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

    public List<Department> getAllDepartments() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_DEPARTMENTS);
            List<Department> deps = new ArrayList<Department>();
            while(rs.next())
            {
                Department dep = getDepartmenFromResultSet(rs);
                deps.add(dep);
            }
            return deps;
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

    public void insertDepartment(Department dep) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_DEPARTMENT);
            int id = getNewId();
            ps.setInt(1, id);
            ps.setString(2, dep.getName());
            ps.setString(3, dep.getPhone());
            ps.setString(4, dep.getEmail());
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

    public void updateDepartment(Department dep) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_DEPARTMENT);
            ps.setString(1, dep.getName());
            ps.setString(2, dep.getPhone());
            ps.setString(3, dep.getEmail());
            ps.setInt(4, dep.getDepId());
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

    public void deleteDepartment(Department dep) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_FROM_DEPARTMENT);
            ps.setInt(1, dep.getDepId());
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
}
