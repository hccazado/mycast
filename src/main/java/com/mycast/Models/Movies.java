package com.mycast.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movies (int page,
                      List<MovieValue> results,
                      int total_pages,
                      int total_results)
{}
