package com.lan.service;

import com.lan.model.Team;

import java.util.List;
import java.util.Map;
/**
 *
 * @author
 */
public interface TeamService {
/**根据条件查询当前页数据*/

	 Map<String,Object>findObjects(
             Integer valid,
             Integer projectId,
             Integer pageCurrent);
/**查询项目id和名称信息*/

	 List<Map<String,Object>>
	           findPrjIdAndNames();

/**禁用和启用团信息
	  * @param ids 对应页面上选中的多个团id值
	  * 例如ids=1,2,3,4,5
	  * @param valid 对应禁用或启用的状态值
	  * */

	 void validById(String ids, Integer valid);
/**保存团信息*/

	 void saveObject(Team team);
/**修改项目信息*/

	 void updateObject(Team team);
/**根据id查找团信息*/

	 Team findObjectById(Integer id);
}





