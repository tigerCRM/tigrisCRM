package com.tiger.crm.repository.mapper;

import com.tiger.crm.common.mapper.GenericMapper;
import com.tiger.crm.common.util.TigrisMap;
import com.tiger.crm.repository.domain.TOrg;

import java.util.HashMap;
import java.util.List;

public interface OrgMapper extends GenericMapper<TOrg>
{

	int insertOrg(TOrg tOrg);

	List<TOrg> selectDeletedOrgList(HashMap<String, Object> paramMap);

	int updateOrg(TOrg tOrg);

	int deleteOrg(TOrg tOrg);

	List<TigrisMap> selectUseOrgList();
}
