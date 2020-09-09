package ch.noseryoung.uk.domainModels.auction;

import ch.noseryoung.uk.domainModels.auction.dto.AuctionDTO;
import ch.noseryoung.uk.domainModels.auction.dto.AuctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

// This is an example controller with CRUD logic
@RestController
@RequestMapping("/auctions")
public class AuctionController {

    // The newly created service to be injected
    private final AuctionService auctionService;

    private final AuctionMapper auctionMapper;

    // Injecting the dependency via constructor
    @Autowired
    public AuctionController(AuctionService auctionService, AuctionMapper auctionMapper) {
        this.auctionService = auctionService;
        this.auctionMapper = auctionMapper;
    }

    // This endpoint creates a new auction with the data given, currently this process is being mocked
    @PostMapping({"/", ""})
    public ResponseEntity<AuctionDTO> create(@RequestBody Auction auction) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.create(auction)), HttpStatus.CREATED);
    }

    // This endpoint retrieves all auctions as a list
    @GetMapping({"/", ""})
    public ResponseEntity<List<AuctionDTO>> getAll() {
        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.findAll()), HttpStatus.OK);
    }

    // This endpoint retrieves a single auction by it's id
    @GetMapping("/{id}")
    public ResponseEntity<AuctionDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.findById(id)), HttpStatus.OK);
    }

    // This endpoint updates an existing auction with the id and data given, currently this process is being mocked
    @PutMapping("/{id}")
    public ResponseEntity<AuctionDTO> updateById(@PathVariable int id, @RequestBody AuctionDTO input) { //AuctionDTO = UserINPUT
        Auction auction = auctionMapper.fromDTO(input); // input also auctionDTO -> Businessobject
        Auction output = auctionService.updateById(id, auction); //BusinessObject wird an Servicelayer weitergegeben
        return new ResponseEntity<>(auctionMapper.toDTO(output), HttpStatus.ACCEPTED); //Rückgabe aus dem Servicelayer wird zu einem DTO gewandelt und ans FE gesendet
    }

    // This endpoint deletes an existing auction with the id given, currently this process is being mocked
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        auctionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{minprice}/{maxprice}")
     public ResponseEntity<List<AuctionDTO>> getAuctionByRange(@PathVariable int minprice, @PathVariable int maxprice) {
        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.getAuctionsByRange(minprice, maxprice)), HttpStatus.OK);
    }
}
