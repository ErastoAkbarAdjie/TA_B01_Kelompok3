package apap.tugasakhir.SIRETAIL.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import apap.tugasakhir.SIRETAIL.service.ItemCabangService;
import reactor.core.publisher.Mono;
import apap.tugasakhir.SIRETAIL.service.ItemCabangRestService;
import apap.tugasakhir.SIRETAIL.rest.*;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemCabangRestController {
    
    @Autowired
    ItemCabangService itemCabangService;

    @GetMapping("/list")
    public ResponseEntity<Result<List<itemDetail>>> getItemList(){
        Result<List<itemDetail>> listItem = itemCabangService.getListItem().block();

        JsonResponse<List<itemDetail>> resGen = new JsonResponse<List<itemDetail>>();
        ResponseEntity<Result<List<itemDetail>>> res = resGen.response("list item", listItem.getResult());
        System.out.println(listItem.getResult().get(0).getNama());
        return res;
    }

}
