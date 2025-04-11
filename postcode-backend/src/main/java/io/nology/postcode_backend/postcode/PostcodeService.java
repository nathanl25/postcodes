package io.nology.postcode_backend.postcode;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbService;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbService;

@Service
public class PostcodeService {
    private PostcodeRepository repo;
    private PostcodeSuburbService postcodeSuburbService;
    private SuburbService suburbService;
    private ModelMapper mapper;

    public PostcodeService(PostcodeRepository repo, @Lazy PostcodeSuburbService postcodeSuburbService,
            @Lazy SuburbService suburbService, ModelMapper mapper) {
        this.repo = repo;
        this.postcodeSuburbService = postcodeSuburbService;
        this.suburbService = suburbService;
        this.mapper = mapper;
    }

    public PostcodeDTO createPostcode(CreatePostcodeDTO data) throws NotFoundException {
        Postcode newPostcode = new Postcode(data.getPostcode());
        // if (data.hasSuburbIds()) {
        List<Suburb> suburbs = this.suburbService.getByIds(data.getSuburbIds());
        this.repo.saveAndFlush(newPostcode);
        this.postcodeSuburbService.setSuburbList(newPostcode, suburbs);
        return new PostcodeDTO(newPostcode, suburbs);
        // }
        // this.repo.saveAndFlush(newPostcode);
        // return new PostcodeDTO(newPostcode);

        // if (data.hasSuburbIds()) {
        // List<Suburb> suburbs = this.suburbService.getByIds(data.getSuburbIds());
        // this.repo.saveAndFlush(newPostcode);
        // this.postcodeSuburbService.setSuburbList(newPostcode, suburbs);
        // return new PostcodeDTO(newPostcode, suburbs);
        // }
        // this.repo.saveAndFlush(newPostcode);
        // return new PostcodeDTO(newPostcode);
    }

    public Optional<Postcode> getByPostcode(String postcode) {
        // return this.repo.findById(id);
        return this.repo.findOneByPostcode(postcode);
    }

    public List<Postcode> getAll() {
        return this.repo.findAll();
    }

    public List<Postcode> getByPostcodes(Set<String> postcodes) throws NotFoundException {
        List<Postcode> postcodeList = this.repo.findByPostcodeIn(postcodes);
        if (postcodeList.size() != postcodes.size()) {
            throw new NotFoundException("Matching postcodes not found");
        }
        return postcodeList;
    }

    public List<PostcodeDTO> getAllRaw() {
        return this.postcodeSuburbService.getAllPostcodes();
    }

    public void deletePostcode(Postcode toDelete) {
        this.postcodeSuburbService.clearSuburbList(toDelete);
        this.repo.delete(toDelete);
    }

    public PostcodeDTO updatePostcode(Postcode toUpdate, UpdatePostcodeDTO data) throws NotFoundException {
        // List<Suburb> suburbs = null;
        // if (data.hasSuburbIds()) {
        List<Suburb> suburbs = this.suburbService.getByIds(data.getSuburbIds());
        this.postcodeSuburbService.setSuburbList(toUpdate, suburbs);
        // }
        mapper.map(data, toUpdate);
        this.repo.saveAndFlush(toUpdate);
        // if (suburbs == null) {
        // suburbs = this.postcodeSuburbService.getSuburbList(toUpdate);
        // }
        suburbs = suburbs != null ? suburbs : this.postcodeSuburbService.getSuburbList(toUpdate);
        System.out.println(data.getPostcode());
        System.out.println(toUpdate.getPostcode());
        return new PostcodeDTO(toUpdate, suburbs);
    }
}
