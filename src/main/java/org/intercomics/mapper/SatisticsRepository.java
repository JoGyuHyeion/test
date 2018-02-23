package org.intercomics.mapper;

import java.util.List;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.StatisticsVO;

public interface SatisticsRepository {
	
	public List<StatisticsVO> getStatisticsByPlatform(SearchCriteria cri) throws Exception;
	
	public List<StatisticsVO> getStatisticsByGenre(SearchCriteria cri) throws Exception;

	
}
