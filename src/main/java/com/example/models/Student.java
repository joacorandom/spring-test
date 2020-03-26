package com.example.models;

import com.example.utils.RUT;

public class Student {
  private RUT rut;
  private String name;
  private String lastName;
  private int age;
  private Course course;

  public Student() {
  }

  public Student(RUT rut, String name, String lastName, int age, Course course) {
    this.rut = rut;
    this.name = name;
    this.lastName = lastName;
    this.age = age;
    this.course = course;
  }

  public RUT getRut() {
    return this.rut;
  }

  public void setRut(RUT rut) {
    this.rut = rut;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Course getCourse() {
    return this.course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Student rut(RUT rut) {
    this.rut = rut;
    return this;
  }

  public Student name(String name) {
    this.name = name;
    return this;
  }

  public Student lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public Student age(int age) {
    this.age = age;
    return this;
  }

  public Student course(Course course) {
    this.course = course;
    return this;
  }

  @Override
  public String toString() {
    return "{" + " rut='" + getRut() + "'" + ", name='" + getName() + "'" + ", lastName='" + getLastName() + "'"
        + ", age='" + getAge() + "'" + ", course='" + getCourse() + "'" + "}";
  }
}