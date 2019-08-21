package tmall.util;

/**
 * DateUtil这个日期工具类主要是用于java.util.Date类与java.sql.Timestamp 类的互相转换
 * @author Administrator
 *
 */
public class DateUtil {
	public static java.sql.Timestamp d2t(java.util.Date d){
		if(null == d)
			return null;
		return new java.sql.Timestamp(d.getTime());
	}
	
	public static java.util.Date t2d(java.sql.Timestamp t){
		if(null == t)
			return null;
		return new java.util.Date(t.getTime());
	}
}
