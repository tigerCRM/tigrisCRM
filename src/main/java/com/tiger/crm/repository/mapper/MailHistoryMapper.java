package com.tiger.crm.repository.mapper;

import com.tiger.crm.common.mapper.GenericMapper;
import com.tiger.crm.repository.domain.TMailHistory;

public interface MailHistoryMapper extends GenericMapper<TMailHistory>
{

	int insertMailHistory(TMailHistory tMailHistory);
}
