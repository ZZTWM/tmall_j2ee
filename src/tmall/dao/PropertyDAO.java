package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {
	/**
	 * ��ȡĳ�ַ����µ������������ڷ�ҳ��ʾ��ʱ����õ�
	 * @param cid
	 * @return
	 */
	public int getTotal(int cid){// PropertyDao ֮����Ҫ����cid������ΪҪ��ĳ�������µ����Խ��з�ҳ������Ҫ����� �����µ���������
		int total = 0;
		
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement()) {
			String sql = "select count(*) from property where cid = " + cid;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return total;
	}
	/**
	 * ����
	 * @param bean
	 */
	public void add(Property bean){
		String sql = "insert into property values(null,?,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * �޸�
	 * @param bean
	 */
	public void update(Property bean){
		String sql = "update property set cid = ? , name = ? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ɾ��
	 * @param id
	 */
	public void delete(int id){
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();) {
			String sql = "delete from property where id = " + id;
			s.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	/**
	 * ����id��ȡ
	 * @param id
	 * @return
	 */
	public Property get(int id){
		Property bean = new Property();
		
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select * from property where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				String name = rs.getString("name");
				int cid = rs.getInt("cid");
				bean.setName(name);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				bean.setId(id);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return bean;
	}
	
	/**
	 * ��ѯȫ������ѯ���������µ����Զ���
	 * @param cid
	 * @return
	 */
	public List<Property> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
	}
	
	/**
	 * ��ҳ��ѯ����ѯĳ�������µ����Զ���
	 * @param cid
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Property> list(int cid,int start,int count){
		List<Property> beans = new ArrayList<Property>();
		String sql = "select * from property where cid = ? order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Property bean = new Property();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				bean.setName(name);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				bean.setId(id);
				beans.add(bean);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return beans;
	}
	
}






















