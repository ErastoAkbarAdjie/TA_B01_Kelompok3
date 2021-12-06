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
public class CouponItemServiceImpl implements CouponItemService {

    private static final Object CouponRequest = null;
    private final WebClient webClient;

    public CouponItemServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.couponUrl).build();
    }

    @Override
    public Mono<Result<List<couponDetail>>> getListCoupon() {
        return this.webClient.get().retrieve().bodyToMono(new ParameterizedTypeReference<Result<List<couponDetail>>>(){});
    }

}
