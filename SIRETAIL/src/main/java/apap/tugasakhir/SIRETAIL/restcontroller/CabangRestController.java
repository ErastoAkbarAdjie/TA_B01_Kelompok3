package apap.tugasakhir.SIRETAIL.restcontroller;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;
import apap.tugasakhir.SIRETAIL.rest.*;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cabang")
public class CabangRestController {
    @Autowired
    private CabangRestService cabangRestService;

    @GetMapping("/listAlamat")
    private Result <List <Map<String,Object>>> getAllAlamat() {
        Result<List <Map<String,Object>>> response = new Result<>();

        try {
            List <CabangModel> listCabang = cabangRestService.getAllCabang();
            List <Map<String,Object>> listAlamat = cabangRestService.getAllAlamat(listCabang);
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(listAlamat);

        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"List Alamat Not Found"
            );
        }
        return response;
    }
}

