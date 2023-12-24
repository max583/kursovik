package server.server.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.core.io.ClassPathResource;
import server.User;
import java.util.List;


@SpringBootApplication
public class DB{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void databaseCheck() throws Exception{
        String sql = "SELECT count(1) FROM information_schema.schemata WHERE schema_name = 'students'";
        int result = jdbcTemplate.queryForObject(sql, Integer.class);
        if ( result == 0 ) {
            ClassPathResource scriptResource = new ClassPathResource("create_schema.sql");
            ScriptUtils.executeSqlScript(this.jdbcTemplate.getDataSource().getConnection(), new EncodedResource(scriptResource, "UTF-8"), false, false, ScriptUtils.DEFAULT_COMMENT_PREFIX, ";;",
                    ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER, ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
        }
    }

    public List<String> getAllUsers() {
        String sql = "SELECT name FROM students.users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"));
    }

    public List<String> getUserRoles(String username) {
        String sql = "select r.name\n" +
                "from students.users u,\n" +
                "\t students.roles r,\n" +
                "\t students.credentials c \n" +
                "where u.name = ?\n" +
                "and c.userid = u.userid\n" +
                "and c.roleid = r.roleid";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"), username);
    }

    public List<String> getAllRoles() {
        String sql = "SELECT name FROM students.roles";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"));
    }

    public boolean checkUserHasRole(String username,String role) {
        String sql = "select count(1)\n" +
                "from students.users u,\n" +
                "\t students.roles r,\n" +
                "\t students.credentials c \n" +
                "where u.name = ?\n" +
                "and c.userid = u.userid\n" +
                "and c.roleid = r.roleid\n" +
                "and r.name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username, role) > 0;
    }

    public void addUser(User user) {
        String sql = "insert into students.users (name, password) values (?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getPassword());
    }

    public void deleteUser(String username) {
        String sql = "delete from students.users where name = ?";
        jdbcTemplate.update(sql, username);
    }

    public void setUserPassword(String username, String password) {
        String sql = "update students.users set password = ? where name = ?";
        jdbcTemplate.update(sql, password, username);
    }

    public void revokeRoleFromUser(String username, String rolename) {
        String sql = "delete from students.credentials where userid = (select userid from students.users where name = ?) and roleid = (select roleid from students.roles where name = ?)";
        jdbcTemplate.update(sql, username, rolename);
    }

    public void grantRoleToUser(String username, String rolename) {
        String sql = "insert into students.credentials (userid, roleid) values ((select userid from students.users where name = ?), (select roleid from students.roles where name = ?))";
        jdbcTemplate.update(sql, username, rolename);
    }

    public boolean checkUserExists(String username, String password) {
        String sql = "select count(1) from students.users where name = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username, password) > 0;
    }

}
