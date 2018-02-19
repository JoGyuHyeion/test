package org.intercomics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.LoginDTO;

public interface ProfileRepository {

	@Insert("insert into profile (id,name, picture_url, email) values (#{id},#{name},#{picture_url},#{email})")
	public void newProfile(LoginDTO dto) throws Exception;

	@Select("select * from profile where id = #{id}")
	public LoginDTO findByUserId(String userId) throws Exception;

	@Delete("delete from profile where id = #{id}")
	public void delete(String userId) throws Exception;

	@Select("select * from profile ")
	public List<LoginDTO> findAll() throws Exception;

}
