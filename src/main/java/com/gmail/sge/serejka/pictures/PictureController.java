package com.gmail.sge.serejka.pictures;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PictureController {
    static final int ITEMS_PER_PAGE = 6;

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }


    @GetMapping("/")
    public String pictures(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;
        List<Picture> pictures = pictureService
                .findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
        model.addAttribute("pictures", pictures);
        model.addAttribute("allPages", getPageCount());
        return "index";
    }

    @GetMapping("/reset")
    public String reset() {
        pictureService.setPic();
        return "redirect:/";
    }


    @PostMapping(value = "/pictures/delete")
    public ResponseEntity<Void> deletePic(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0) {
            pictureService.deletePictures(toDelete);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/pictures/archivate")
    public ResponseEntity<Void> archivatePic(@RequestParam(value = "toArchive[]",required = false) long[] toArchive){
        if (toArchive != null && toArchive.length > 0){
            pictureService.archivatePictures(toArchive);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private long getPageCount() {
        long totalCount = pictureService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}