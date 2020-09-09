package ch.noseryoung.uk.domainModels.auction.integrationstest;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

// Pro Business OBJ eine TestKlasse
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuctionIntegrationsTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuctionRepository auctionRepository;

    @Test
    public void findALl_requestALlAuctions_returnAllAuctions() throws Exception {

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())

                        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))

                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", Matchers.containsInAnyOrder("Uhr", "Kette", "Band")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", Matchers.containsInAnyOrder(99, 100, 101)));

    }
}
