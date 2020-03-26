package com.example.models;

public class Course {
  private String name;
  private String code;

  public Course() {
  }

  public Course(final String name, final String code) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public Course name(final String name) {
    this.name = name;
    return this;
  }

  public Course code(final String code) {
    this.code = code;
    return this;
  }

  @Override
  public String toString() {
    return "{" + " name='" + getName() + "'" + ", code='" + getCode() + "'" + "}";
  }

}