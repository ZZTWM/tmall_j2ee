package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDAO {
	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getTotal(){
		int total = 0;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select count(*) from productimage";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 增加
	 * @param bean
	 */
	public void add(ProductImage bean){
		String sql = "insert into productimage values(null,?,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
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
	 * 修改
	 * @param bean
	 */
	public void update(ProductImage bean){
		
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id){
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();) {
			String sql = "delete from productimage where id = " + id;
			s.execute(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public ProductImage get(int id){
		ProductImage bean = new ProductImage();
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select * from productimage where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				int pid = rs.getInt("pid");
				String type = rs.getString("type");
				Product product = new ProductDAO().get(pid);
				bean.setProduct(product);
				bean.setType(type);
				bean.setId(id);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return bean;
	}
	
	/**
	 * 查询全部：查询指定产品下，某种类型的ProductImage
	 * @param p
	 * @param type
	 * @return
	 */
	public List<ProductImage> list(Product p,String type){
		return list(p,type,0,Short.MAX_VALUE);
	}
	
	/**
	 * 分页查询：查询指定产品下，某种类型的ProductImage
	 * @param p
	 * @param type
	 * @param start
	 * @param count
	 * @return
	 */
	public List <ProductImage> list(Product p,String type,int start,int count){
		List<ProductImage> beans = new ArrayList<ProductImage>();
		
		String sql = "select * from ProductImage where pid = ? and type = ? order by id desc limit ?,?";
		
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, p.getId());
			ps.setString(2, type);
			ps.setInt(3, start);
			ps.setInt(4, count);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				ProductImage bean = new ProductImage();
				int id = rs.getInt(1);
				
				bean.setProduct(p);
				bean.setType(type);
				bean.setId(id);
				
				beans.add(bean);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return beans;
	}
	
}




















;






