package com.example.springboot.service;

import com.example.springboot.dto.UniversalSearch;
import com.example.springboot.dto.Video;
import com.example.springboot.dto.VideoSearch;
import com.example.springboot.entity.VideoEntity;
import com.example.springboot.repository.VideoRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class VideoService {

    private List<Video> videos =
            List.of(new Video("Need HELP with your SPRING BOOT 3 App?"),
                    new Video("Don't do THIS to your own CODE!"),
                    new Video("SECRETS to fix BROKEN CODE!"));

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

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

    public List<VideoEntity> search(VideoSearch videoSearch) {
        if (!Objects.isNull(videoSearch)) {
            if (StringUtils.isNotBlank(videoSearch.name())
                    && StringUtils.isNotBlank(videoSearch.description())) {
                return this.videoRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase(videoSearch.name(),
                        videoSearch.description());
            }
            if (StringUtils.isNotBlank(videoSearch.name())) {
                return this.videoRepository.findByNameContainsIgnoreCase(videoSearch.name());
            }
            if (StringUtils.isNotBlank(videoSearch.description())) {
                return this.videoRepository.findByDescriptionContainsIgnoreCase(videoSearch.description());
            }
        }
        return Collections.emptyList();
    }

    public List<VideoEntity> search(UniversalSearch universalSearch) {
        final VideoEntity probe = new VideoEntity();
        final String value = universalSearch.value();
        if (StringUtils.isNotBlank(value)) {
            probe.setName(value);
            probe.setDescription(value);
        }
        final Example<VideoEntity> example = Example.of(probe,
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return this.videoRepository.findAll(example);
    }

}
