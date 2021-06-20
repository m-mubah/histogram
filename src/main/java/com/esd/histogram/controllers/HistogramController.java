package com.esd.histogram.controllers;

import com.esd.histogram.entities.Student;
import com.esd.histogram.services.StudentsService;
import com.esd.histogram.viewmodels.Histogram;
import com.esd.histogram.viewmodels.Marks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/")
public class HistogramController {
    private final StudentsService studentsService;

    @Autowired
    public HistogramController(StudentsService  studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping
    public String get(Model model) {
        int[] data = studentsService.generateHistogram();

        Histogram histogram = new Histogram(data);

        model.addAttribute("histogram", histogram);
        model.addAttribute("marks", new Marks());

        return "index";
    }

    @PostMapping("/student")
    public String newStudent(@ModelAttribute Marks marks, RedirectAttributes redirectAttributes) {
        studentsService.create(marks.getValue());

        redirectAttributes.addFlashAttribute("success", "Student created successfully!");
        return "redirect:/";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if(!file.getContentType().equalsIgnoreCase("text/plain")){
            redirectAttributes.addFlashAttribute("error", "Only plain-text .txt files allowed!");
        } else {
            redirectAttributes.addFlashAttribute("success", "File uploaded successfully!");

            try {
              String[] temp = new String(file.getBytes()).split("\\r?\\n");
              List<Integer> data = new ArrayList();

              for (String s : temp) {
                  data.add(Integer.parseInt(s));
              }

              studentsService.createMany(data);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "An error occurred: \n" + e.getMessage());
            }
        }

        return "redirect:/";
    }
}
