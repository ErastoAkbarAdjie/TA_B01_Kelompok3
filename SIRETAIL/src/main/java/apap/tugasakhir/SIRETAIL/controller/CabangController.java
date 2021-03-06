package apap.tugasakhir.SIRETAIL.controller;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.rest.*;
import apap.tugasakhir.SIRETAIL.service.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.model.IModelFactory;

@Controller
public class CabangController {
    @Autowired
    private UserService userService;

    @Autowired
    private CabangService cabangService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ItemCabangService itemCabangService;

    @Autowired
    private CouponItemService couponItemService;

    @Autowired
    private UpdateStokItemService updateStokItemService;

    @GetMapping("/cabang/add")
    public String addCabangFormPage(Model model){
        model.addAttribute("cabang", new CabangModel());
        return "form-add-cabang";
    }

    @PostMapping(value = "/cabang/add", params = {"save"})
    public String addCabangSubmitPage(
            @ModelAttribute CabangModel cabang,
            Model model
    ){
        //user yang membuat
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        cabang.setUser(user);
        // status disetujui
        cabang.setStatus(2);
        cabangService.addCabang(cabang);
        model.addAttribute("namaCabang", cabang.getNama());
        return "add-cabang-success";
    }

    @GetMapping("/viewAllCabang")
    public String listCabang(Model model) {

        List<CabangModel> listCabang = cabangService.getListCabang();
        List<CabangModel> listCabangManager = cabangService.getListCabang();
        List<CabangModel> delete = new ArrayList<CabangModel>();
        for (CabangModel cabang : listCabang) {
            if(cabang.getStatus() != 2){
                delete.add(cabang);
            }
        }
        for (CabangModel del : delete) {
            listCabangManager.remove(del);
        }
        String nama = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer id_user = userService.getUserByUsername(nama).getId();
        model.addAttribute("listCabangManager", listCabangManager);

        model.addAttribute("listCabang", listCabang);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        model.addAttribute("id_user", id_user);
        return "view-all-cabang";
    }

    @GetMapping ("/cabang/viewAll/request")
    public String listCabangRequest (Model model) {
        List<CabangModel> listCabang = cabangService.getListCabang();
        List <CabangModel> listCabangRequested = cabangService.getListCabangRequested(listCabang);
        model.addAttribute("listCabangRequested", listCabangRequested);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "view-all-request-cabang";
    }

    @GetMapping("/cabang/tolak/{id}")
    public String tolakCabang (@PathVariable Integer id, Model model) {
        CabangModel cabangDihapus = cabangService.getCabangByIdCabang(id);

//        CabangModel cabang = new CabangModel();
//        for (int i = 0 ; i< listcabang.size(); i++){
//            if (listcabang.get(i).getId() == id) {
//                cabang= listcabang.get(i);
//                System.out.println("CEK ID CABANG 2 " + cabang.getId());
//            }
//        }

        String responMessage = cabangService.tolakCabang(cabangDihapus);
        model.addAttribute("message", responMessage);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "delete-cabang";
    }

    @GetMapping("/cabang/setuju/{id}")
    public String terimaCabang (@PathVariable Integer id, Model model) {
        CabangModel cabangDisetujui= cabangService.getCabangByIdCabang(id);

        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        cabangDisetujui.setUser(user);

        // status disetujui
        cabangDisetujui.setStatus(2);
        cabangService.addCabang(cabangDisetujui);
        model.addAttribute("namaCabang", cabangDisetujui.getNama());

        String responMessage = "Cabang " + cabangDisetujui.getNama() + " berhasil diterima";
        model.addAttribute("message", responMessage);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "delete-cabang";
    }

    @GetMapping("/cabang/view")
    public String viewDetailCabangPage(
            @RequestParam(value = "id") Integer id,
            Model model
    ){
        CabangModel cabang = cabangService.getCabangByIdCabang(id);
        String nama = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer id_user = userService.getUserByUsername(nama).getId();
        model.addAttribute("cabang", cabang);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        model.addAttribute("id_user", id_user);
        return "view-cabang";
    }

    @GetMapping("/cabang/delete/{id}")
    public String deleteCabang (@PathVariable Integer id, Model model) {
        CabangModel cabang = cabangService.getCabangByIdCabang(id);
        String responMessage = cabangService.deleteCabang(cabang);
        model.addAttribute("message", responMessage);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "delete-cabang";
    }
    
