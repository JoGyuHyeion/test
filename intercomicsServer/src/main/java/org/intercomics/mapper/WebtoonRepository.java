package org.intercomics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.intercomics.domain.EpisodeVO;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.WebtoonVO;

public interface WebtoonRepository {

	public WebtoonVO getInformation(String webtoonId) throws Exception;

	@Select("select * from webtoon natural join day natural join platform natural join writer natural join genre limit #{scrollStart}, #{perScrollNum}")
	public List<WebtoonVO> listPerScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listPerDayPlatformScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listPerDayAllPlatformScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listOrderbyNewUpdateScroll(SearchCriteria cri) throws Exception;

	public int OrderbyNewUpdateLastNum(SearchCriteria cri) throws Exception;

	public WebtoonVO timestampOrderbyNewUpdateScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listOrderbySubscriptionsScroll(SearchCriteria cri) throws Exception;

	public int OrderbySubscriptionsLastNum(SearchCriteria cri) throws Exception;

	public WebtoonVO timestampOrderbySubscriptionsScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listOrderbyTrendingScroll(SearchCriteria cri) throws Exception;

	public int OrderbyTrendingLastNum(SearchCriteria cri) throws Exception;

	public WebtoonVO timestampOrderbyTrendingScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listOrderbyGenreScroll(SearchCriteria cri) throws Exception;

	public List<WebtoonVO> listOrderbyIsNewScroll(SearchCriteria cri) throws Exception;

	public int OrderbyIsNewLastNum(SearchCriteria cri) throws Exception;

	public WebtoonVO timestampOrderbyIsNewScroll(SearchCriteria cri) throws Exception;

	@Select("SELECT distinct(platform) FROM intercomics.platform")
	public List<String> getInitPlatforms() throws Exception;

	public List<WebtoonVO> getWebToonByPlayform(@Param("platform") String platform) throws Exception;

	@Select("SELECT * FROM intercomics.episode where webtoonId  = #{webtoonId} order by episodeNo asc;")
	public List<EpisodeVO> getEpisodeByWebtoon(@Param("webtoonId") String webtoonId) throws Exception;

	@Update(" update webtoon set viewCount = viewCount +1 where webtoonId = #{webtoonId}")
	public void updateViewCount(@Param("webtoonId") String webtoonId) throws Exception;

	public List<WebtoonVO> searchWebtoon(SearchCriteria cri) throws Exception;
	
	public int searchWebtoonLastNum(SearchCriteria cri) throws Exception;


}
