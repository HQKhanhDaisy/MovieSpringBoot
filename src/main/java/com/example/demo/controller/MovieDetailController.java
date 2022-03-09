package com.example.demo.controller;

import com.example.demo.DTO.MovieDetailDTO;
import com.example.demo.DTO.MovieDetailPage;
import com.example.demo.service.MovieDetailService;
import com.example.demo.util.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movieDetail")
public class MovieDetailController {
    private final MovieDetailService movieDetailService;

    @GetMapping("/page")
    public MovieDetailPage getAllUsers(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return movieDetailService.getAllMovieDetailPage(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/getMovieDetail/{id}")
    public ResponseEntity<MovieDetailDTO> getMovieDetailById(@PathVariable int id) {
        return new ResponseEntity<>(movieDetailService.getMovieById(id), HttpStatus.OK);
    }

    @PostMapping("/addMovie")
    public ResponseEntity<String> addMovieDetail(@RequestBody MovieDetailDTO movieDetailDTO) throws Exception {
        return new ResponseEntity<>(movieDetailService.addMovieDetail(movieDetailDTO), HttpStatus.OK);
    }

    @PutMapping("/editMovieDetail")
    public ResponseEntity<String> editMovieDetail(@RequestBody MovieDetailDTO movieDetailDTO) throws Exception {
        return new ResponseEntity<>(movieDetailService.editMovieDetail(movieDetailDTO), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeMovie(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(movieDetailService.deleteMovieDetail(id), HttpStatus.OK);
    }
}