    @GetMapping("/cabang/viewAllItem/{id}") //reference id cabang jangan lupa (lihat func diatas)
    public String listItem(
        @PathVariable Integer id,
        Model model
        ){
            CabangModel cabang = cabangService.getCabangByIdCabang(id);
            List<ItemCabangModel> existedItemList = new ArrayList<ItemCabangModel>();
            if(!cabang.getListItemCabang().isEmpty()){
                for (ItemCabangModel itemPrev : cabang.getListItemCabang()) {
                    existedItemList.add(itemPrev);
                }
                cabang.getListItemCabang().removeAll(existedItemList);
            }
            // CabangModel cabang = new CabangModel();
            // cabang.setListItemCabang(new ArrayList<ItemCabangModel>());
            cabang.getListItemCabang().add(new ItemCabangModel());
            Result<List<itemDetail>> item = itemCabangService.getListItem().block();
            List<itemDetail> listItem = item.getResult();
            model.addAttribute("namaCabang", cabang.getNama());
            model.addAttribute("listItem", listItem);
            model.addAttribute("cabang", cabang);
            model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
            return "view-all-item";
        }
    
    @PostMapping(value="/cabang/viewAllItem/{id}",params={"add"})
    public String addRow(
            @PathVariable Integer id,
            @ModelAttribute CabangModel cabang,
            Model model
        ){
            // cabang = cabangService.getCabangByIdCabang(id);
            for (ItemCabangModel items : cabang.getListItemCabang()) {
                if(items.getStok() == null){
                    items.setStok(0);
                }
            }
            Result<List<itemDetail>> item = itemCabangService.getListItem().block();
            List<itemDetail> listItem = item.getResult();
            cabang.getListItemCabang().add(new ItemCabangModel());
            model.addAttribute("namaCabang", cabangService.getCabangByIdCabang(id).getNama());
            model.addAttribute("listItem", listItem);
            model.addAttribute("cabang", cabang);
            model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
            return "view-all-item";
        }
    
    @PostMapping(value="/cabang/viewAllItem/{id}",params={"delete"})
        public String deleteRow(
            @ModelAttribute CabangModel cabang,
            @PathVariable Integer id, 
            @RequestParam(name="delete") int idRow,
            Model model
        ){
            Result<List<itemDetail>> item = itemCabangService.getListItem().block();
            List<itemDetail> listItem = item.getResult();
            if(cabang.getListItemCabang().size() != 1){
                cabang.getListItemCabang().remove(idRow);
            }
            else{
                if(cabang.getListItemCabang().get(0).getStok() == null){
                    cabang.getListItemCabang().get(0).setStok(0);
                }
            }
            model.addAttribute("namaCabang", cabangService.getCabangByIdCabang(id).getNama());
            model.addAttribute("listItem", listItem);
            model.addAttribute("cabang", cabang);
            model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
            return "view-all-item";
        }

