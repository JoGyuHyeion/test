package org.intercomics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.OauthAccessTokenVO;

public interface TokenRepository {

	@Insert("insert into oauth_access_token (token_id,access_token, userId, client_id) values (uuid(),#{access_token},#{userId},#{client_id})")
	public void newToken(OauthAccessTokenVO vo) throws Exception;

	@Select("select * from user where userId = #{userId}")
	public OauthAccessTokenVO findByUserId(String userId) throws Exception;

	@Delete("delete from oauth_access_token where userId = #{userId}")
	public void delete(String userId) throws Exception;

	@Select("select * from oauth_access_token ")
	public List<OauthAccessTokenVO> findAll() throws Exception;

}
