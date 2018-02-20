package org.intercomics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.RecentWebtoonVO;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.WebtoonVO;

public interface RecentWebtoonRepository {

	@Select("select * from recentwebtoon where webtoonId = #{webtoonId} and userId = #{userId}")
	public RecentWebtoonVO getRecentWebtoon(@Param("webtoonId") String webtoonId, @Param("userId") String userId)
			throws Exception;

	@Select("insert into recentwebtoon(userId,webtoonId,episodeNo,viewTime) values (#{userId},#{webtoonId},#{episodeNo},now()) ")
	public RecentWebtoonVO addRecentWebtoon(@Param("webtoonId") String webtoonId, @Param("userId") String userId, @Param("episodeNo") int episodeNo)
			throws Exception;

	@Select("update recentwebtoon set viewTime = now(),episodeNo=#{episodeNo} where webtoonId = #{webtoonId} and userId = #{userId}")
	public RecentWebtoonVO updateDateTime(@Param("webtoonId") String webtoonId, @Param("userId") String userId,@Param("episodeNo") int episodeNo)
			throws Exception;

	@Select("select * from webtoon natural join day natural join platform natural join writer natural join genre natural join recentwebtoon where userId=#{userId} order by viewTime desc limit #{scrollStart}, #{perScrollNum}")
	public List<WebtoonVO> getRecentWebtoonList(SearchCriteria cri) throws Exception;

}
