package ch.noseryoung.uk.domainModels.auction.unittest;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionRepository;
import ch.noseryoung.uk.domainModels.auction.AuctionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//oberhalb der classe
@RunWith(SpringRunner.class)
@SpringBootTest
class AuctionServiceTest {

    @Autowired
    private AuctionService auctionService;
    @MockBean
    private AuctionRepository auctionRepository;

    private Auction auctionToBeTested1;
    private Auction auctionToBeTested2;
    private Auction auctionToBeTested3;
    private List<Auction> testAuctionList;
    private List<Auction> testAuctionListByRange;

    private int minprice;
    private int maxprice;


    @BeforeEach
    void setUp() {
        auctionToBeTested1 = new Auction();
        auctionToBeTested1.setId(1);
        auctionToBeTested1.setName("Kette");
        auctionToBeTested1.setPrice(99);

        auctionToBeTested2 = new Auction();
        auctionToBeTested2.setId(2);
        auctionToBeTested2.setName("Perle");
        auctionToBeTested2.setPrice(100);

        auctionToBeTested3 = new Auction();
        auctionToBeTested3.setId(3);
        auctionToBeTested3.setName("Armband");
        auctionToBeTested3.setPrice(101);


        testAuctionListByRange = new ArrayList<>();

        testAuctionListByRange.add(auctionToBeTested1);
        testAuctionListByRange.add(auctionToBeTested2);
        testAuctionListByRange.add(auctionToBeTested3);

        minprice = 1;
        maxprice = 100;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void NF12442_getAuctionByRange_requestAllAuctions_returnAuctions() throws Exception {
        given(auctionRepository.findAll()).willReturn(testAuctionListByRange);
        testAuctionList = auctionService.getAuctionsByRange(minprice, maxprice);


        Assertions.assertThat(!testAuctionList.contains(auctionToBeTested3));
        Assertions.assertThat(testAuctionList.contains(auctionToBeTested1));
        Assertions.assertThat(testAuctionList.contains(auctionToBeTested2));
        verify(auctionRepository, times(1)).findAll();
    }

}