        @PostMapping(value="/cabang/viewAllItem/{id}",params={"submit"})
        public String confirmationPage(
            @ModelAttribute CabangModel cabang,
            @PathVariable Integer id, 
            Model model
        ){
            CabangModel cabang_item = cabangService.getCabangByIdCabang(id);
            Result<List<itemDetail>> item = itemCabangService.getListItem().block();
            List<itemDetail> listItem = item.getResult();

            //handle null stok
            for (ItemCabangModel items : cabang.getListItemCabang()) {
                if(items.getStok() == null){
                    items.setStok(0);
                }
            }

            // handle multiple same item in list
            List<ItemCabangModel> sortedList = itemCabangService.sortList(cabang.getListItemCabang());
            List<ItemCabangModel> deletedSoon = new ArrayList<ItemCabangModel>();
            
            //handle if itemCabang.stok > item.stok
            for (ItemCabangModel itemCabang : sortedList) {
                Result<itemDetail> itemAPI = itemCabangService.getItemByUuid(itemCabang.getUuid()).block();
                itemDetail detail = itemAPI.getResult();
                if(detail.getStok()==0){
                    deletedSoon.add(itemCabang);
                }
                else{
                    itemCabang.setCabang(cabang_item);
                    itemCabang.setUuid(detail.getUuid());
                    itemCabang.setNama(detail.getNama());
                    itemCabang.setHarga(detail.getHarga());
                    itemCabang.setKategori(detail.getKategori());
                    if(itemCabang.getStok() > detail.getStok()){
                        itemCabang.setStok(detail.getStok());
                    }
                }  
            }

            sortedList.removeAll(deletedSoon); // handle ambil barang stok 0  

            List<ItemCabangModel> intersectList = new ArrayList<ItemCabangModel>();
            List<ItemCabangModel> uniqueList = new ArrayList<ItemCabangModel>();

            //handle adjustment if there were previous item list
            if(cabang_item.getListItemCabang().size()!=0){
                intersectList = itemCabangService.getIntersectList(cabang_item.getListItemCabang(), sortedList);
                uniqueList = itemCabangService.getUniqueList(cabang_item.getListItemCabang(), sortedList);
            }

            // Empty out previous list 
            if(sortedList.size()!=0){
                cabang.getListItemCabang().clear();
                for (ItemCabangModel itemCabang : sortedList) {
                    cabang.getListItemCabang().add(itemCabang);
                }
            }
            else{
                cabang.getListItemCabang().clear();
                cabang.getListItemCabang().add(new ItemCabangModel());
            }
            
            for (ItemCabangModel itemCabang : uniqueList) {
                System.out.println(itemCabang.getUuid());
            }
            // API Update
            System.out.println("Besar dari sortedList adalah :" + sortedList.size());
           
                for (ItemCabangModel itemCabang : sortedList) {
                    Integer change_in_stock=0;
                    Integer stok_then = itemCabangService.getItemByUuid(itemCabang.getUuid()).block().getResult().getStok();
                    change_in_stock = stok_then - itemCabang.getStok();
                    itemCabangService.update(itemCabang, change_in_stock); 
                }
            
            System.out.println(cabang_item.getListItemCabang().size());

            if(cabang_item.getListItemCabang().size()==0){
                if(sortedList.size()!=0){
                    //Add ke database 
                    for (ItemCabangModel itemCabang : sortedList) {
                        itemCabangService.addItemToDb(itemCabang);
                    }
                    model.addAttribute("namaCabang", cabangService.getCabangByIdCabang(id).getNama());
                    model.addAttribute("listItem", listItem);
                    model.addAttribute("cabang", cabang);
                    model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
                    return "item-confirm-page";
                }
                else{
                    model.addAttribute("namaCabang", cabangService.getCabangByIdCabang(id).getNama());
                    model.addAttribute("listItem", listItem);
                    model.addAttribute("cabang", cabang);
                    model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
                    return "view-all-item";
                }
            }

            else{
                if(intersectList.size()!=0 || uniqueList.size()!=0){
                    // Update data Intersect 
                    if(intersectList.size()!=0){
                        for (ItemCabangModel itemCabang : cabang_item.getListItemCabang()) {
                            for (ItemCabangModel itemCabangNew : intersectList) {
                                if(itemCabang.getUuid() == itemCabangNew.getUuid()){
                                    itemCabangService.updateItemFromDb(itemCabang.getId(), itemCabangNew.getStok());
                                }
                            }
                        }
                    }

                    //Add data Unique
                    if(uniqueList.size()!=0){
                        for (ItemCabangModel itemCabang : uniqueList) {
                            Result<itemDetail> itemAPI = itemCabangService.getItemByUuid(itemCabang.getUuid()).block();
                            itemDetail detail = itemAPI.getResult();
                            itemCabang.setCabang(cabang);
                            itemCabang.setHarga(detail.getHarga());
                            itemCabang.setKategori(detail.getKategori());
                            itemCabang.setNama(detail.getNama());
                            itemCabangService.addItemToDb(itemCabang);
                        }
                    }
                    model.addAttribute("namaCabang", cabangService.getCabangByIdCabang(id).getNama());
                    model.addAttribute("listItem", listItem);
                    model.addAttribute("cabang", cabang);
                    model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
                    return "item-confirm-page";
                }
                else{
                    model.addAttribute("namaCabang", cabangService.getCabangByIdCabang(id).getNama());
                    model.addAttribute("listItem", listItem);
                    model.addAttribute("cabang", cabang);
                    model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
                    return "view-all-item";
                }
            }
        }

        //fitur 12
    @GetMapping("/item/viewAllCoupon/{id}") //reference id cabang jangan lupa (lihat func diatas)
    public String listCoupon(
            @PathVariable Integer id,
            Model model
    ){
        ItemCabangModel item = itemCabangService.getItemCabangById(id);
        Result<List<couponDetail>> coupon = couponItemService.getListCoupon().block();
        List<couponDetail> listCoupon = coupon.getResult();
        model.addAttribute("idCabang", item.getCabang().getId());
        model.addAttribute("idItem", item.getId());
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("coupon", coupon);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "view-all-coupon";
    }

