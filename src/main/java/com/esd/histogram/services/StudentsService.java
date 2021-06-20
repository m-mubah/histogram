package com.esd.histogram.services;

import com.esd.histogram.entities.Student;
import com.esd.histogram.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentsService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public void create(int marks) {
        //hibernate is not generating ids for derby db
        //ids are now manually generated
        Long id = studentRepository.findTopByOrderByIdDesc().getId();
        id++;

        Student student = new Student(id, marks);
        studentRepository.save(student);
    }

    public void createMany(List<Integer> data) {
        data.forEach(i -> {
            create(i.intValue());
        });
    }

    public int[] generateHistogram() {
        List<Student> students = getAll();

        int[] histogram = {0,0,0,0,0,0,0,0,0,0};

        students.forEach(i -> {
            int marks = i.getMarks();

            if (marks > 0 && marks < 11) {
                histogram[0] = histogram[0]+=1;
            } else if (marks > 10 && marks < 21) {
                histogram[1] = histogram[1]+=1;
            } else if (marks > 20 && marks < 31) {
                histogram[2] = histogram[2]+=1;
            } else if (marks > 30 && marks < 41) {
                histogram[3] = histogram[3]+=1;
            } else if (marks > 40 && marks < 51) {
                histogram[4] = histogram[4]+=1;
            } else if (marks > 50 && marks < 61) {
                histogram[5] = histogram[5]+=1;
            } else if (marks > 60 && marks < 71) {
                histogram[6] = histogram[6]+=1;
            } else if (marks > 70 && marks < 81) {
                histogram[7] = histogram[7]+=1;
            } else if (marks > 80 && marks < 91) {
                histogram[8] = histogram[8]+=1;
            } else if (marks > 90 && marks < 101) {
                histogram[9] = histogram[9]+=1;
            }
        });

        return histogram;
    }
}
