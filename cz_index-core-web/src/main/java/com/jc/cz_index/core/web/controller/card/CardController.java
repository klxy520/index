package com.jc.cz_index.core.web.controller.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.service.ICardService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.card.CardDto;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.Person;

/**
 * 
 * 描述：卡信息管理
 * 
 * @author yangjunhui 2017年12月27日 上午9:40:03
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/card")
public class CardController extends BaseController {
	@Autowired
	private ICardService iCardService;
	@Autowired
	private IPersonService personService;

	private static final Logger LOG = Logger.getLogger(CardController.class);

	/**
	 * 
	 * 描述：跳转卡务管理首页
	 * 
	 * @return
	 * @author yangjunhui 2017年12月27日 上午9:44:04
	 * @version 1.0
	 */
	@RequestMapping("/listPage")
	public String listPage() {
		return "/card/cardList";
	}

	/**
	 * 
	 * 描述：
	 * 
	 * @param request
	 * @param aoData
	 * @return
	 * @author yangjunhui 2017年12月27日 上午9:49:12
	 * @version 1.0
	 */
	@RequestMapping("/QueryCardByParams")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
		// 解析前台框架dataTable传过来的值，格式：Map
		Map<String, Object> aoDataMap = getAoDataMap(aoData);
		QueryParams params = new QueryParams();
		// 设置条件查询 QueryParam
		setFilterParam(aoDataMap, params);
		PagedList<CardDto> cardList = iCardService.queryAllCardList(params,
				StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
				StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("iTotalRecords", cardList.getTotalSize());
		result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
		result.put("iTotalDisplayRecords", cardList.getTotalSize());
		result.put("aaData", cardList.getList());
		return result;
	}

	/**
	 * 
	 * 描述：进入此人卡片详情列表页面
	 * 
	 * @param id
	 * @return
	 * @author yangjunhui 2018年1月1日 下午5:57:50
	 * @version 1.0
	 */
	@RequestMapping("/manageCard")
	public ModelAndView manageCardPage(Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Person person = personService.getModelDetailById(id);
		modelAndView.addObject("person", person);
		modelAndView.setViewName("/card/manageCard_list");
		return modelAndView;
	}

	/**
	 * 
	 * 描述：个人所有卡列表
	 * 
	 * @param request
	 * @param aoData
	 * @return
	 * @author yangjunhui 2018年1月3日 下午5:06:41
	 * @version 1.0
	 */
	@RequestMapping("/QueryPersonCard")
	@ResponseBody
	public Map<String, Object> personCard(HttpServletRequest request, @RequestParam String aoData) {
		// 解析前台框架dataTable传过来的值，格式：Map
		Map<String, Object> aoDataMap = getAoDataMap(aoData);
		QueryParams params = new QueryParams();
		// 设置条件查询 QueryParam
		setFilterParam(aoDataMap, params);
		params.put("delFalg", 0);
		PagedList<Card> cardList = iCardService.queryPagedBaseList(params,
				StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
				StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("iTotalRecords", cardList.getTotalSize());
		result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
		result.put("iTotalDisplayRecords", cardList.getTotalSize());
		result.put("aaData", cardList.getList());
		return result;
	}

	/**
	 * 
	 * 描述： 删除卡
	 * 
	 * @param id
	 * @return
	 * @author yangjunhui 2018年1月3日 下午5:07:19
	 * @version 1.0
	 */
	@RequestMapping(value = "/deleteCardById")
	@ResponseBody
	public ResponseData deleteCardById(Long id) {
		ResponseData responseData = new ResponseData();
		responseData = iCardService.deleteCardById(id);
		return responseData;
	}

	/**
	 * 
	 * 描述：添加卡
	 * 
	 * @param card
	 * @return
	 * @author yangjunhui 2018年1月3日 下午5:07:35
	 * @version 1.0
	 */
	@RequestMapping(value = "/addCard", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData addCard(Card card) {
		ResponseData responseData = new ResponseData();
		try {
			responseData = iCardService.addCard(card);
			LOG.debug("成功信息:" + responseData.getMessage());
		} catch (Exception e) {
			responseData.setStatus(ResponseData.ERROR_STATUS);
			responseData.setMessage("添加失败");
			LOG.error("卡添加 失败,异常信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return responseData;
	}

	/**
	 * 
	 * 描述：修改
	 * 
	 * @param card
	 * @return
	 * @author yangjunhui 2018年1月3日 下午5:08:02
	 * @version 1.0
	 */
	@RequestMapping(value = "/updateCard", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData updateCard(Card card) {
		ResponseData responseData = new ResponseData();
		try {
			responseData = iCardService.updateCard(card);
			LOG.debug("成功信息:" + responseData.getMessage());
		} catch (Exception e) {
			responseData.setStatus(ResponseData.ERROR_STATUS);
			responseData.setMessage("添加失败");
			LOG.error("卡修改失败,异常信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return responseData;
	}

	/**
	 * 
	 * 描述：单卡详情
	 * 
	 * @param id
	 * @return
	 * @author yangjunhui 2018年1月3日 下午5:08:17
	 * @version 1.0
	 */
	@RequestMapping("/queryCardById")
	public ModelAndView queryCardById(Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = iCardService.getModelDetailById(id);
		modelAndView.addObject("card", card);
		modelAndView.setViewName("/card/cardDetails");
		return modelAndView;
	}

	/**
	 * 
	 * 描述：去重复判断
	 * 
	 * @param no
	 * @param type
	 * @return
	 * @author yangjunhui 2018年1月16日 下午2:39:55
	 * @version 1.0
	 */
	@RequestMapping("/isExistNo")
	public Map<String, Object> isExistNo(String no, String type) {

		Map<String, Object> result = new HashMap<String, Object>();
		QueryParams params = new QueryParams();
		params.put("cardNo", no);
		params.put("cardTypeCode", type);
		params.put("delFalg", 0);
		List<Card> list = iCardService.queryBaseList(params);
		if (list.size() <= 0) {
			result.put("id", -1);
		} else {
			result.put("id", list.get(0).getId());
		}
		return result;
	}
}
