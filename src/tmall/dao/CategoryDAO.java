package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDAO {
	/**
	 * 增加
	 * @param bean
	 */
	public void add(Category bean){
		String sql = "insert into category values(null,?)";
		try(Connection c = DBUtil.getConnection(); 
				PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, bean.getName());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()){
				int id = rs.getInt(1);//这里的1指的是返回的结果集的第一个字段
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id){
		try(Connection c = DBUtil.getConnection();
			Statement s = c.createStatement();) {
			String sql = "delete from category where id = " + id;
			s.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改
	 * @param bean
	 */
	public void update(Category bean){
		String sql = "update category set name= ? where id = ?";
		try(Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	public Category get(int id){
		Category bean = null;
		try(Connection c = DBUtil.getConnection();
			Statement s = c.createStatement();) {
			String sql = "select * from category where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				bean = new Category();
				String name = rs.getString(2);
				bean.setName(name);
				bean.setId(id);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 分页查询
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Category> list(int start,int count){
		List<Category> beans = new ArrayList<>();
		String sql = "select * from category order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);	) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Category bean = new Category();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				bean.setId(id);
				bean.setName(name);
				beans.add(bean);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return beans;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<Category> list(){
		return list(0,Short.MAX_VALUE);
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getTotal(){
		int total = 0;
		try(Connection c = DBUtil.getConnection();
			Statement s = c.createStatement();	) {
			
			String sql = "select count(*) from category";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return total;
	}
}
