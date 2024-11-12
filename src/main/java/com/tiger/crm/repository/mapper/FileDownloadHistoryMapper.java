package com.tiger.crm.repository.mapper;

import com.tiger.crm.common.mapper.GenericMapper;
import com.tiger.crm.repository.domain.TFileDownloadHistory;

public interface FileDownloadHistoryMapper extends GenericMapper<TFileDownloadHistory>
{

	void insertFileDownloadHistory(TFileDownloadHistory tFileDownload);
}
