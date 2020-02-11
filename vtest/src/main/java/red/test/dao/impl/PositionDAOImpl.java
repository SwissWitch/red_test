package red.test.dao.impl;

import red.test.dao.PositionDAO;
import red.test.model.Employee;
import red.test.model.Position;
import red.test.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAOImpl implements PositionDAO {
    private DataSource dataSource;

    private Position getPositionFromResultSet(ResultSet rs) throws SQLException {
        Position pos = new Position();
        pos.setPosId( rs.getInt(Position.ID_COLUMN) );
        pos.setName( rs.getString(Position.NAME_COLUMN) );
        pos.setSalary(rs.getInt(Position.SALARY_COLUMN) );
        pos.setChief(rs.getBoolean(Position.CHIEF_COLUMN) );
        return pos;
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

    public Position getPosition(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_POSITION_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return getPositionFromResultSet(rs);
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

    public List<Position> getAllPositions() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_POSITIONS);
            List<Position> poss = new ArrayList<Position>();
            while(rs.next())
            {
                Position pos = getPositionFromResultSet(rs);
                poss.add(pos);
            }
            return poss;
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

    public void insertPosition(Position pos) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_POSITION);
            int id = getNewId();
            ps.setInt(1, id);
            ps.setString(2, pos.getName());
            ps.setInt(3, pos.getSalary());
            ps.setBoolean(4, pos.isChief());
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

    public void updatePosition(Position pos) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_POSITION);
            ps.setString(1, pos.getName());
            ps.setInt(2, pos.getSalary());
            ps.setBoolean(3, pos.isChief());
            ps.setInt(4, pos.getPosId());
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

    public void deletePosition(Position dep) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_FROM_POSITION);
            ps.setInt(1, dep.getPosId());
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

    public boolean checkChief(Employee emp) {
        Connection connection = null;
        Position pos = getPosition(emp.getPosition().getPosId());
        if(pos != null && pos.isChief()) {
            try {
                connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(CHECK_CHIEF);
                stmt.setInt(1, emp.getPosition().getPosId());
                stmt.setInt(2, emp.getDepartment().getDepId());
                ResultSet rs = stmt.executeQuery();
                if(rs.next())
                {
                    return true;
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
        }

        return false;

    }
}
