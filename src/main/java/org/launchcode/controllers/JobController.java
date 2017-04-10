package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        Job jobByID = jobData.findById(id);
        model.addAttribute("jobName", jobByID.getName());
        model.addAttribute("jobEmp", jobByID.getEmployer());
        model.addAttribute("jobLoc", jobByID.getLocation());
        model.addAttribute("jobPType", jobByID.getPositionType());
        model.addAttribute("jobSkill", jobByID.getCoreCompetency());
        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    static HashMap<String, String> cheeses = new HashMap<>();

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@Valid JobForm jobForm, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute(new JobForm());
            model.addAttribute("errors", "Name may not be empty.");
            return "new-job";
        }

        Employer choiceEmployer = new Employer(jobForm.getEmployer());
        Location choiceLocation = new Location(jobForm.getLocation());
        PositionType choicePositionType = new PositionType(jobForm.getPositionType());
        CoreCompetency choiceCompetency = new CoreCompetency(jobForm.getCoreCompetency());

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        Job newJob = new Job(jobForm.getName(),
                choiceEmployer,
                choiceLocation,
                choicePositionType,
                choiceCompetency);
        jobData.add(newJob);

//        return "new-job";
        int id = newJob.getId();
        return "redirect:?id=" + id;
    }
}