    @RequestMapping("/item/addCoupon/{idCabang}/{idItem}/{idCoupon}/{discountAmount}")
    public String addCoupon(
            @PathVariable(value = "idCabang") Integer idCabang,
            @PathVariable(value = "idItem") Integer idItem,
            @PathVariable(value = "idCoupon") Integer idCoupon,
            @PathVariable(value = "discountAmount") Double discountAmount,
            Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);
        ItemCabangModel item = itemCabangService.getItemCabangById(idItem);
        itemCabangService.updateItemDiskon(idItem, idCoupon, discountAmount);
        model.addAttribute("cabang", item.getCabang());
        model.addAttribute("item", item);
        return "coupon-apply-success";
    }

    //fitur 15
    @GetMapping(value = "/cabang/item/delete/{idItem}")
    public String deleteItem(
            @PathVariable(value = "idItem", required = true) Integer idItem,
            Model model
    ){
        ItemCabangModel item = itemCabangService.getItemCabangById(idItem);
        itemCabangService.deleteItem(item);
        model.addAttribute("cabang", item.getCabang());
        model.addAttribute("item", item);
        return "delete-item";
    }

    @GetMapping("/cabang/update/{id}")
    public String updateCabangFormPage(
            @PathVariable Integer id,
            Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(id);

        model.addAttribute("cabang", cabang);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "form-update-cabang";
    }

    @PostMapping("/cabang/update/{id}")
    public String updateCabangSubmitPage(
            @PathVariable Integer id,
            @ModelAttribute CabangModel cabang,
            Model model
    ) {
        try {
            CabangModel oldCabang = cabangService.getCabangByIdCabang(id);
            cabang.setListItemCabang(oldCabang.getListItemCabang());
            cabang.setStatus(oldCabang.getStatus());
            cabang.setUser(oldCabang.getUser());
            CabangModel updatedCabang = cabangService.updateCabang(cabang);
            model.addAttribute("message", "cabang berhasil di-update");
            model.addAttribute("pageTitle", "Daftar Cabang");
            model.addAttribute("url", "/viewAllCabang");
            return "success-page";
        } catch (Exception e) {
            model.addAttribute("error", "cabang tidak berhasil di-update");
            model.addAttribute("pageTitle", "Daftar Cabang");
            model.addAttribute("url", "/viewAllCabang");
            return "error-page";
        }
    }

    @GetMapping("/cabang/{id}/requestUpdateItemStock")
    public String requestUpdateItemStockFormPage(
            @PathVariable Integer id,
            Model model
    ){
        Result<List<itemDetail>> item = itemCabangService.getListItem().block(); // persis kaya eros
        List<itemDetail> listItem = item.getResult(); // persis kaya eros

        CabangModel cabang = cabangService.getCabangByIdCabang(id);

        model.addAttribute("namaCabang", cabang.getNama());
        model.addAttribute("idCabang", cabang.getId());
        model.addAttribute("listItem", listItem);

        return "request-update-item-stock-form";
    }

    @PostMapping("/cabang/{id}/requestUpdateItemStock")
    public String requestUpdateItemStockSubmit(
            @PathVariable Integer id,
            @ModelAttribute UpdateStokItemPayload payload,
            Model model
    ){
        Result<itemDetail> itemDetailResult = itemCabangService.getItemByUuid(payload.getItemId()).block();
        itemDetail item = itemDetailResult.getResult(); // dapet objek item nya yg dipilih untuk di update (dari si item)

        HttpStatus status = updateStokItemService.createRequest(item, payload.getStok(), id);
        System.out.println(status);

        if(status.is2xxSuccessful()) {
            model.addAttribute("message", "permohonan update stok berhasil dikirim");
            model.addAttribute("pageTitle", "Detail Cabang");
            model.addAttribute("url", "/cabang/view?id=" + id);
            return "success-page";
        } else {
            model.addAttribute("message","permohonan update stok gagal dikirim");
            model.addAttribute("pageTitle", "Detail Cabang");
            model.addAttribute("url", "/cabang/view?id=" + id);
            return "error-page";
        }
    }
}
