package io.nology.postcode_backend.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeRepository;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbRepository;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbRepository;

@Component
@Profile("build")
public class JsonImporter implements CommandLineRunner {

    private final PostcodeRepository postcodeRepo;
    private final SuburbRepository suburbRepo;
    private final PostcodeSuburbRepository postcodeSuburbRepo;
    // public ModelMapper mapper;

    public JsonImporter(PostcodeRepository postcodeRepo, SuburbRepository suburbRepo,
            PostcodeSuburbRepository postcodeSuburbRepo, ModelMapper mapper) {
        this.postcodeRepo = postcodeRepo;
        this.suburbRepo = suburbRepo;
        this.postcodeSuburbRepo = postcodeSuburbRepo;
        // this.mapper = mapper;
    }

    private static String capitaliseString(String s) {
        String[] words = s.toLowerCase().split(" ");
        StringJoiner sj = new StringJoiner(" ");
        for (String word : words) {
            sj.add(word.substring(0, 1).toUpperCase() + word.substring(1));
        }
        return sj.toString();
    }

    @Override
    public void run(String... args) throws Exception {
        this.postcodeSuburbRepo.deleteAll();
        this.postcodeRepo.deleteAll();
        this.suburbRepo.deleteAll();
        ObjectMapper mapper = new ObjectMapper();
        // TypeReference<List<Postcode>> posTypeReference = new
        // TypeReference<List<Postcode>>(){};
        TypeReference<List<JsonNode>> jsonTypeReference = new TypeReference<List<JsonNode>>() {
        };
        InputStream jsonInput = getClass().getResourceAsStream("/json/postcodes.json");
        List<JsonNode> jsonValues = mapper.readValue(jsonInput, jsonTypeReference);
        Map<String, Suburb> suburbs = new HashMap<>();
        Map<String, Postcode> postcodes = new HashMap<>();
        Set<PostcodeSuburb> ps = new HashSet<>();
        for (JsonNode j : jsonValues) {
            String postcode = j.get("postcode").asText();
            String suburb = capitaliseString(j.get("suburb").asText());
            Postcode p;
            Suburb s;
            if (postcodes.containsKey(postcode)) {
                p = postcodes.get(postcode);
            } else {
                p = new Postcode(postcode);
                postcodes.put(postcode, p);
                this.postcodeRepo.saveAndFlush(p);
            }

            if (suburbs.containsKey(suburb)) {
                s = suburbs.get(suburb);
            } else {
                s = new Suburb(suburb);
                suburbs.put(suburb, s);
                this.suburbRepo.saveAndFlush(s);
            }
            PostcodeSuburb np = new PostcodeSuburb(p, s);
            ps.add(np);
        }
        this.postcodeSuburbRepo.saveAllAndFlush(ps);

    }

}
