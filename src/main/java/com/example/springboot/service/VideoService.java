package com.example.springboot.service;

import com.example.springboot.dto.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private List<Video> videos =
            List.of(new Video("Need HELP with your SPRING BOOT 3 App?"),
                    new Video("Don't do THIS to your own CODE!"),
                    new Video("SECRETS to fix BROKEN CODE!"));

    public List<Video> getVideos() {
        return this.videos;
    }

    /**
     * while it takes a couple of extra steps, this code ensures that
     * no existing copy of data will be accidentally mutated by invoking another method.
     * Therefore, side effects are prevented while ensuring a consistent state is maintained.
     */
    public Video create(Video video) {
        final List<Video> extendedVideos = new ArrayList<>(this.videos);
        extendedVideos.add(video);
        this.videos = List.copyOf(extendedVideos);
        return video;
    }

}
