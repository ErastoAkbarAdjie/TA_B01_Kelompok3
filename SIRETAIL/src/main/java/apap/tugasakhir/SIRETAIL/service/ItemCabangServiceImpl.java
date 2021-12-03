package apap.tugasakhir.SIRETAIL.service;
import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.repository.ItemCabangDb;
import apap.tugasakhir.SIRETAIL.rest.*;
import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemCabangServiceImpl implements ItemCabangService {

    private static final Object ItemRequest = null;
    private final WebClient webClient;

    public ItemCabangServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
    }


    @Autowired
    ItemCabangDb itemCabangDb;

    @Override
    public Mono<Result<List<itemDetail>>> getListItem() {
        return this.webClient.get().retrieve().bodyToMono(new ParameterizedTypeReference<Result<List<itemDetail>>>(){});
    }
    
    @Override
    public ItemCabangModel addItemCabang(ItemCabangModel itemCabang) {
        return itemCabangDb.save(itemCabang);
    }

    @Override
    public List<ItemCabangModel> sortList(List<ItemCabangModel> listItemCabang) {
        List<ItemCabangModel> sorted = new ArrayList<ItemCabangModel>();
        List<String> uuidItem = new ArrayList<String>();
        HashMap<String, Integer> sortedMap = new HashMap<String, Integer>();

        for (ItemCabangModel itemCabang : listItemCabang){
            if(uuidItem.size() == 0){
                uuidItem.add(itemCabang.getUuid());
                sortedMap.put(itemCabang.getUuid(), itemCabang.getStok());
            }
            else{
                if(uuidItem.contains(itemCabang.getUuid())){
                    Integer past_value = 0;
                    past_value = sortedMap.get(itemCabang.getUuid());
                    sortedMap.put(itemCabang.getUuid(), itemCabang.getStok() + past_value);
                }
                else{
                    uuidItem.add(itemCabang.getUuid());
                    sortedMap.put(itemCabang.getUuid(), itemCabang.getStok());
                }
            }
        }

        for (Map.Entry<String,Integer> kv_pair : sortedMap.entrySet()) {
            String uuid = kv_pair.getKey();
            Integer stok = kv_pair.getValue();
            if(stok!=0){
                ItemCabangModel itemCabang = new ItemCabangModel();
                itemCabang.setUuid(uuid);
                itemCabang.setStok(stok);
                sorted.add(itemCabang);
            }
        }
        return sorted;

    }

    @Override
    public Mono<Result<itemDetail>> getItemByUuid(String uuid) {
        return this.webClient.get().uri(uuid).retrieve().bodyToMono(new ParameterizedTypeReference<Result<itemDetail>>(){});
    }

    @Override
    public Mono<Result<itemDetail>> update(ItemCabangModel itemCabang, Integer stok) {
        ItemRequest data = new ItemRequest(stok);
        System.out.println(this.webClient.put().uri(itemCabang.getUuid()).body(BodyInserters.fromValue(data)).retrieve().bodyToMono(new ParameterizedTypeReference<Result<itemDetail>>(){}).block().getResult());
        return this.webClient.put().uri(itemCabang.getUuid()).body(BodyInserters.fromValue(data)).retrieve().bodyToMono(new ParameterizedTypeReference<Result<itemDetail>>() {
        });
    }


    @Override
    public List<ItemCabangModel> getIntersectList(List<ItemCabangModel> listItemCabangOld, List<ItemCabangModel> listItemCabangNew) {

        List<ItemCabangModel> listOld = new ArrayList<ItemCabangModel>();
        List<ItemCabangModel> listNew = new ArrayList<ItemCabangModel>();
        List<ItemCabangModel> updatedList = new ArrayList<ItemCabangModel>();
        List<String> uuidItem = new ArrayList<String>();

        //previous list
        HashMap<String, Integer> map_old = new HashMap<>();

        for (ItemCabangModel itemCabang : listItemCabangOld){
            if(listOld.size() == 0){
                uuidItem.add(itemCabang.getUuid());
                map_old.put(itemCabang.getUuid(), itemCabang.getStok());
            }
            else{
                if(uuidItem.contains(itemCabang.getUuid())){
                    Integer past_value = 0;
                    past_value = map_old.get(itemCabang.getUuid());
                    map_old.put(itemCabang.getUuid(), itemCabang.getStok() + past_value);
                }
                else{
                    uuidItem.add(itemCabang.getUuid());
                    map_old.put(itemCabang.getUuid(), itemCabang.getStok());
                }
            }
        }

        uuidItem.clear();
        
        //current list
        HashMap<String, Integer> map_new = new HashMap<>();

        for (ItemCabangModel itemCabang : listItemCabangNew){
            if(listNew.size() == 0){
                uuidItem.add(itemCabang.getUuid());
                map_new.put(itemCabang.getUuid(), itemCabang.getStok());
            }
            else{
                if(uuidItem.contains(itemCabang.getUuid())){
                    Integer past_value = 0;
                    past_value = map_old.get(itemCabang.getUuid());
                    map_new.put(itemCabang.getUuid(), itemCabang.getStok() + past_value);
                }
                else{
                    uuidItem.add(itemCabang.getUuid());
                    map_new.put(itemCabang.getUuid(), itemCabang.getStok());
                }
            }
        }

        // Make intersect key 
        HashMap<String, Integer> intersectKeys = new HashMap<String, Integer>();
        
        for (String key : map_old.keySet()) {
            if(map_new.containsKey(key)){
                intersectKeys.put(key, map_new.get(key)+map_old.get(key));
            }   
        }

        // change map into list 
        for (Map.Entry<String,Integer> kv_pair : intersectKeys.entrySet()) {
            String uuid = kv_pair.getKey();
            Integer stok = kv_pair.getValue();
            if(stok!=0){
                ItemCabangModel itemCabang = new ItemCabangModel();
                itemCabang.setUuid(uuid);
                itemCabang.setStok(stok);
                updatedList.add(itemCabang);
            }
        }
        
        return updatedList;
    }

    @Override
    public ItemCabangModel updateItemFromDb(Integer id, Integer number) {
        ItemCabangModel item = getItemCabangById(id);
        item.setStok(number);
        return itemCabangDb.save(item);
    }

    @Override
    public ItemCabangModel getItemCabangById(Integer id) {
        Optional<ItemCabangModel> item = itemCabangDb.findById(id);
        if(item.isPresent()){
            return item.get();
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void addItemToDb(ItemCabangModel itemCabang) {
        itemCabangDb.save(itemCabang);
    }

    @Override
    public void deleteItemFromDb(ItemCabangModel itemCabang) {
        itemCabangDb.deleteAll();
        
    }

    @Override
    public List<ItemCabangModel> getUniqueList(List<ItemCabangModel> listItemCabangOld,
            List<ItemCabangModel> listItemCabangNew) {
                List<ItemCabangModel> toBeDeleted = listItemCabangOld;
                List<ItemCabangModel> listOld = new ArrayList<ItemCabangModel>();
                List<ItemCabangModel> listNew = new ArrayList<ItemCabangModel>();
                List<ItemCabangModel> updatedList = new ArrayList<ItemCabangModel>();
                List<String> uuidItem = new ArrayList<String>();
        
                //previous list
                HashMap<String, Integer> map_old = new HashMap<>();
        
                for (ItemCabangModel itemCabang : listItemCabangOld){
                    if(listOld.size() == 0){
                        uuidItem.add(itemCabang.getUuid());
                        map_old.put(itemCabang.getUuid(), itemCabang.getStok());
                    }
                    else{
                        if(uuidItem.contains(itemCabang.getUuid())){
                            Integer past_value = 0;
                            past_value = map_old.get(itemCabang.getUuid());
                            map_old.put(itemCabang.getUuid(), itemCabang.getStok() + past_value);
                        }
                        else{
                            uuidItem.add(itemCabang.getUuid());
                            map_old.put(itemCabang.getUuid(), itemCabang.getStok());
                        }
                    }
                }
        
                uuidItem.clear();
                
                //current list
                HashMap<String, Integer> map_new = new HashMap<>();
        
                for (ItemCabangModel itemCabang : listItemCabangNew){
                    if(listNew.size() == 0){
                        uuidItem.add(itemCabang.getUuid());
                        map_new.put(itemCabang.getUuid(), itemCabang.getStok());
                    }
                    else{
                        if(uuidItem.contains(itemCabang.getUuid())){
                            Integer past_value = 0;
                            past_value = map_old.get(itemCabang.getUuid());
                            map_new.put(itemCabang.getUuid(), itemCabang.getStok() + past_value);
                        }
                        else{
                            uuidItem.add(itemCabang.getUuid());
                            map_new.put(itemCabang.getUuid(), itemCabang.getStok());
                        }
                    }
                }
        
                // Make Union Key
                HashMap<String, Integer> uniqueKeys = new HashMap<String, Integer>();
                Set<String> keys = map_old.keySet();
                for(String k: keys){
                    if(!map_new.containsKey(k)) {
                        uniqueKeys.put(k, map_old.get(k));
                    }
                }
                Set<String> keys2 = map_new.keySet();
                for(String k: keys2){
                    if(!map_old.containsKey(k)) {
                        uniqueKeys.put(k, map_new.get(k));
                    }
        
                }
                // change map into list 
                for (Map.Entry<String,Integer> kv_pair : uniqueKeys.entrySet()) {
                    String uuid = kv_pair.getKey();
                    Integer stok = kv_pair.getValue();
                    if(stok!=0){
                        ItemCabangModel itemCabang = new ItemCabangModel();
                        itemCabang.setUuid(uuid);
                        itemCabang.setStok(stok);
                        updatedList.add(itemCabang);
                    }
                }

                //remove existing file from DB 
                List<ItemCabangModel> delete = new ArrayList<ItemCabangModel>();

                for (ItemCabangModel itemDelete : toBeDeleted) {
                    for (ItemCabangModel newItems : updatedList) {
                        if(itemDelete.getUuid() == newItems.getUuid()){
                            delete.add(newItems);
                        }
                    }
                }

                for (ItemCabangModel itemCabang : delete) {
                    updatedList.remove(itemCabang);
                }
                
                return updatedList;
            }
    
}
