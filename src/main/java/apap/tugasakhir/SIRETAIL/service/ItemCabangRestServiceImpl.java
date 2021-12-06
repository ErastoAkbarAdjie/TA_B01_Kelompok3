package apap.tugasakhir.SIRETAIL.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.repository.ItemCabangDb;
import apap.tugasakhir.SIRETAIL.rest.Setting;
import apap.tugasakhir.SIRETAIL.rest.itemDetail;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
@Transactional
public class ItemCabangRestServiceImpl implements ItemCabangRestService {

    private final WebClient webClient;

    @Autowired
    private ItemCabangDb itemCabangDb;

    public ItemCabangRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.itemUrl).build();
    }

    @Override
    public Mono<String> getStatus() {
        return this.webClient.get().uri("/api/item").retrieve().bodyToMono(String.class);
    }

    @Override
    public Mono<itemDetail> postStatus() {
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        
        return null;
    }

    @Override
    public Map<String, Object> getList() {
        String uri = "https://si-item.herokuapp.com/api/item";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> result = restTemplate.getForObject(uri, Map.class);
        return result;
    }

    @Override
    public Mono<itemDetail> update(String uuid, ItemCabangModel itemCabang) {
        return this.webClient.put().uri(uuid).body(Mono.just(itemCabang.getStok()), itemDetail.class).retrieve().bodyToMono(itemDetail.class);
    }
    
}
