package org.intercomics;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.intercomics.service.LoginUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DuctoonBootApplicationTests {

	@Autowired
	private DataSource ds;

	@Autowired
	private SqlSessionFactory sqlSession;

	@Autowired
	private LoginUserDetailsService login;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Test
	public void context() {
		Map<String, String> map = new HashMap<>();
		map.put("d", "zxzx");
		map.put("g", "qwewqe");
		for (String s : map.keySet()) {
			System.out.println(s);
		}
		Date date = new Date();
		System.out.println(sdf.format(date));
	}

//	 @Test
	public void testConnection() throws Exception {
		System.out.println(ds);
		Connection con = ds.getConnection();
		System.out.println(con);
		con.close();
	}

	// @Test
	public void testSqlSession() throws Exception {
		System.out.println(sqlSession);
	}

	// @Test
	public void test() throws Exception {
		login.loadUserByUsername("testjo");

	}

}
