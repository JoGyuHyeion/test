package org.intercomics.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.StatisticsDTO;
import org.intercomics.domain.StatisticsVO;
import org.intercomics.domain.BoardVO;
import org.intercomics.domain.EpisodeVO;
import org.intercomics.domain.MywebtoonVO;
import org.intercomics.domain.RankDTO;
import org.intercomics.domain.RankVO;
import org.intercomics.domain.RecentWebtoonVO;
import org.intercomics.domain.WebtoonVO;
import org.intercomics.mapper.BoardRepository;
import org.intercomics.mapper.EpisodeRepository;
import org.intercomics.mapper.RankRepository;
import org.intercomics.mapper.RecentWebtoonRepository;
import org.intercomics.mapper.SatisticsRepository;
import org.intercomics.mapper.SubscribeRepository;
import org.intercomics.mapper.UserRepository;
import org.intercomics.mapper.WebtoonRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

	@Autowired
	private EpisodeRepository episodeRepository;
	@Autowired
	private WebtoonRepository webtoonRepository;
	@Autowired
	private SubscribeRepository subscribeRepository;
	@Autowired
	private RecentWebtoonRepository recentWebtoonRepository;
	@Autowired
	private SatisticsRepository satisticsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private RankRepository rankRepository;

	@Resource(name = "MywebtoonVO")
	private MywebtoonVO mywebtoonVO;
	@Resource(name = "RecentWebtoonVO")
	RecentWebtoonVO recentWebtoonVO;
	@Resource(name = "WebtoonVO")
	WebtoonVO webtoonVO;

	private Date date = new Date();

	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	// 플랫폼이름 배열 요청
	@RequestMapping(value = "/platforms", method = RequestMethod.GET)
	public Map<String, Object> getPlatformList() throws Exception {

		System.out.println("/platforms - GET : " + sdf.format(date));

		Map<String, Object> map = new HashMap<>();
		map.put("platforms", webtoonRepository.getInitPlatforms());
		map.put("timestamp", sdf.format(date));
		return map;
	}

	// all 기능 추가 하기
	// 플랫폼별 Map 구조 요청
	@RequestMapping(value = "/platforms/{platforms}", method = RequestMethod.GET)
	public Map<String, Map<String, String>> getWebtoonByPlatform(@PathVariable("platforms") String[] platforms)
			throws Exception {

		System.out.println("/platforms/{platforms} -GET  : " + sdf.format(date));

		Map<String, Map<String, String>> mapPlatform = new HashMap<String, Map<String, String>>();
		Map<String, String> mapWebtoon = new HashMap<String, String>();

		for (String platform : platforms) {
			mapPlatform.put(platform, mapWebtoon);
		}

		return mapPlatform;
	}

	// 통계
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getStatistics(SearchCriteria cri, HttpServletRequest request)
			throws Exception {

		System.out.println("/statistics -GET  : " + sdf.format(date));
		String userId = getUserId(request.getHeader("x-token"));
		cri.setUserId(userId);
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> statistic = new HashMap<String, Object>();

		try {
			cri.setAllPlatform("all");
			cri.setPerScrollNum(5);

			statistic.put("genre", satisticsRepository.getStatisticsByGenre(cri));
			statistic.put("platforms", satisticsRepository.getStatisticsByPlatform(cri));
			statistic.put("subscribe", subscribeRepository.subscibeLastNum(cri));
			statistic.put("episode", userRepository.findByUserId(userId).getEpisodeCount());

			entity = new ResponseEntity<Map<String, Object>>(statistic, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 구독 토글 - 등록
	@RequestMapping(value = "/subscribe/{webtoonId}", method = RequestMethod.POST)
	public ResponseEntity<String> subscribePost(HttpServletRequest request,
			@PathVariable("webtoonId") String webtoonId) {

		System.out.println("/subscribe/{webtoonId} -POST : " + sdf.format(date));

		ResponseEntity<String> entity = null;
		try {
			mywebtoonVO.setUserId(getUserId(request.getHeader("x-token")));
			mywebtoonVO.setWebtoonId(webtoonId);
			System.out.println(mywebtoonVO.toString());
			subscribeRepository.addSubscribe(mywebtoonVO);
			entity = new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 구독 토글 - 취소
	@RequestMapping(value = "/subscribe/{webtoonId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> subscribeDelete(HttpServletRequest request,
			@PathVariable("webtoonId") String webtoonId) {

		System.out.println("/subscribe/{webtoonId} -DELETE : " + sdf.format(date));

		ResponseEntity<String> entity = null;
		try {
			mywebtoonVO.setUserId(getUserId(request.getHeader("x-token")));
			mywebtoonVO.setWebtoonId(webtoonId);
			subscribeRepository.removeSubscribe(mywebtoonVO);
			entity = new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 유져별 구독 리스트 요청
	@RequestMapping(value = "/subscribe/{dayFilter}/{scroll}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> subscribeList(HttpServletRequest request, SearchCriteria cri,
			@PathVariable("dayFilter") String dayFilter, @RequestParam("timestamp") String timestamp) {

		System.out.println("/subscribe/{dayFilter}/{scroll} -GET : " + sdf.format(date));

		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		cri.setDay(dayFilter);
		cri.setUserId(getUserId(request.getHeader("x-token")));
		System.out.println(cri.toString());
		try {
			String beforTimestamp = String.valueOf(subscribeRepository.timestampSubscibe(cri));

			if (!(beforTimestamp.equals(timestamp))) {
				cri.setScroll(1);
				System.out.println("trending : 시간다르다" + beforTimestamp);
			}
			Boolean isLast = false;
			int lastNum = subscribeRepository.subscibeLastNum(cri);
			if (lastNum <= (cri.getScrollStart() + cri.getPerScrollNum())) {
				isLast = true;
			}
			map.put("isLast", isLast);

			List<WebtoonVO> subscribeList = subscribeRepository.listSubscibe(cri);
			map.put("subscribe", subscribeList);
			map.put("timestamp", beforTimestamp);

			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	private RankDTO getRankDTO(String rankName, List<RankVO> webtoonList) {

		RankDTO rankDTO = new RankDTO();
		rankDTO.setName(rankName);
		rankDTO.setRanks(webtoonList);

		return rankDTO;
	}

	private StatisticsDTO getStatisticsDTO(String rankName, List<StatisticsVO> webtoonList) {

		StatisticsDTO dto = new StatisticsDTO();
		dto.setName(rankName);
		dto.setRanks(webtoonList);

		return dto;
	}



	// 웹툰리스트 - 플랫폼, 인기순 구독순 최신순 요청
	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	public ResponseEntity<List<RankDTO>> webToonTop10RankList(SearchCriteria cri) {

		System.out.println("/rank -GET : " + sdf.format(date));

		ResponseEntity<List<RankDTO>> entity = null;

		List<RankDTO> rank = new ArrayList<RankDTO>();
		try {
			List<RankVO> subscribeWebtoon =rankRepository.getList("subscribeWebtoon");
			List<RankVO> viewCount =rankRepository.getList("viewCount");
			List<RankVO> finishWebtoon =rankRepository.getList("finishWebtoon");

			rank.add(getRankDTO("구독순 top10", subscribeWebtoon));
			rank.add(getRankDTO("조회순 top10", viewCount));
			rank.add(getRankDTO("완결 top10", finishWebtoon));

			entity = new ResponseEntity<List<RankDTO>>(rank, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 웹툰 리스트 - 최신순, 구독순, 인기순 ,신작순 (day,platform으로 필터링)
	// 신작 완결 리스트 포함
	@RequestMapping(value = "/webtoon/{platforms}/{orderby}/{dayFilter}/{scroll}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> webToonDayPlatformScrollList(SearchCriteria cri,
			@PathVariable("dayFilter") String dayFilter, @PathVariable("platforms") String platforms,
			@PathVariable("orderby") String orderby, @RequestParam("timestamp") String timestamp) {

		System.out.println("/webtoon/{platforms}/{orderby}/{dayFilter}/{scroll} -GET : " + sdf.format(date));

		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		List<WebtoonVO> webtoonList = null;
		System.out.println("받아오는 시간" + timestamp);
		String beforTimestamp = "";
		cri.setAllPlatform(platforms);
		cri.setDay(dayFilter);
		Boolean isLast = false;
		try {
			if (orderby.equals("trending")) {
				webtoonVO = webtoonRepository.timestampOrderbyTrendingScroll(cri);
				beforTimestamp = webtoonVO != null ? webtoonVO.getLastDate().toString() : "notFound";
				if (!(beforTimestamp.equals(timestamp))) {
					cri.setScroll(1);
					System.out.println("trending : 시간다르다" + beforTimestamp);
				}
				webtoonList = webtoonRepository.listOrderbyTrendingScroll(cri);

				int lastNum = webtoonRepository.OrderbyTrendingLastNum(cri);
				if (lastNum <= (cri.getScrollStart() + cri.getPerScrollNum())) {
					isLast = true;
				}

			} else if (orderby.equals("subscriptions")) {
				webtoonVO = webtoonRepository.timestampOrderbySubscriptionsScroll(cri);
				beforTimestamp = webtoonVO != null ? webtoonVO.getLastDate().toString() : "notFound";
				if (!(beforTimestamp.equals(timestamp))) {
					cri.setScroll(1);
					webtoonVO = webtoonRepository.timestampOrderbySubscriptionsScroll(cri);
					System.out.println("subscriptions : 시간다르다" + beforTimestamp);
				}
				webtoonList = webtoonRepository.listOrderbySubscriptionsScroll(cri);

				int lastNum = webtoonRepository.OrderbySubscriptionsLastNum(cri);
				if (lastNum <= (cri.getScrollStart() + cri.getPerScrollNum())) {
					isLast = true;
				}

			} else if (orderby.equals("newupdate")) {
				webtoonVO = webtoonRepository.timestampOrderbyNewUpdateScroll(cri);
				beforTimestamp = webtoonVO != null ? webtoonVO.getLastDate().toString() : "notFound";
				if (!(beforTimestamp.equals(timestamp))) {
					cri.setScroll(1);
					System.out.println("newupdate : 시간다르다" + beforTimestamp);
				}
				webtoonList = webtoonRepository.listOrderbyNewUpdateScroll(cri);

				int lastNum = webtoonRepository.OrderbyNewUpdateLastNum(cri);
				if (lastNum <= (cri.getScrollStart() + cri.getPerScrollNum())) {
					isLast = true;
				}

			} else if (orderby.equals("new")) {
				webtoonVO = webtoonRepository.timestampOrderbyIsNewScroll(cri);
				beforTimestamp = webtoonVO != null ? webtoonVO.getLastDate().toString() : "notFound";
				if (!(beforTimestamp.equals(timestamp))) {
					cri.setScroll(1);
					webtoonVO = webtoonRepository.timestampOrderbyIsNewScroll(cri);
					System.out.println("new : 시간다르다" + beforTimestamp);
				}
				webtoonList = webtoonRepository.listOrderbyIsNewScroll(cri);

				int lastNum = webtoonRepository.OrderbyIsNewLastNum(cri);
				if (lastNum <= (cri.getScrollStart() + cri.getPerScrollNum())) {
					isLast = true;
				}
			}

			// if (webtoonList.size() == 0) {
			// throw new Exception();
			// }
			map.put("isLast", isLast);
			map.put("timestamp", webtoonVO != null ? webtoonVO.getLastDate().toString() : "");
			map.put("webtoons", webtoonList);
			System.out.println(cri.toString());
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 웹툰리스트 - 장르 요청
	@RequestMapping(value = "/webtoon/{genre}/{scroll}", method = RequestMethod.GET)
	public ResponseEntity<List<WebtoonVO>> webToonOderbyGenreScrollList(SearchCriteria cri) {

		System.out.println("/webtoon/{genre}/{scroll} -GET : " + sdf.format(date));
		System.out.println(cri.toString());

		ResponseEntity<List<WebtoonVO>> entity = null;
		try {
			entity = new ResponseEntity<List<WebtoonVO>>(webtoonRepository.listOrderbyGenreScroll(cri), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 웹퉁 정보
	@RequestMapping(value = "/webtoon/{webtoonId}", method = RequestMethod.GET)
	public ResponseEntity<List<WebtoonVO>> webToonInformation(@PathVariable("webtoonId") String webtoonId) {

		System.out.println("/webtoon/{webtoonId} -GET : " + sdf.format(date));

		ResponseEntity<List<WebtoonVO>> entity = null;
		try {
			List<WebtoonVO> webtoonInformation = new ArrayList<>();
			webtoonInformation.add(webtoonRepository.getInformation(webtoonId));
			entity = new ResponseEntity<List<WebtoonVO>>(webtoonInformation, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 웹툰 이름,웹툰작가 검색리스트 요청
	@RequestMapping(value = "/search/{writer}/{scroll}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> searchWebToonScrollList(SearchCriteria cri) {

		System.out.println("/search/{writer} -GET : " + sdf.format(date));

		ResponseEntity<Map<String, Object>> entity = null;
		Boolean isLast = false;
		Map<String, Object> map = new HashMap<>();
		List<WebtoonVO> webtoonList = null;
		try {
			int lastNum = webtoonRepository.searchWebtoonLastNum(cri);
			if (lastNum <= (cri.getScrollStart() + cri.getPerScrollNum())) {
				isLast = true;
			}
			webtoonList = webtoonRepository.searchWebtoon(cri);
			map.put("isLast", isLast);
			map.put("webtoons", webtoonList);
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 최근본 웹툰 등록 및 리스트
	@RequestMapping(value = "/recentWebtoon/{webtoonId}", method = RequestMethod.PATCH)
	public ResponseEntity<List<WebtoonVO>> recentWebToonPath(HttpServletRequest request, SearchCriteria cri,
			@PathVariable("webtoonId") String webtoonId) {

		System.out.println("/recentWebtoon/{webtoonId} -PATCH : " + sdf.format(date));

		ResponseEntity<List<WebtoonVO>> entity = null;
		try {

			String userId = getUserId(request.getHeader("x-token"));
			cri.setUserId(userId);
			cri.setPerScrollNum(30);

			recentWebtoonVO = recentWebtoonRepository.getRecentWebtoon(webtoonId, userId);
			if (recentWebtoonVO == null) {
				recentWebtoonRepository.addRecentWebtoon(webtoonId, userId, 0);
			} else {
				recentWebtoonRepository.updateDateTime(webtoonId, userId, recentWebtoonVO.getEpisodeNo());
			}
			entity = new ResponseEntity<List<WebtoonVO>>(recentWebtoonRepository.getRecentWebtoonList(cri),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 최근본 웹퉁 리스트
	@RequestMapping(value = "/recentWebtoon", method = RequestMethod.GET)
	public ResponseEntity<List<WebtoonVO>> recentWebToonScrollList(HttpServletRequest request, SearchCriteria cri) {

		System.out.println("/recentWebtoon -GET : " + sdf.format(date));

		ResponseEntity<List<WebtoonVO>> entity = null;
		try {
			cri.setPerScrollNum(30);
			cri.setUserId(getUserId(request.getHeader("x-token")));
			entity = new ResponseEntity<List<WebtoonVO>>(recentWebtoonRepository.getRecentWebtoonList(cri),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 플렛폼별 웹툰 에피소드 전체 구조 테스트
	@RequestMapping(value = "/webtoons/{platforms}", method = RequestMethod.GET)
	public Map<String, Map<String, Map<String, EpisodeVO>>> test2getWebtoonByPlatform(
			@PathVariable("platforms") String[] platforms) throws Exception {

		System.out.println("/webtoons/{platforms} -GET : " + sdf.format(date));

		Map<String, Map<String, Map<String, EpisodeVO>>> mapPlatform = new HashMap<String, Map<String, Map<String, EpisodeVO>>>();
		for (String platform : platforms) {
			Map<String, Map<String, EpisodeVO>> mapWebtoon = new HashMap<String, Map<String, EpisodeVO>>();
			for (WebtoonVO webtoon : webtoonRepository.getWebToonByPlayform(platform)) {
				Map<String, EpisodeVO> mapEpisode = new HashMap<String, EpisodeVO>();
				for (EpisodeVO episode : webtoonRepository.getEpisodeByWebtoon(webtoon.getWebtoonId())) {
					mapEpisode.put(episode.getEpisodeName(), episode);

				}
				mapWebtoon.put(webtoon.getWebtoonName(), mapEpisode);
			}
			mapPlatform.put(platform, mapWebtoon);
		}
		return mapPlatform;
	}

	// 웹툰에 해당하는 에피소드 리스트 ,최근본 리스트 - 앱이 가지고 있는 episodeNo 이후값 요청
	@RequestMapping(value = "/episode/{webtoonId}/{episodeNo}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> episodeScrollList11(SearchCriteria cri, HttpServletRequest request,
			@RequestParam String orderby) {

		System.out.println("/episode/{webtoonId}/{episodeNo} -GET : " + sdf.format(date));

		Boolean isLast = false;
		final int defaultPerScrollNum = 20;
		int calOrderby = defaultPerScrollNum;
		int perScrollNum = defaultPerScrollNum;
		int recentEpNum = 0;
		cri.setPerScrollNum(perScrollNum);
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String userId = getUserId(request.getHeader("x-token"));
			cri.setUserId(userId);

			int lastNum = episodeRepository.episodeLastNum(cri);
			recentWebtoonVO = recentWebtoonRepository.getRecentWebtoon(cri.getWebtoonId(), userId);

			if (recentWebtoonVO != null) {
				recentEpNum = recentWebtoonVO.getEpisodeNo();
			}

			if (orderby.equals("asc")) {
				calOrderby = (recentEpNum - cri.getEpisodeNo());
				perScrollNum = calOrderby > 0 ? calOrderby : defaultPerScrollNum;
				cri.setPerScrollNum(perScrollNum);
				if ((cri.getEpisodeNo() < lastNum) && (lastNum <= (cri.getEpisodeNo() + perScrollNum))) {

					isLast = true;
				}

			} else if (orderby.equals("desc")) {

				if (lastNum - recentEpNum == 0) {
					calOrderby = (lastNum - cri.getEpisodeNo() + 1);
				} else {
					calOrderby = (lastNum - recentEpNum - cri.getEpisodeNo() + 1);
				}

				perScrollNum = calOrderby > 0 && recentEpNum != 0 ? calOrderby : defaultPerScrollNum;
				cri.setPerScrollNum(perScrollNum);
				if (((lastNum - perScrollNum) <= cri.getEpisodeNo()) && (cri.getEpisodeNo() < lastNum)) {
					isLast = true;
				}
				cri.setEpisodeNo(lastNum + 1 - cri.getEpisodeNo());
			}

			map.put("recentEpisodeNo", recentEpNum);

			mywebtoonVO.setUserId(userId);
			mywebtoonVO.setWebtoonId(cri.getWebtoonId());
			map.put("isSubscribe", subscribeRepository.isSubscibe(mywebtoonVO) != null ? true : false);

			List<EpisodeVO> episodelist = episodeRepository.afterEpisodeNoScroll(cri);

			if (episodelist.size() == 0) {
				// throw new Exception();
				isLast = true;
			}
			map.put("episodeList", episodelist);
			map.put("isLast", isLast);
			map.put("lastNum", lastNum);

			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// episode 클릭시 웹툰 조회수 증가
	// 최근본 웹툰 등록, 최근본 웹툰 시간 업데이트
	@RequestMapping(value = "/episode/{webtoonId}/{episodeNo}", method = RequestMethod.PATCH)
	public ResponseEntity<String> webtoon(HttpServletRequest request, @PathVariable("webtoonId") String webtoonId,
			@PathVariable("episodeNo") int episodeNo) {

		System.out.println("/episode/{webtoonId}/{episodeNo} -PATCH : " + sdf.format(date));

		ResponseEntity<String> entity = null;
		try {
			String userId = getUserId(request.getHeader("x-token"));
			webtoonRepository.updateViewCount(webtoonId);
			userRepository.episodeCount(userId);
			recentWebtoonVO = recentWebtoonRepository.getRecentWebtoon(webtoonId, userId);
			if (recentWebtoonVO == null) {
				recentWebtoonRepository.addRecentWebtoon(webtoonId, userId, episodeNo);
			} else {
				recentWebtoonRepository.updateDateTime(webtoonId, userId, episodeNo);
			}
			// 유저마다 리센트 테이블에 웹툰이 없으면 insert 있으면 datatime 업데이트

			entity = new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 에피소드 리스트
	@RequestMapping(value = "/episode/{scroll}", method = RequestMethod.GET)
	public ResponseEntity<List<EpisodeVO>> epicodeScrollList(SearchCriteria cri) {

		System.out.println("/episode/{scroll} -GET : " + sdf.format(date));

		ResponseEntity<List<EpisodeVO>> entity = null;
		try {
			entity = new ResponseEntity<List<EpisodeVO>>(episodeRepository.listPerScroll(cri), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 공지사항 최신순으로 요청
	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public ResponseEntity<List<BoardVO>> boardScroll(SearchCriteria cri) {

		System.out.println("/notification -GET : " + sdf.format(date));

		ResponseEntity<List<BoardVO>> entity = null;
		try {
			entity = new ResponseEntity<List<BoardVO>>(boardRepository.getList(cri), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	private String getUserId(String token) {
		Jwt jwt = JwtHelper.decode(token);
		JSONObject obj = new JSONObject(jwt.getClaims());
		System.out.println("token : " + token);
		System.out.println("user_name : " + obj.get("user_name").toString());
		return obj.get("user_name").toString();
	}

}
