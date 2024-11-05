//package com.example.crm.service.org;
//
//import com.example.crm.core.util.StringUtils;
//import com.example.crm.core.util.TigrisMap;
//import com.example.crm.repository.mapper.OrgMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class OrgServiceImpl implements OrgService
//{
//	@Autowired OrgMapper orgMapper;
//
//	@Override
//	public Object getOrgTree()
//	{
//		List<TigrisMap> list = orgMapper.selectUseOrgList();
//
//		//최초 upOrgId의  값("")을 셋팅해준다.
//		return getOrgTree(list);
//	}
//
//	@SuppressWarnings("unchecked")
//	private Object getOrgTree(List<TigrisMap> list)
//	{
//		JSONArray jsonArray = new JSONArray();
//
//		Map<String, JSONObject> folderMap = new HashMap<String, JSONObject>();
//
//		for (TigrisMap org : list)
//		{
//			String orgId 	= (String) org.get("orgId");
//			String upOrgId 	= StringUtils.stripToEmpty((String) org.get("upOrgId"));
//
//			JSONObject jsonObject = new JSONObject();
////			jsonObject.put("key", 		org.get("orgId"));
//			jsonObject.put("key", 		org.get("orgCode"));
//			jsonObject.put("orgCode", 	org.get("orgCode"));
//			jsonObject.put("upOrgId", 	upOrgId);
//			jsonObject.put("title", 	org.get("orgName"));
//
//			//하위 폴더 체크
//			Long children = Long.parseLong(org.get("childCnt").toString());
//			boolean hasChildren = (children.intValue() > 0);
//			if (hasChildren) {
//				jsonObject.put("expand", "true");
//				jsonObject.put("isFolder", "true");
//			}
//
//			folderMap.put(orgId, jsonObject);
//		}
//
//		Iterator<String> it = folderMap.keySet().iterator();
//		try
//		{
//			while(it != null && it.hasNext())
//			{
//				String 		id 			= it.next();
//				JSONObject 	thisItem 	= folderMap.get(id);
//				String 		upOrgId 	= (String) thisItem.get("upOrgId");
//
//				if(StringUtils.isEmpty(upOrgId))
//				{
//					TigrisMap map = new TigrisMap();
//					map.put("orgId", 	thisItem.get("orgId"));
//					map.put("isRoot", 	true);
//					jsonArray.add(thisItem);
//				}
//				else if(folderMap.containsKey(upOrgId))
//				{
//					JSONObject parentItem = folderMap.get(upOrgId);
//					parentItem.put("expand", 	true);
//					parentItem.put("isFolder", 	true);
//					JSONArray children = (JSONArray) parentItem.get("children");
//
//					if(children == null)
//					{
//						children = new JSONArray();
//						parentItem.put("children", children);
//					}
//					children.add(thisItem);
//				}
//			}
//		}
//		catch (RuntimeException e)
//		{
//			log.error(e.getMessage());
//		}
//
//		return jsonArray;
//	}
//
//}
