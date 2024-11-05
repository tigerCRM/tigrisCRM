package com.example.crm.repository.mapper;

import com.example.crm.core.mapper.GenericMapper;
import com.example.crm.core.util.TigrisMap;
import com.example.crm.repository.domain.TOrg;

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
