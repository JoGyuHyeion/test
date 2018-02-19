package org.intercomics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.BoardVO;
import org.intercomics.domain.SearchCriteria;

public interface BoardRepository {

	@Select("select * from board order by regdate desc")
	public List<BoardVO> getList(SearchCriteria cri) throws Exception;

}
