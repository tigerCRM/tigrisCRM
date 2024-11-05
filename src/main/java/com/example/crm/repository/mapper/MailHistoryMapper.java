package com.example.crm.repository.mapper;

import com.example.crm.core.mapper.GenericMapper;
import com.example.crm.repository.domain.TMailHistory;

public interface MailHistoryMapper extends GenericMapper<TMailHistory>
{

	int insertMailHistory(TMailHistory tMailHistory);
}
