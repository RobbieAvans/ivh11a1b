package edu.avans.hartigehap.web.controller.rs;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import edu.avans.hartigehap.domain.ConcreteHallReservation;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationAPIWrapper;
import edu.avans.hartigehap.domain.HallReservationOption;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.web.controller.rs.testutil.RestTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HallReservationAPITest.class })
@WebAppConfiguration
@ImportResource({ "classpath:/test-root-context.xml", "classpath:*servlet-context.xml" })
public class HallReservationAPITest {

    @Autowired
    private HallReservationRS hallReservationRS;

    @Autowired
    private HallReservationService hallReservationServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        Mockito.reset(hallReservationServiceMock);

        mockMvc = standaloneSetup(hallReservationRS).build();
    }

    @Bean
    public HallReservationService hallReservationService() {
        return Mockito.mock(HallReservationService.class);
    }

    @Test
    public void getHallReservation() throws Exception {
        Long id = 1L;
        HallReservation hallReservation = getHallReservation(id);
        Mockito.when(hallReservationServiceMock.findById(id)).thenReturn(hallReservation);

        mockMvc.perform(get("/rest/v1/hallReservation/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.hallOptions").isArray())
                .andExpect(jsonPath("$.data.hallOptions", hasSize(2)))
                .andExpect(jsonPath("$..hallOptions[0].price").value(5.0))
                .andExpect(jsonPath("$..hallOptions[1].price").value(50.0));
    }

    @Test
    public void updateHallReservation() throws Exception {
        Long id = 1L;
        HallReservation hallReservation = getHallReservation(id);
        Mockito.when(hallReservationServiceMock.findById(id)).thenReturn(hallReservation);

        HallReservationAPIWrapper wrapper = new HallReservationAPIWrapper(hallReservation);
        
//        mockMvc.perform(put("/rest/v1/hallReservation/1").contentType(RestTestUtil.APPLICATION_JSON_UTF8)
//                .content(RestTestUtil.convertObjectToJSONContent(wrapper)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data").isNotEmpty())
//                .andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data").isMap())
//                .andExpect(jsonPath("$.data.hallOptions").isArray())
//                .andExpect(jsonPath("$.data.hallOptions", hasSize(2)))
//                .andExpect(jsonPath("$..hallOptions[0].price").value(5.0))
//                .andExpect(jsonPath("$..hallOptions[1].price").value(50.0));
    }

    private HallReservation getHallReservation(Long id) {
        Hall hall = new Hall();
        HallOption hallOption1 = new HallOption("Wifi", 5.00);
        HallOption hallOption2 = new HallOption("DJ", 50.00);

        // Create reservation
        HallReservation reservation = new ConcreteHallReservation(hall);
        reservation = new HallReservationOption(reservation, hallOption1);
        reservation = new HallReservationOption(reservation, hallOption2);
        reservation.setId(id);

        return reservation;
    }
}