package com.diplomski.backend.external.util;

import java.util.List;

public interface ExternalMapper<E,EDTO> {
    E convertToEntity(EDTO edto);

    default List<E> convertToEntities(List<EDTO> list){
        return list.stream().map(this::convertToEntity).toList();
    }
}
