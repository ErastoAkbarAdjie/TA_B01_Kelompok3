package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.rest.itemDetail;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import apap.tugasakhir.SIRETAIL.rest.*;

public interface ItemCabangRestService {
    Mono<String> getStatus();
    Mono<itemDetail> postStatus();
    Map<String, Object> getList();
    Mono<itemDetail> update(String uuid, ItemCabangModel itemCabang); 
}
