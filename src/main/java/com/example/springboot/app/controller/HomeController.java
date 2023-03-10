package com.example.springboot.app.controller;

import com.example.springboot.app.dto.Video;
import com.example.springboot.app.dto.VideoSearch;
import com.example.springboot.app.entity.VideoEntity;
import com.example.springboot.app.dto.UniversalSearch;
import com.example.springboot.app.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private final VideoService videoService;

    @Autowired
    public HomeController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", this.videoService.getVideos());
        return "index";
    }

    @GetMapping("/react")
    public String react() {
        return "react";
    }

    @PostMapping("/new-video")
    public String newVideo(@ModelAttribute Video newVideo) {
        this.videoService.create(newVideo);
        return "redirect:/";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearch(@ModelAttribute VideoSearch videoSearch, Model model) {
        final List<VideoEntity> videos = this.videoService.search(videoSearch);
        model.addAttribute("videos", videos);
        return "index";
    }

    @PostMapping("/universal-search")
    public String universalSearch(@ModelAttribute UniversalSearch universalSearch, Model model) {
        final List<VideoEntity> videos = this.videoService.search(universalSearch);
        model.addAttribute("videos", videos);
        return "index";
    }

}
