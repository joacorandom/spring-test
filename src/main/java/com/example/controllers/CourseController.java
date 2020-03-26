package com.example.controllers;

import com.example.models.Course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

  @GetMapping("/")
  public Course[] getPages() {
    return null;
  }

  @GetMapping("/all")
  public Course[] getAll() {
    return null;
  }

  @GetMapping("/{id}")
  public Course get(@PathVariable int id) {
    return null;
  }
}