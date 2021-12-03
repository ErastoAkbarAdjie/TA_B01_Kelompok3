package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import java.util.List;
import reactor.core.publisher.Mono;
import java.util.List;
import apap.tugasakhir.SIRETAIL.rest.*;

public interface ItemCabangService {
    void addItemToDb(ItemCabangModel itemCabang);
    void deleteItemFromDb(ItemCabangModel itemCabang);
    ItemCabangModel updateItemFromDb(Integer id, Integer number);
    ItemCabangModel getItemCabangById(Integer id);
    Mono<Result<List<itemDetail>>> getListItem();
    ItemCabangModel addItemCabang(ItemCabangModel itemCabang);
    List<ItemCabangModel> sortList(List<ItemCabangModel> listItemCabang);
    Mono<Result<itemDetail>> getItemByUuid(String uuid);
    Mono<Result<itemDetail>> update(ItemCabangModel itemCabang, Integer stok);
    List<ItemCabangModel> getIntersectList(List<ItemCabangModel> listItemCabangOld, List<ItemCabangModel> listItemCabangNew );
    List<ItemCabangModel> getUniqueList(List<ItemCabangModel> listItemCabangOld, List<ItemCabangModel> listItemCabangNew );
}
