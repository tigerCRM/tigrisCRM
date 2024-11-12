package com.tiger.crm.repository.mapper;

import com.tiger.crm.common.mapper.GenericMapper;
import com.tiger.crm.repository.domain.TStorage;
import com.tiger.crm.repository.dto.storage.StorageSearchDto;

import java.util.List;

public interface StorageMapper extends GenericMapper<TStorage>
{
	List<TStorage> getStorageList(StorageSearchDto storageSearchDto);

	int getStorageListCount(StorageSearchDto storageSearchDto);

	TStorage getStorageDetail(StorageSearchDto storageSearchDto);
}
