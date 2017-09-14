package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


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

        // TODO #1 - get the Job with the given ID and pass it into the view
        // [...]/job?id=X
        Job selectedJob = jobData.findById(id);
        model.addAttribute("selectedJob", selectedJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors, @RequestParam String name, @RequestParam int employerId, @RequestParam int locationId, @RequestParam int positionTypeId, @RequestParam int coreCompetencyId) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            return "new-job";
        }

        Job newJob = new Job();
        newJob.setName(name);
        Employer employer = jobData.getEmployers().findById(employerId);
        newJob.setEmployer(employer);
        Location location = jobData.getLocations().findById(locationId);
        newJob.setLocation(location);
        PositionType positionType = jobData.getPositionTypes().findById(positionTypeId);
        newJob.setPositionType(positionType);
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(coreCompetencyId);
        newJob.setCoreCompetency(coreCompetency);

        jobData.add(newJob);

        return "redirect:/job?id=" + newJob.getId();

    }
}
