package io.nology.postcode_backend.postcodeSuburb;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.suburb.Suburb;

public interface PostcodeSuburbRepository extends JpaRepository<PostcodeSuburb, Long> {
    List<PostcodeSuburb> findAllByOrderByPostcodePostcode();

    List<PostcodeSuburb> findByPostcodeNotNullOrderByPostcodePostcode();

    List<PostcodeSuburb> findByPostcodeNotNullOrderByPostcodePostcodeAscSuburbNameAsc();

    List<PostcodeSuburb> findBySuburbNotNullOrderBySuburbName();

    List<PostcodeSuburb> findBySuburbNotNullOrderBySuburbNameAscPostcodePostcodeAsc();

    List<PostcodeSuburb> findAllByOrderBySuburbName();

    List<PostcodeSuburb> findByPostcodeOrderBySuburbName(Postcode postcode);

    List<PostcodeSuburb> findByPostcode(Postcode postcode);

    List<PostcodeSuburb> findBySuburb(Suburb suburb);

    void deleteByPostcode(Postcode postcode);

    void deleteBySuburb(Suburb suburb);

    @NativeQuery(value = "SELECT * FROM postcode_suburbs LEFT JOIN postcodes ON postcodes.id = postcode_suburbs.postcode_id ORDER BY p_name")
    List<PostcodeSuburb> findRawMap();
}
