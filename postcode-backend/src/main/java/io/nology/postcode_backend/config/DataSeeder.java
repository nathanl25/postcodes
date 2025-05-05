package io.nology.postcode_backend.config;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeRepository;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbRepository;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbRepository;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final PostcodeRepository postcodeRepo;
    private final SuburbRepository suburbRepo;
    private final PostcodeSuburbRepository postcodeSuburbRepo;
    private final Faker faker = new Faker();

    public DataSeeder(PostcodeRepository postcodeRepo, SuburbRepository suburbRepo,
            PostcodeSuburbRepository postcodeSuburbRepo) {
        this.postcodeRepo = postcodeRepo;
        this.suburbRepo = suburbRepo;
        this.postcodeSuburbRepo = postcodeSuburbRepo;
    }

    private static String capitaliseString(String s) {
        String[] words = s.toLowerCase().split(" ");
        StringJoiner sj = new StringJoiner(" ");
        for (String word : words) {
            sj.add(word.substring(0, 1).toUpperCase() + word.substring(1));
        }
        // Stream<String> st = Arrays.stream(s.toLowerCase().split(" "));
        // st.map((word) -> {
        // return word.substring(0, 0).toUpperCase() + word.substring(1);
        // });
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
        for (JsonNode j : jsonValues) {
            String postcode = j.get("postcode").asText();
            String suburb = j.get("suburb").asText();
            System.out.println(postcode);
            System.out.println(capitaliseString(suburb));
        }

        // Ensure at least 20 suburbs
        for (int i = 0; i <= 20; i++) {
            Suburb newSuburb = new Suburb();
            newSuburb.setName(faker.address().cityName());
            this.suburbRepo.saveAndFlush(newSuburb);
        }
        // Ensure at least 20 postcodes
        for (int i = 0; i <= 20; i++) {
            Postcode newPostCode = new Postcode();
            newPostCode.setPostcode(faker.number().digits(4));
            this.postcodeRepo.saveAndFlush(newPostCode);
        }
        List<Postcode> postcodes = this.postcodeRepo.findAll();
        List<Suburb> suburbs = this.suburbRepo.findAll();
        Set<Suburb> unusedSuburbs = new HashSet<Suburb>(suburbs);
        int suburbAmt = suburbs.size() - 1;
        // int postcodeAmt = postcodes.size() - 1;

        // Randomly allocate suburbs to postcodes
        for (Postcode p : postcodes) {
            int suburbEntries = faker.number().numberBetween(0, 3);
            if (suburbEntries == 0) {
                PostcodeSuburb dataEntry = new PostcodeSuburb(p, null);
                this.postcodeSuburbRepo.saveAndFlush(dataEntry);
                continue;
            }
            Set<Integer> randomSuburbIndexes = new HashSet<>();
            while (randomSuburbIndexes.size() < suburbEntries) {
                randomSuburbIndexes.add(faker.number().numberBetween(0, suburbAmt));
            }
            randomSuburbIndexes.forEach((num) -> {
                Suburb randomSuburb = suburbs.get(num);
                unusedSuburbs.remove(randomSuburb);
                PostcodeSuburb newEntry = new PostcodeSuburb(p, randomSuburb);
                this.postcodeSuburbRepo.saveAndFlush(newEntry);
            });
        }
        // Suburbs that weren't allocated to a postcode are added to the join table
        unusedSuburbs.forEach(((s) -> {
            PostcodeSuburb suburbNoPostcode = new PostcodeSuburb(null, s);
            this.postcodeSuburbRepo.saveAndFlush(suburbNoPostcode);
        }));

    }
}
