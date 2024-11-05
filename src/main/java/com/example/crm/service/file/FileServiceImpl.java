//package com.example.crm.service.file;
//
//import com.example.crm.core.ErrorCode;
//import com.example.crm.core.TigrisGlobal;
//import com.example.crm.core.context.ConfigProperties;
//import com.example.crm.core.file.FileUtils;
//import com.example.crm.core.response.ResponseData;
//import com.example.crm.core.util.DateUtils;
//import com.example.crm.repository.domain.TFeed;
//import com.example.crm.repository.domain.TFile;
//import com.example.crm.repository.domain.TFileDownloadHistory;
//import com.example.crm.repository.domain.TStorage;
//import com.example.crm.repository.dto.file.FileUploadDto;
//import com.example.crm.repository.dto.user.UserSearchDto;
//import com.example.crm.repository.mapper.FileDownloadHistoryMapper;
//import com.example.crm.repository.mapper.FileMapper;
//import com.example.crm.service.user.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//@Service
//@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
//public class FileServiceImpl implements FileService
//{
//	private Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//	@Autowired private ConfigProperties 			config;
//	@Autowired private FileMapper 					fileMapper;
//	@Autowired private FileDownloadHistoryMapper 	fileDownloadHistoryMapper;
//	@Autowired private UserService 					userService;
//
//	@Override
//	public ResponseData upload(FileUploadDto fileUploadDto, HttpServletRequest request)
//	{
//		ResponseData responseData = new ResponseData();
//		try
//		{
//			MultipartHttpServletRequest 			multiRequest 	= (MultipartHttpServletRequest) request;
//			Map<String, MultipartFile> 				fileMap 		= multiRequest.getFileMap();
//			Iterator<Entry<String, MultipartFile>> 	iter 			= fileMap.entrySet().iterator();
//
//			int 	totalSize 		= 0;
//			String 	userId 			= fileUploadDto.getUserId();
//			String 	fileId 			= fileUploadDto.getFileId();
//			int 	seq 			= getNextSeq(fileId);
//			String 	filePath 		= FileUtils.makeUploadPath(userId);
//			String 	fileFullPath 	= config.getFileUploadPath() + filePath;
//			boolean isFirstUploaded = fileUploadDto.getFirstUploaded();
//
//			File existsDir = new File(fileFullPath);
//			if (!existsDir.exists() || existsDir.isFile()) {
//				existsDir.mkdirs();
//			}
//
//			List<TFile> insertFiles = new ArrayList<TFile>();
//
//			TFile tFile = new TFile(userId);
//			tFile.setFileId(fileId);
//			tFile.setSeq(seq);
//			tFile.setFilePath(filePath);
//
//			if(isFirstUploaded) { fileMapper.insertFileMaster(tFile); }
//
//			while (iter.hasNext()) {
//				Entry<String, MultipartFile> entry = iter.next();
//
//				MultipartFile file = entry.getValue();
//
//				String 	orginFileName 	= file.getOriginalFilename();
//				int 	index 			= orginFileName.lastIndexOf(".");
//				String 	ext 			= orginFileName.substring(index + 1);
//				String 	fileName 		= FileUtils.makeUploadFileName(seq);
//
//				tFile.setSeq(seq);
//				tFile.setFileName(fileName);
//				tFile.setOriginFileName(orginFileName);
//				tFile.setFileSize(file.getSize());
//				tFile.setFileExt(ext);
//				tFile.setMimeType(file.getContentType());
//
//				insertFiles.add(tFile);
//
//				totalSize += file.getSize();
//				seq++;
//
//				File uploadFile = new File(fileFullPath, fileName);
//				file.transferTo(uploadFile);
//
//				fileMapper.insertFileDetail(tFile);
//			}
//
//			LOGGER.debug("file count : {}", tFile.getSeq());
//			LOGGER.debug("total size : {}", totalSize);
//
//			if(insertFiles.isEmpty())
//			{
//				responseData.setCode(ErrorCode.FAIL.getErrorCode());
//				responseData.setMessage("실패");
//				return responseData;
//			}
//
//			responseData.setCode(ErrorCode.SUCCESS.getErrorCode());
//			responseData.setMessage("성공");
//			responseData.setData(fileId);
//		}
//		catch (IllegalStateException | IOException e)
//		{
//			e.printStackTrace();
//			responseData.setCode(ErrorCode.FAIL.getErrorCode());
//			responseData.setMessage("실패");
//			return responseData;
//		}
//
//		return responseData;
//	}
//
//	@Override
//	public int getNextSeq(String fileId) {
//
//		Integer seq = fileMapper.selectMaxSeq(fileId);
//
//		return seq == null ? 1 : seq + 1;
//	}
//
//	@Override
//	public List<TFile> getRequestFileList(TFeed feed)
//	{
//		return setCreateDtStr(fileMapper.getRequestFileList(feed));
//	}
//
//	@Override
//	public List<TFile> getReviewFileList(TFeed feed)
//	{
//		return setCreateDtStr(fileMapper.getReviewFileList(feed));
//	}
//
//	private List<TFile> setCreateDtStr(List<TFile> fileList)
//	{
//		if(0 < fileList.size())
//		{
//			for(int i=0; i<fileList.size(); i++)
//			{
//				String createDtStr = DateUtils.getDateToString(fileList.get(i).getCreateDt(), "yyyy-MM-dd HH:mm:ss");
//				fileList.get(i).setCreateDtStr(createDtStr);
//			}
//		}
//
//		return fileList;
//	}
//
//	@Override
//	public FileSystemResource download(TFile tFile, HttpServletRequest request, HttpServletResponse response)
//	{
//		TFile fileInfo = fileMapper.selectFile(tFile);
//
//		File file = new File(config.getFileUploadPath() + fileInfo.getFilePath(), fileInfo.getFileName());
//
//		try
//		{
//			FileUtils.setDisposition(fileInfo.getOriginFileName(), request, response);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//        return new FileSystemResource(file);
//	}
//
//	public void insertFileDownloadHistory(HttpServletRequest request, TFile fileInfo)
//	{
//		UserSearchDto userInfo = userService.getCookie(request);
//
//		TFileDownloadHistory tFileDownload = new TFileDownloadHistory();
//		tFileDownload.setFileDownloadId(TigrisGlobal.generateID());
//		tFileDownload.setUserId(userInfo.getUserId());
//		tFileDownload.setFileId(fileInfo.getFileId());
//		tFileDownload.setSeq(fileInfo.getSeq());
//		tFileDownload.setDownloadIp(TigrisGlobal.getRemoteAddr(request));
//		tFileDownload.setUserAgent(request.getHeader("User-Agent"));
//
//		fileDownloadHistoryMapper.insertFileDownloadHistory(tFileDownload);
//	}
//
//	@Override
//	public int remove(TFile file)
//	{
//		return fileMapper.remove(file);
//	}
//
//	@Override
//	public List<TFile> getStorageFileList(TStorage storageInfo)
//	{
//		return setCreateDtStr(fileMapper.getStorageFileList(storageInfo));
//	}
//}
