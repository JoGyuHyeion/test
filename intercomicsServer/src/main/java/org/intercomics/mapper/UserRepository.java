package org.intercomics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.intercomics.user.UserVO;

public interface UserRepository {

	@Insert("insert into user (uid,userId, password, userType,regDate) values (uuid(),#{userId},#{password},#{userType},#{regDate})")
	public void newUser(UserVO vo) throws Exception;

	@Select("select uid, userId, password, userType,regDate from user where userId = #{userId}")
	public UserVO findByUserId(String userId) throws Exception;

	@Delete("delete from user where userId = #{userId}")
	public void delete(String userId) throws Exception;

	@Select("select * from user ")
	public List<UserVO> findAll() throws Exception;

}
