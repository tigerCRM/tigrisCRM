package com.example.crm.service.file;

import com.example.crm.core.response.ResponseData;
import com.example.crm.repository.domain.TFeed;
import com.example.crm.repository.domain.TFile;
import com.example.crm.repository.domain.TStorage;
import com.example.crm.repository.dto.file.FileUploadDto;
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
