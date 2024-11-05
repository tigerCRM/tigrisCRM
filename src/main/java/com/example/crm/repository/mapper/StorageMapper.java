package com.example.crm.repository.mapper;

import com.example.crm.core.mapper.GenericMapper;
import com.example.crm.repository.domain.TStorage;
import com.example.crm.repository.dto.storage.StorageSearchDto;

import java.util.List;

public interface StorageMapper extends GenericMapper<TStorage>
{
	List<TStorage> getStorageList(StorageSearchDto storageSearchDto);

	int getStorageListCount(StorageSearchDto storageSearchDto);

	TStorage getStorageDetail(StorageSearchDto storageSearchDto);
}
