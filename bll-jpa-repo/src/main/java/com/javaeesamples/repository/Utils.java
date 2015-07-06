package com.javaeesamples.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.javaeesamples.model.SortDirection;

public class Utils {
	private static Integer DEFAULT_PAGE_SIZE = 50;
	private static Integer DEFAULT_PAGE_INDEX = 0;
	
	public static Pageable constructPageSpecification(Optional<Integer> pageSize,
			Optional<Integer> pageIndex, Optional<SortDirection> direction, List<String> columns) {
		
		int pSize = pageSize.isPresent() && pageSize.get() > 0 ? pageSize.get() : DEFAULT_PAGE_SIZE;
		int pIndex = pageIndex.isPresent() && pageIndex.get() > 0 ? pageIndex.get() : DEFAULT_PAGE_INDEX;
		Direction sdirect = direction.isPresent() ? Direction.fromString(direction.get().name()) : Direction.ASC;
				
		Pageable pageSpecification = new PageRequest(pIndex, pSize, sdirect, columns.toArray(new String[columns.size()]));
		return pageSpecification;
	}
}
