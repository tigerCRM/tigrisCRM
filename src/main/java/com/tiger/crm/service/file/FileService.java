package com.tiger.crm.service.file;

import com.tiger.crm.common.response.ResponseData;
import com.tiger.crm.repository.domain.TFeed;
import com.tiger.crm.repository.domain.TFile;
import com.tiger.crm.repository.domain.TStorage;
import com.tiger.crm.repository.dto.file.FileUploadDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

public interface FileService
{
	ResponseData upload(FileUploadDto fileUploadDto, HttpServletRequest request);

	int getNextSeq(String fileId);

	List<TFile> getRequestFileList(TFeed feed);

	List<TFile> getReviewFileList(TFeed feed);

	FileSystemResource download(TFile tFile, HttpServletRequest request, HttpServletResponse response);

	int remove(TFile file);

	List<TFile> getStorageFileList(TStorage storageInfo);

	void insertFileDownloadHistory(HttpServletRequest request, TFile tFile);
}
