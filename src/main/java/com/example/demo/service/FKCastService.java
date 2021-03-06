package com.example.demo.service;

import com.example.demo.DTO.FKCastDTO;
import com.example.demo.DTO.MovieCastDTO;
import com.example.demo.DTO.MovieDetailDTO;
import com.example.demo.model.FKCast;

import java.util.List;

public interface FKCastService {
    //get all cast on a movie
    List<FKCastDTO> getCastByMovieId(int movieDetailId);

    //get all movie on a cast
    List<MovieDetailDTO> getMovieDetailByCastId(int castId);

    void deleteFkCastByCastId(int castId);

    void deleteFkCastByMovieId(int movieDetailId);

    List<FKCastDTO> getAllFKCast();
    void removeFkCastExits(Integer movieId);
}
