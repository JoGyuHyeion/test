package org.intercomics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.intercomics.domain.Day;
import org.intercomics.domain.EpisodeVO;
import org.intercomics.domain.MywebtoonVO;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.WebtoonVO;
import org.intercomics.mapper.EpisodeRepository;
import org.intercomics.mapper.SubscribeRepository;
import org.intercomics.mapper.WebtoonRepository;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class MapperTests {
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Autowired
	private WebtoonRepository webtoonRepository;
	@Autowired
	private EpisodeRepository episodeRepository;
	@Autowired
	private SubscribeRepository subscribeRepository;

	private SearchCriteria cri = new SearchCriteria();

	@Resource(name = "MywebtoonVO")
	private MywebtoonVO mywebtoonVO;
	
	private String[] platforms = { "naver", "kakao", "daum" };

	// @Test
	public void trendTimestamp() throws Exception {
		cri.setAllPlatform("all");
		cri.setDay("fri");
		cri.setScroll(1);

		System.out.println(webtoonRepository.timestampOrderbyTrendingScroll(cri).getLastDate());

	}

	// @Test
	public void episodeAfterNo() throws Exception {
		cri.setWebtoonId("00565e03-f149-4df2-9a65-2fe62a87d413");
		cri.setEpisodeNo(22);

		List<EpisodeVO> list = episodeRepository.afterEpisodeNoScroll(cri);
		System.out.println(list.size());
		System.out.println(episodeRepository.afterEpisodeNoScroll(cri).size());
		for (EpisodeVO vo : episodeRepository.afterEpisodeNoScroll(cri)) {
			System.out.println(vo.toString());
		}
	}

	// @Test
	public void map() throws Exception {

		cri.setUserId("jo");
		cri.setDay("finish");

		JSONArray jsonArray = new JSONArray(subscribeRepository.listSubscibe(cri));
		Date date = new Date();

		Map<String, Object> map = new HashMap<>();
		map.put("sc", jsonArray);
		map.put("time", sdf.format(date));

		System.out.println(map.get("sc"));

		// entity = new
		// ResponseEntity<List<WebtoonVO>>(subscribeRepository.listSubscibe(getUserId(request.getHeader("x-token"))),
		// HttpStatus.OK);
		// obj.put("subscribe", jsonArray);
		// obj.put("timestamp", sdf.format(date));
	}

	 @Test
	public void day() throws Exception {
		System.out.println(Day.valueOf("finish").ordinal());
	}
	 
//	 @Test
	 public void timestamp() throws Exception{
		 cri.setUserId("898669406962660");
		 cri.setDay("etc");
		 System.out.println(subscribeRepository.timestampSubscibe(cri));
	 }

	

	// @Test
	public void subscribe() throws Exception {
		mywebtoonVO.setUserId("jo");
		mywebtoonVO.setWebtoonId("0c166b7a-411e-4f68-8cf3-b618b723da1c");
		// subscribeRepository.removeSubscribe(mywebtoonVO);
		subscribeRepository.addSubscribe(mywebtoonVO);
	}

//	@Test
	public void subscribeList() throws Exception {
		cri.setUserId("jo");
		cri.setDay("finish");
		subscribeRepository.listSubscibe(cri);
		for (WebtoonVO vo : subscribeRepository.listSubscibe(cri)) {
			System.out.println("1 번째");
			System.out.println(vo.toString());
		}
	}

	// @Test
	public void webToonbyPlatfrom() throws Exception {
		Map<String, Map<String, WebtoonVO>> mapPlatform = new HashMap<String, Map<String, WebtoonVO>>();
		Map<String, WebtoonVO> mapWebtoon = new HashMap<String, WebtoonVO>();

		for (String platform : platforms) {
			System.out.println(platform);
			for (WebtoonVO webtoon : webtoonRepository.getWebToonByPlayform(platform)) {
				System.out.println(webtoon.getWebtoonName());
				mapWebtoon.put(webtoon.getWebtoonName(), webtoon);
			}
			mapPlatform.put(platform, mapWebtoon);

		}

	}

	// @Test
	public void epciosedByPlatfrom() throws Exception {
		Map<String, Map<String, List<EpisodeVO>>> mapPlatform = new HashMap<String, Map<String, List<EpisodeVO>>>();
		Map<String, List<EpisodeVO>> mapWebtoon = new HashMap<String, List<EpisodeVO>>();

		for (String platform : platforms) {
			System.out.println(platform);
			for (WebtoonVO webtoon : webtoonRepository.getWebToonByPlayform(platform)) {
				System.out.println(webtoon.getWebtoonName());
				List<EpisodeVO> episode = webtoonRepository.getEpisodeByWebtoon(webtoon.getWebtoonId());
				mapWebtoon.put(webtoon.getWebtoonName(), episode);
			}
			mapPlatform.put(platform, mapWebtoon);

		}

	}

	// @Test
	public void context() throws Exception {
		// List<String> platforms = webtoonRepository.getInitPlatforms();
		String platforms = "Lezhin";

		for (WebtoonVO vo : webtoonRepository.getWebToonByPlayform(platforms)) {
			System.out.println(vo.toString());
		}
	}

	// @Test
	public void webToon() throws Exception {

		cri.setScroll(1);
		cri.setDay("mon");
		cri.setPlatforms(platforms);
		for (WebtoonVO vo : webtoonRepository.listPerDayPlatformScroll(cri)) {
			System.out.println(vo.toString());
		}

	}

//	 @Test
	public void webToonOrderbyTrending() throws Exception {
		cri.setScroll(0);
		cri.setPerScrollNum(90);
cri.setDay("finish");
		cri.setPlatforms(platforms);
		cri.setAllPlatform("all");
		System.out.println("cri() = " +cri.toString());
	for(String s:cri.getPlatforms()) {
		System.out.println("cri.getPlatforms()" +s);
	}


		for (WebtoonVO vo : webtoonRepository.listOrderbyTrendingScroll(cri)) {
			System.out.println(vo.toString());
		}

	}

	// @Test
	public void webToonOrderbySubscriptions() throws Exception {

		cri.setScroll(1);

		for (WebtoonVO vo : webtoonRepository.listOrderbySubscriptionsScroll(cri)) {
			System.out.println(vo.toString());
		}

	}

	// @Test
	public void episode() throws Exception {

		cri.setScroll(1);
		cri.setWebtoonId("17dfcfd9-b4dc-4f99-94a3-bb390517961e");

		for (EpisodeVO vo : episodeRepository.listPerWebToonScroll(cri)) {
			System.out.println(vo.toString());
		}

	}
}
