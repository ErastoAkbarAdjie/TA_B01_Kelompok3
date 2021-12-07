package apap.tugasakhir.SIRETAIL.service;
import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import java.util.List;
import reactor.core.publisher.Mono;
import java.util.List;
import apap.tugasakhir.SIRETAIL.rest.*;

public interface CouponItemService {
    Mono<Result<List<couponDetail>>> getListCoupon();
}
