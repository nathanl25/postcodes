package io.nology.postcode_backend.suburb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeService;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbService;
import jakarta.validation.Valid;

// import io.nology.postcode_backend.Suburb;

@Service
public class SuburbService {

    private SuburbRepository repo;
    private PostcodeService postcodeService;
    private PostcodeSuburbService postcodeSuburbService;
    private ModelMapper mapper;

    SuburbService(SuburbRepository repo, @Lazy PostcodeService postcodeService,
            @Lazy PostcodeSuburbService postcodeSuburbService, ModelMapper mapper) {
        this.repo = repo;
        this.postcodeService = postcodeService;
        this.postcodeSuburbService = postcodeSuburbService;
        this.mapper = mapper;
    }

    public List<Suburb> getByIds(Set<Long> suburbIds) throws NotFoundException {
        if (suburbIds == null) {
            // System.out.println("suburb ids null");
            // List<Suburb> list = new ArrayList<>();
            // list.add(null);
            // return list;
            return null;
        }
        List<Suburb> suburbs = this.repo.findAllById(suburbIds);
        if (suburbs.size() != suburbIds.size()) {
            throw new NotFoundException("Matching suburbs not found");
        }
        return suburbs;
    }

    public List<SuburbDTO> getAll() {
        return this.postcodeSuburbService.getAllSuburbs();
    }

    public Optional<Suburb> getByName(String name) {
        return this.repo.findOneByName(name);
    }

    public Optional<Suburb> getById(Long id) {
        return this.repo.findById(id);
    }

    public void deleteSuburb(Suburb toDelete) {
        this.postcodeSuburbService.clearPostcodeList(toDelete);
        this.repo.delete(toDelete);
    }

    public SuburbDTO createSuburb(CreateSuburbDTO data) throws NotFoundException {
        Suburb newSuburb = new Suburb(data.getName());
        // if (data.hasPostcodes()) {
        List<Postcode> postcodes = this.postcodeService.getByPostcodes(data.getPostcodes());
        this.repo.saveAndFlush(newSuburb);
        this.postcodeSuburbService.setPostcodeList(newSuburb, postcodes);
        // return newSuburb;
        // }
        // this.repo.saveAndFlush(newSuburb);
        return new SuburbDTO(newSuburb, postcodes);
    }

    public SuburbDTO updateSuburb(Suburb toUpdate, UpdateSuburbDTO data) throws NotFoundException {
        List<Postcode> postcodes = this.postcodeService.getByPostcodes(data.getPostcodes());
        this.postcodeSuburbService.setPostcodeList(toUpdate, postcodes);
        mapper.map(data, toUpdate);
        System.out.println(data.getName());
        System.out.println(toUpdate.getName());
        this.repo.saveAndFlush(toUpdate);
        postcodes = postcodes != null ? postcodes : this.postcodeSuburbService.getPostcodeList(toUpdate);
        return new SuburbDTO(toUpdate, postcodes);
    }
}
