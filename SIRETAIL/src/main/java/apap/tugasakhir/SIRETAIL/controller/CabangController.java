package apap.tugasakhir.SIRETAIL.controller;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.rest.*;
import apap.tugasakhir.SIRETAIL.service.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

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
        model.addAttribute("id_cabang", cabang.getId());
        model.addAttribute("id_item", item.getId());
        return "coupon-apply-success";
    }


}
