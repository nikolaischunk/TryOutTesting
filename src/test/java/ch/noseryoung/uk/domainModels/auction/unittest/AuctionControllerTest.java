package ch.noseryoung.uk.domainModels.auction.unittest;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionService;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//oberhalb der classe
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuctionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuctionService auctionService;
    private Auction auctionToBeTested;

    private Auction auctionToBeTested2;
    private List<Auction> testAuctionList;

    private Auction auctionToBeTested3;
    private List<Auction> testAuctionListByRange;
    private int minprice;
    private int maxprice;


    AuctionControllerTest() {
    }

    @BeforeClass

    @BeforeEach
    void setUp() {
        auctionToBeTested = new Auction();
        auctionToBeTested.setId(1);
        auctionToBeTested.setName("Kette");
        auctionToBeTested.setPrice(150);

        auctionToBeTested2 = new Auction();
        auctionToBeTested2.setId(2);
        auctionToBeTested2.setName("Perle");
        auctionToBeTested2.setPrice(300);
        testAuctionList = new LinkedList<Auction>();
        testAuctionList.add(auctionToBeTested);
        testAuctionList.add(auctionToBeTested2);

        auctionToBeTested3 = new Auction();
        auctionToBeTested3.setId(3);
        auctionToBeTested3.setName("Armband");
        auctionToBeTested3.setPrice(500);
        testAuctionListByRange = new ArrayList<>();
        testAuctionListByRange.add(auctionToBeTested);
        testAuctionListByRange.add(auctionToBeTested2);
        testAuctionListByRange.add(auctionToBeTested3);
        minprice = 299;
        maxprice = 1000;
    }


    @Test
    //@WithMockUser
    public void findByID_requestAuctionById_returnsAuction() throws Exception {
        given(auctionService.findById(anyInt())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception();
            return (auctionToBeTested);
        });

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/{id}", auctionToBeTested.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(auctionToBeTested.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(auctionToBeTested.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(auctionToBeTested.getPrice()));


        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(auctionService, times(1)).findById(integerArgumentCaptor.capture());
        Assertions.assertThat(integerArgumentCaptor.getValue()).isEqualTo(auctionToBeTested.getId());
    }

    @Test
    public void NF12442_getAll_requestAllAuctions_returnAuctions() throws Exception {
        given(auctionService.findAll()).willReturn(testAuctionList);
        mvc.perform(
                MockMvcRequestBuilders.get("/auctions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(auctionToBeTested.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(auctionToBeTested.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(auctionToBeTested.getPrice()))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(auctionToBeTested.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(auctionToBeTested.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(auctionToBeTested.getPrice()));

        verify(auctionService, times(1)).findAll();
    }

    @Test
    public void NF12442_getAuctionByRange_requestAllAuctions_returnAuctions() throws Exception {
        given(auctionService.getAuctionsByRange(minprice, maxprice)).willReturn(testAuctionListByRange);

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/{minprice}/{maxprice}", 299, 1000)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testAuctionListByRange.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(testAuctionListByRange.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(testAuctionListByRange.get(0).getPrice()))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(testAuctionListByRange.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(testAuctionListByRange.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(testAuctionListByRange.get(1).getPrice()))

                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(testAuctionListByRange.get(2).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(testAuctionListByRange.get(2).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].price").value(testAuctionListByRange.get(2).getPrice()));


        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> integerArgumentCaptor2 = ArgumentCaptor.forClass(Integer.class);

        verify(auctionService, times(1)).getAuctionsByRange(integerArgumentCaptor.capture(), integerArgumentCaptor2.capture());
        Assertions.assertThat(integerArgumentCaptor.getValue()).isEqualTo(minprice);
        Assertions.assertThat(integerArgumentCaptor2.getValue()).isEqualTo(maxprice);
    }

  @AfterEach
    void tearDown() {
    }
}