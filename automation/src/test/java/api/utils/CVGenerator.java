package api.utils;

import api.models.api.cv_controller.cv.*;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CVGenerator {
    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());
    Faker faker = new Faker();

    public CV generateCV() {
        CV cv = CV.builder()
                .isApproved(false)
                .certificates(generateCertificates())
                .contactInfo(generateContactInfo())
                .educations(generateEducations())
                .employeeId(9)
                .personalInfo(generatePersonalInfo())
                .professionalSummary(Stream.of("5+ year of experience in Salesforce.com (Administration, Configuration)",
                        "Experience in business processes analysis and optimization for pharmaceutical and insurance business branches",
                        "Competent in specifying business processes in a business process model",
                        "Extensive experience in gathering user requirements and converting them into software requirement specifications",
                        "Experience with design documentation, final project documentation, user guide and user training manuals preparation process",
                        "Intense experience working with Sales Cloud, Service Cloud and Community Cloud",
                        "Business reports creation experience (including the use of standard Salesforce.com tools)",
                        "Good written and verbal communication skills",
                        "Creation of workflow rules, email alerts, field updates",
                        "Creation of approval processes",
                        "Creating reports and dashboards",
                        "Creation of change sets, adding all admin components to the set",
                        "Experience in importing/exporting data using data loader",
                        "Experience working with different modes: Classic and LEX",
                        "Following changes for general functionality within releases",
                        "Creation of flows and diagrams describing project or specific phase flow",
                        "Creation of user and admin guides for implemented solution: list of components, description of configurations and screenshots",
                        "Ability to follow priorities and communicate with related team members in case of priority conflicts").collect(toList()))
                .experience(generateExperience())
                .skillGroups(generateSkillGroup())
                .build();
        return cv;
    }

    private ArrayList<Certificate> generateCertificates() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        Certificate certificate;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
        for (int i = 0; i < randomNum; i++) {
            certificate = Certificate.builder()
                    .expiration_date(getTimeMilli(false))
                    .name(faker.options().option("Salesforce Administrator", "Salesforce Developer", "Sales Manager",
                            "Marketing Manager", "Salesforce Technical Architect", "Business Analyst"))
                    .build();
            certificates.add(certificate);
        }
        return certificates;
    }

    private ContactInfo generateContactInfo() {
        return ContactInfo.builder()
                .email(faker.internet().emailAddress())
                .links(Stream.of(faker.internet().emailAddress()).collect(toList()))
                .phone(faker.phoneNumber().phoneNumber())
                .build();
    }

    private ArrayList<Education> generateEducations() {
        ArrayList<Education> educations = new ArrayList<>();
        Education education;
        education = Education.builder()
                .university_name(faker.university().name())
                .degree(faker.educator().course())
                .specialization(faker.educator().course())
                .start_year(getTimeMilli(true))
                .finish_year(getTimeMilli(false))
                .build();
        educations.add(education);
        return educations;
    }

    private PersonalInfo generatePersonalInfo() {
        return PersonalInfo.builder()
                .birth_date(getTimeMilli(false))
                .first_name(faker.name().firstName())
                .last_name(faker.name().lastName())
                .position(faker.job().position())
                .build();
    }

    private ArrayList<Experience> generateExperience() {
        ArrayList<Experience> experiences = new ArrayList<>();
        Experience experience;
        List<String> responsibilities = new ArrayList<>();
        responsibilities.add("Creating page layouts, search layouts, custom links, and related lists");
        responsibilities.add("Configuring pick lists, dependent pick lists, lookups, junction objects, master-detail relationships, validation rules, and formula fields");
        responsibilities.add("Creating Salesforce Objects and related metadata necessary to support customizations across instancess");
        responsibilities.add("Creating Users, Roles, Public Groups, sharing rules and record level permissions to manage sharing access among different users");
        responsibilities.add("Working with Salesforce data tools such as Data Loader, Eclipse Force.com IDE for data migration");
        responsibilities.add("Designing and developing Apex Classes, controller classes, controller extensions, Visual force pages and Apex triggers");
        responsibilities.add("Developing workflows and approval processes for various policy management");
        experience = Experience.builder()
                .customer_domain(faker.company().industry())
                .customer_name(faker.company().name())
                .ended_at(getTimeMilli(false))
                .project_description(faker.options().option("Innovation Development HUB (ID Hub) is a Ukrainian company, part of an international commercial group, which specializes in the development of business applications and complex solutions for electronic identification, cryptography, data protection.",
                        "An healthcare company. The company continuously creates innovative solutions and resources that help people live their healthiest lives on their terms – when and where they need it."))
                .project_name(faker.commerce().productName())
                .responsibilities(responsibilities)
                .role(faker.job().title())
                .started_at(getTimeMilli(true))
                .technologies(Stream.of("Angular", "React", "Java", "SQL", "Spring", "Maven", "Git", "Bitbucket").collect(toList()))
                .build();
        experiences.add(experience);
        return experiences;
    }

    private List<SkillGroup> generateSkillGroup() {
        List<SkillGroup> skillGroups = new ArrayList<>();
        SkillGroup skillGroup;
        List<String> skills = null;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
        for (int i = 0; i < randomNum; i++) {
            String skillType = faker.options().option("Salesforce.com technologies", "Salesforce Technical Skills", "Business Analysis skills", "Management Tools", "Database Technologies");
            switch (skillType) {
                case "Salesforce.com technologies":
                    skills = Stream.of("Sales Cloud", "CTPharma/CTMobile/CT CPG", "ServiceCloud", "CommunityCloud", "MarketingCloud", "Pardot", "Integrations").collect(Collectors.toList());
                    break;
                case "Salesforce Technical Skills":
                    skills = Stream.of("Object and field architecture", "CRUD", "FLS", "Sharing", "Validations", "Automation Processes", "Reports", "Installed Packages", "Deployment", "Data Loader", "Knowledge of Classic/Lightning Salesforce", "Licenses/Editions", "Code Base").collect(Collectors.toList());
                    break;
                case "Business Analysis skills":
                    skills = Stream.of("Understanding Project Flow and Phases", "Elicitation and Collaboration", "Requirements Gathering and Approval", "Documentation and Design", "Strategy Analysis", "Business Analysis Tools").collect(Collectors.toList());
                    break;
                case "Management Tools":
                    skills = Stream.of("Jira", "Confluence").collect(Collectors.toList());
                    break;
                case "Database Technologies":
                    skills = Stream.of("Microsoft SQL Server", "Oracle 11g").collect(Collectors.toList());
            }
            skillGroup = SkillGroup.builder()
                    .skillType(skillType)
                    .skills(skills)
                    .build();
            skillGroups.add(skillGroup);
        }
        return skillGroups;
    }

    private long getTimeMilli(boolean isStartDate) {
        if (isStartDate) {
            LocalDate startDate = LocalDate.of(1970, 1, 1);
            LocalDate todayDate = LocalDate.now();
            LocalDate endDate = todayDate.minus(Period.ofDays(1));
            List<LocalDate> days = startDate.datesUntil(endDate)
                    .collect(Collectors.toList());
            Instant instant  = days.get(faker.random().nextInt(days.size())).atStartOfDay(ZoneId.systemDefault()).toInstant();
            return instant.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
        }
        return Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli();
    }
}
