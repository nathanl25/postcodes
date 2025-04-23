package io.nology.postcode_backend.postcode;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.postcodeSuburb.FilterResultsDTO;
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
        List<Suburb> suburbs = this.suburbService.getByIds(data.getSuburbIds());
        this.repo.saveAndFlush(newPostcode);
        this.postcodeSuburbService.setSuburbList(newPostcode, suburbs);
        return new PostcodeDTO(newPostcode, suburbs);
    }

    public Optional<Postcode> getByPostcode(String postcode) {
        return this.repo.findOneByPostcode(postcode);
    }

    public PostcodeDTO getPostcodeData(Postcode postcode) {
        return this.postcodeSuburbService.getOnePostcode(postcode);
    }

    public List<Postcode> getByPostcodes(Set<String> postcodes) throws NotFoundException {
        if (postcodes == null) {
            return null;
        }
        List<Postcode> postcodeList = this.repo.findByPostcodeIn(postcodes);
        if (postcodeList.size() != postcodes.size()) {
            throw new NotFoundException("Matching postcodes not found");
        }
        return postcodeList;
    }

    public List<PostcodeDTO> getAll() {
        return this.postcodeSuburbService.getAllPostcodes();
    }

    public void deletePostcode(Postcode toDelete) {
        this.postcodeSuburbService.clearSuburbList(toDelete);
        this.repo.delete(toDelete);
    }

    public boolean validPostcodeUpdate(Postcode toUpdate, UpdatePostcodeDTO data) {
        if (toUpdate.getPostcode().equals(data.getPostcode())) {
            return true;
        }
        if (data.hasPostcode() && this.getByPostcode(data.getPostcode()).isPresent()) {
            return false;
        }
        return true;
    }

    public PostcodeDTO updatePostcode(Postcode toUpdate, UpdatePostcodeDTO data) throws NotFoundException {
        List<Suburb> suburbs = this.suburbService.getByIds(data.getSuburbIds());
        this.postcodeSuburbService.setSuburbList(toUpdate, suburbs);
        mapper.map(data, toUpdate);
        this.repo.saveAndFlush(toUpdate);
        suburbs = suburbs != null ? suburbs : this.postcodeSuburbService.getSuburbList(toUpdate);
        return new PostcodeDTO(toUpdate, suburbs);
    }

    public List<PostcodeDTO> findByCriteria(FilterResultsDTO data) {
        List<PostcodeDTO> filtered = this.postcodeSuburbService.queryPostcodes(data);
        return filtered;
    }
}
