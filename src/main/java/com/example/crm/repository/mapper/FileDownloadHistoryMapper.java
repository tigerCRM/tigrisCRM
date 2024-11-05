package com.example.crm.repository.mapper;

import com.example.crm.core.mapper.GenericMapper;
import com.example.crm.repository.domain.TFileDownloadHistory;

public interface FileDownloadHistoryMapper extends GenericMapper<TFileDownloadHistory>
{

	void insertFileDownloadHistory(TFileDownloadHistory tFileDownload);
}
