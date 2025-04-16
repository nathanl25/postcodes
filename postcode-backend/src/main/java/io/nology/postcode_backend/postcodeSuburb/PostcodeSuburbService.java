package io.nology.postcode_backend.postcodeSuburb;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeDTO;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbDTO;

@Service
public class PostcodeSuburbService {

    private PostcodeSuburbRepository repo;

    public PostcodeSuburbService(PostcodeSuburbRepository repo) {
        this.repo = repo;
    }

    public void setSuburbList(Postcode postcode, List<Suburb> suburbs) {
        if (suburbs == null) {
            return;
        }
        clearSuburbList(postcode);
        if (suburbs.isEmpty()) {
            this.repo.saveAndFlush(new PostcodeSuburb(postcode, null));
            return;
        }
        List<PostcodeSuburb> postcodeSuburbs = new ArrayList<>();
        for (Suburb suburb : suburbs) {
            postcodeSuburbs.add(new PostcodeSuburb(postcode, suburb));
        }
        this.repo.saveAllAndFlush(postcodeSuburbs);
        return;
    }

    public List<Suburb> getSuburbList(Postcode postcode) {
        List<PostcodeSuburb> rawList = this.repo.findByPostcode(postcode);
        return rawList.stream().map(p -> p.getSuburb()).toList();

    }

    public List<Postcode> getPostcodeList(Suburb suburb) {
        List<PostcodeSuburb> rawList = this.repo.findBySuburb(suburb);
        return rawList.stream().map(s -> s.getPostcode()).toList();
    }

    public void clearSuburbList(Postcode postcode) {

        List<PostcodeSuburb> toDelete = this.repo.findByPostcode(postcode);
        this.repo.deleteAll(toDelete);

    }

    public void clearPostcodeList(Suburb suburb) {

        List<PostcodeSuburb> toDelete = this.repo.findBySuburb(suburb);
        this.repo.deleteAll(toDelete);
    }

    public void setPostcodeList(Suburb suburb, List<Postcode> postcodes) {
        if (postcodes == null) {
            return;
        }
        clearPostcodeList(suburb);
        if (postcodes.isEmpty()) {
            this.repo.saveAndFlush(new PostcodeSuburb(null, suburb));
            return;
        }
        List<PostcodeSuburb> suburbPostcodes = new ArrayList<>();
        for (Postcode postcode : postcodes) {
            suburbPostcodes.add(new PostcodeSuburb(postcode, suburb));
        }
        this.repo.saveAllAndFlush(suburbPostcodes);
        return;
    }

    public List<SuburbDTO> getAllSuburbs() {
        Map<Suburb, List<Postcode>> suburbList = new LinkedHashMap<>();
        List<PostcodeSuburb> rawList = this.repo.findBySuburbNotNullOrderBySuburbNameAscPostcodePostcodeAsc();
        for (PostcodeSuburb p : rawList) {
            suburbList.computeIfAbsent(p.getSuburb(), k -> new ArrayList<>())
                    .add(p.getPostcode());
        }
        return suburbList.entrySet().stream()
                .map(p -> new SuburbDTO(p.getKey(), p.getValue()))
                .toList();
    }

    public List<PostcodeDTO> getAllPostcodes() {
        Map<Postcode, List<Suburb>> postcodeList = new LinkedHashMap<Postcode, List<Suburb>>();
        List<PostcodeSuburb> rawList = this.repo.findByPostcodeNotNullOrderByPostcodePostcodeAscSuburbNameAsc();
        for (PostcodeSuburb p : rawList) {
            postcodeList.computeIfAbsent(p.getPostcode(), k -> new ArrayList<>())
                    .add(p.getSuburb());
        }
        return postcodeList.entrySet().stream()
                .map(p -> new PostcodeDTO(p.getKey(), p.getValue()))
                .toList();
    }

    public PostcodeDTO getOnePostcode(Postcode postcode) {
        List<Suburb> suburbList = new ArrayList<>();
        List<PostcodeSuburb> rawSuburbs = this.repo.findByPostcodeOrderBySuburbName(postcode);
        for (PostcodeSuburb p : rawSuburbs) {
            suburbList.add(p.getSuburb());
        }
        return new PostcodeDTO(postcode, suburbList);
    }
}
