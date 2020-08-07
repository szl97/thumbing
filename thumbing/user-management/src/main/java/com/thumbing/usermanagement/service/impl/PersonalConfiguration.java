package com.thumbing.usermanagement.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.sql.personal.Interest;
import com.thumbing.shared.entity.sql.personal.Job;
import com.thumbing.shared.entity.sql.personal.Occupation;
import com.thumbing.shared.repository.sql.personal.IInterestRepository;
import com.thumbing.shared.repository.sql.personal.IJobRepository;
import com.thumbing.shared.repository.sql.personal.IOccupationRepository;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.usermanagement.cache.PersonalConfigurationCache;
import com.thumbing.usermanagement.dto.output.InterestDto;
import com.thumbing.usermanagement.dto.output.JobDto;
import com.thumbing.usermanagement.dto.output.OccupationDto;
import com.thumbing.usermanagement.dto.output.PersonalConfigurationDto;
import com.thumbing.usermanagement.service.IPersonalConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 8:50
 */
@Service
@Transactional
public class PersonalConfiguration implements IPersonalConfiguration {
    @Autowired
    IJobRepository jobRepository;
    @Autowired
    IOccupationRepository occupationRepository;
    @Autowired
    IInterestRepository interestRepository;
    @Autowired
    PersonalConfigurationCache personalConfigurationCache;
    @Autowired
    Mapper mapper;

    @Override
    public PersonalConfigurationDto get() {
        PersonalConfigurationDto dto = personalConfigurationCache.get();
        if(dto == null){
            dto = fetchInDataBase();
            if(dto == null){
                return get();
            }
        }
        return dto;
    }

    @AccessLock(value = "com.thumbing.usermanagement.dto.output.PersonalConfigurationDto")
    private PersonalConfigurationDto fetchInDataBase(){
        PersonalConfigurationDto personalConfigurationDto = new PersonalConfigurationDto();
        CompletableFuture<Void> completableFutureJob = CompletableFuture
                .supplyAsync(()->findAllJob())
                .thenAccept(l->personalConfigurationDto.setJobs(l));
        CompletableFuture<Void> completableFutureOccupation = CompletableFuture
                .supplyAsync(()->findAllOccupation())
                .thenAccept(l->personalConfigurationDto.setOccupations(l));
        CompletableFuture<Void> completableFutureInterest = CompletableFuture
                .supplyAsync(()->findAllInterest())
                .thenAccept(l->personalConfigurationDto.setInterests(l));
        CompletableFuture.allOf(completableFutureJob, completableFutureOccupation, completableFutureInterest).join();
        personalConfigurationCache.set(personalConfigurationDto);
        return personalConfigurationDto;
    }

    private List<JobDto> findAllJob(){
        Sort sort = Sort.by("sort");
        List<Job> jobs = jobRepository.findAll(sort);
        return DozerUtils.mapList(mapper, jobs, JobDto.class);
    }

    private List<OccupationDto> findAllOccupation(){
        Sort sort = Sort.by("sort");
        List<Occupation> occupations = occupationRepository.findAll(sort);
        return DozerUtils.mapList(mapper, occupations, OccupationDto.class);
    }

    private List<InterestDto> findAllInterest(){
        Sort sort = Sort.by("sort");
        List<Interest> interests = interestRepository.findAll(sort);
        return DozerUtils.mapList(mapper, interests, InterestDto.class);
    }
}
