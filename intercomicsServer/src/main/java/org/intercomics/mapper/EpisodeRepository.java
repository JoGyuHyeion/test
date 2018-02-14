package org.intercomics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.Criteria;
import org.intercomics.domain.EpisodeVO;
import org.intercomics.domain.SearchCriteria;

public interface EpisodeRepository {

	@Select("select * from episode limit #{scrollStart}, #{perScrollNum}")
	public List<EpisodeVO> listPerScroll(Criteria cri) throws Exception;

	@Select("select * from episode where webtoonId = #{webtoonId} limit #{scrollStart}, #{perScrollNum}")
	public List<EpisodeVO> listPerWebToonScroll(SearchCriteria cri) throws Exception;
	
	//@Select("select * from episode where webtoonId = #{webtoonId} and episodeNo > #{episodeNo} limit #{scrollStart}, #{perScrollNum}")
	public List<EpisodeVO> afterEpisodeNoScroll(SearchCriteria cri) throws Exception;
	
	@Select("select max(episodeNo) from episode where webtoonId = #{webtoonId}")
	public int episodeLastNum(SearchCriteria cri) throws Exception;
}
