package com.tiger.crm.repository.mapper;

import com.tiger.crm.common.mapper.GenericMapper;
import com.tiger.crm.repository.domain.TFeed;
import com.tiger.crm.repository.domain.TFile;
import com.tiger.crm.repository.domain.TStorage;

import java.util.List;

public interface FileMapper extends GenericMapper<TFile>
{
	Integer selectMaxSeq(String fileId);

	void insertFileMaster(TFile tFile);

	void insertFileDetail(TFile tFile);

	List<TFile> getRequestFileList(TFeed feed);

	List<TFile> getReviewFileList(TFeed feed);

	TFile selectFile(TFile tFile);

	int remove(TFile file);

	List<TFile> getReplyFileList(TFeed tFeed);

	List<TFile> getStorageFileList(TStorage storageInfo);
}
