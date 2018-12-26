package learn.cp.todolist.network;

/**
 * function：
 * author by cpMark
 * create on 2018/12/19.
 */
public class UserBean {


    /**
     * account : cpp
     * pwd : 123456
     * userId : 2
     */

    private String account;
    private String pwd;
    private int userId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
