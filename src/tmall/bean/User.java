package tmall.bean;

public class User {
	private String name;
	private String password;
	private int id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 获取本用户的匿名名称
	 * @return
	 */
    public String getAnonymousName(){
        if(null==name)
            return null;
         
        if(name.length()<=1)
            return "*";
         
        if(name.length()==2)
            return name.substring(0,1) +"*";
         
        char[] cs =name.toCharArray();
        for (int i = 1; i < cs.length-1; i++) {
            cs[i]='*';
        }
        return new String(cs);
         
    }
	
}
