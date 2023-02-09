package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.ListDms;

public interface ListDmsService {
	
	public void saveList(ListDms listDms);
	
	public List<ListDms> getAllListDms();

	Optional<ListDms> getListByListCode(String list_CODE);

	List<String> getBloodGroup(String listName);

	List<ListDms> getByListName(String listName);

}
