package apap.tugasakhir.SIRETAIL.restcontroller;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;
import apap.tugasakhir.SIRETAIL.rest.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cabang")
public class CabangRestController {
    @Autowired
    private CabangRestService cabangRestService;

    @PostMapping(value="/createCabang")
    private CabangModel createCabang(@Valid @RequestBody CabangModel cabang, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        } else {
            return cabangRestService.createCabang(cabang);
        }

    }

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

