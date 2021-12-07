package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.rest.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UpdateStokItemServiceImpl implements UpdateStokItemService {

    private static final Object UpdateStokItemRequest = null;
    private final WebClient webClient;

    public UpdateStokItemServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.updateStokItemUrl).build();
    }

    private Integer convertKategoriToIdKategori(String kategori) {
        Map<String, Integer> categories = new HashMap<>();
        categories.put("BUKU", 1);
        categories.put("DAPUR", 2);
        categories.put("MAKANAN & MINUMAN", 3);
        categories.put("ELEKTRONIK", 4);
        categories.put("FASHION", 5);
        categories.put("KECANTIKAN & PERAWATAN DIRI", 6);
        categories.put("FILM & MUSIK", 7);
        categories.put("GAMING", 8);
        categories.put("GADGET", 9);
        categories.put("KESEHATAN", 10);
        categories.put("RUMAH TANGGA", 11);
        categories.put("FURNITURE", 12);
        categories.put("ALAT & PERANGKAT KERAS", 13);
        categories.put("WEDDING", 14);

        return categories.get(kategori);
    }

    @Override
    public HttpStatus createRequest(itemDetail item, Integer tambahanStok, Integer idCabang) {
        UpdateStokItemRequest data = new UpdateStokItemRequest(
                item.getUuid(),
                this.convertKategoriToIdKategori(item.getKategori()),
                tambahanStok,
                LocalDate.now(),
                idCabang
        );

        return Optional.of(this.webClient.post().uri("create").body(BodyInserters.fromValue(data)).retrieve().toBodilessEntity().block().getStatusCode()).get();
    }

}
