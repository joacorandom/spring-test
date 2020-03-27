package com.example.services;

import com.example.exceptions.StudentNotFoundException;
import com.example.exceptions.InvalidPaginationParameterException;
import com.example.entities.Student;
import com.example.repositories.StudentRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  /**
   * 
   * @return
   */
  public Iterable<Student> findAll() {
    return studentRepository.findAll();
  }

  /**
   * 
   * @param pageNumber
   * @param pageSize
   * @return
   * @throws InvalidPaginationParameterException
   */
  public Iterable<Student> findByPage(int pageNumber, int pageSize) throws InvalidPaginationParameterException {
    if (pageNumber < 0 || pageSize < 0) {
      throw new InvalidPaginationParameterException();
    }

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    return studentRepository.findAll(pageable);
  }

  /**
   * 
   * @param studentId
   * @return
   * @throws StudentNotFoundException
   */
  public Student findById(Long studentId) throws StudentNotFoundException {
    Optional<Student> student = studentRepository.findById(studentId);
    if (student.isPresent()) {
      throw new StudentNotFoundException(studentId);
    }
    return student.get();
  }

  /**
   * 
   * @param student
   */
  public void create(Student student) {
    studentRepository.save(student);
  }

  /**
   * 
   * @param student
   * @throws StudentNotFoundException
   */
  public void update(Student student) throws StudentNotFoundException {
    Student updateStudent = this.findById(student.getId());

    updateStudent.setAge(student.getAge());
    updateStudent.setCourse(student.getCourse());
    updateStudent.setLastName(student.getLastName());
    updateStudent.setName(student.getName());
    updateStudent.setRut(student.getRut());

    studentRepository.save(updateStudent);
  }

  /**
   * 
   * @param studentId
   * @throws StudentNotFoundException
   */
  public void delete(Long studentId) throws StudentNotFoundException {
    Student student = this.findById(studentId);
    studentRepository.delete(student);
  }
}