//package com.example.crm.service.storage;
//
//import com.example.crm.repository.domain.TStorage;
//import com.example.crm.repository.dto.storage.StorageSearchDto;
//import com.example.crm.repository.mapper.StorageMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
//public class StorageServiceImpl implements StorageService
//{
//	@Autowired private StorageMapper storageMapper;
//
//	@Override
//	public List<TStorage> getStorageList(StorageSearchDto storageSearchDto)
//	{
//		return storageMapper.getStorageList(storageSearchDto);
//	}
//
//	@Override
//	public int getStorageListCount(StorageSearchDto storageSearchDto)
//	{
//		return storageMapper.getStorageListCount(storageSearchDto);
//	}
//
//	@Override
//	public int insert(TStorage tStorage)
//	{
//		return storageMapper.insert(tStorage);
//	}
//
//	@Override
//	public TStorage getStorageDetail(StorageSearchDto storageSearchDto)
//	{
//		return storageMapper.getStorageDetail(storageSearchDto);
//	}
//
//	@Override
//	public int update(TStorage tStorage)
//	{
//		return storageMapper.update(tStorage);
//	}
//
//	@Override
//	public int delete(TStorage tStorage)
//	{
//		return storageMapper.delete(tStorage);
//	}
//}
