package io.nology.postcode_backend.postcodeSuburb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeDTO;
import io.nology.postcode_backend.suburb.Suburb;

@Service
public class PostcodeSuburbService {

    private PostcodeSuburbRepository repo;

    public PostcodeSuburbService(PostcodeSuburbRepository repo) {
        this.repo = repo;
    }

    public void setSuburbList(Postcode postcode, List<Suburb> suburbs) {
        if (suburbs == null) {
            // System.out.println("No suburb");
            // this.repo.saveAndFlush(new PostcodeSuburb(postcode, null));
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
        // return new ArrayList<>();
    }

    public void clearSuburbList(Postcode postcode) {
        // this.repo.deleteByPostcode(postcode);
        List<PostcodeSuburb> toDelete = this.repo.findByPostcode(postcode);
        this.repo.deleteAll(toDelete);
        // this.repo.
    }

    public void clearPostcodeList(Suburb suburb) {
        this.repo.deleteBySuburb(suburb);
    }

    public void setPostcodeList(Suburb suburb, List<Postcode> postcodes) {
        List<PostcodeSuburb> suburbPostcodes = new ArrayList<>();
        for (Postcode postcode : postcodes) {
            suburbPostcodes.add(new PostcodeSuburb(postcode, suburb));
        }
        this.repo.saveAllAndFlush(suburbPostcodes);
        return;
    }

    public List<PostcodeDTO> getAllPostcodes() {
        Map<Postcode, List<Suburb>> postcodeList = new LinkedHashMap<Postcode, List<Suburb>>();
        List<PostcodeSuburb> rawList = this.repo.findAllByOrderByPostcodePostcode();
        for (PostcodeSuburb p : rawList) {
            System.out.println(p.getPostcode().getPostcode());
            postcodeList.computeIfAbsent(p.getPostcode(), k -> new ArrayList<>())
                    .add(p.getSuburb());
        }
        return postcodeList.entrySet().stream()
                .map(p -> {
                    System.out.println(p.getKey().getPostcode());
                    return new PostcodeDTO(p.getKey(), p.getValue());
                })
                .toList();
    }
}
