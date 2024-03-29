package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {
	
	/**
	 * 获取总数
	 * @return
	 */
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
  
            String sql = "select count(*) from Review";
  
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return total;
    }
    
    /**
     * 根据产品id获取评价总数
     * @param pid
     * @return
     */
    public int getTotal(int pid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
             
            String sql = "select count(*) from Review where pid = " + pid;
             
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
             
            e.printStackTrace();
        }
        return total;
    }
  
    /**
     * 增加
     * @param bean
     */
    public void add(Review bean) {
 
        String sql = "insert into Review values(null,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setString(1, bean.getContent());
            ps.setInt(2, bean.getUser().getId());
            ps.setInt(3, bean.getProduct().getId());
            ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
             
            ps.execute();
  
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
    }
    
    /**
     * 修改
     * @param bean
     */
    public void update(Review bean) {
 
        String sql = "update Review set content= ?, uid=?, pid=? , createDate = ? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, bean.getContent());
            ps.setInt(2, bean.getUser().getId());
            ps.setInt(3, bean.getProduct().getId());
            ps.setTimestamp(4, DateUtil.d2t( bean.getCreateDate()) );
            ps.setInt(5, bean.getId());
            ps.execute();
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
  
    }
    
    /**
     * 删除
     * @param id
     */
    public void delete(int id) {
  
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
  
            String sql = "delete from Review where id = " + id;
  
            s.execute(sql);
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
    }
    
    /**
     * 根据id获取
     * @param id
     * @return
     */
    public Review get(int id) {
        Review bean = new Review();
  
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
  
            String sql = "select * from Review where id = " + id;
  
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                 
                String content = rs.getString("content");
                 
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                 
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setId(id);
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return bean;
    }
    
    /**
     * 查询总数：获取指定产品的评价
     * @param pid
     * @return
     */
    public List<Review> list(int pid) {
        return list(pid, 0, Short.MAX_VALUE);
    }
    
    /**
     * 获取指定产品一共有多少条评价
     * @param pid
     * @return
     */
    public int getCount(int pid) {
        String sql = "select count(*) from Review where pid = ? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
               return rs.getInt(1);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return 0;      
    }
    /**
     * 分页查询：获取指定产品的评价
     * @param pid
     * @param start
     * @param count
     * @return
     */
    public List<Review> list(int pid, int start, int count) {
        List<Review> beans = new ArrayList<Review>();
  
        String sql = "select * from Review where pid = ? order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, pid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                Review bean = new Review();
                int id = rs.getInt(1);
 
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                 
                String content = rs.getString("content");
                 
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                 
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setId(id);    
                bean.setProduct(product);
                bean.setUser(user);
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
    /**
     * 
     * @param content
     * @param pid
     * @return
     */
    public boolean isExist(String content, int pid) {
         
        String sql = "select * from Review where content = ? and pid = ?";
         
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, content);
            ps.setInt(2, pid);
             
            ResultSet rs = ps.executeQuery();
  
            if (rs.next()) {
                return true;
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
 
        return false;
    }	
}
