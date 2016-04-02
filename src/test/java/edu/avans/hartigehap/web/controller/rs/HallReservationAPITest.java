package edu.avans.hartigehap.web.controller.rs;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Calendar;
import java.util.Date;

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

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.Manager;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import edu.avans.hartigehap.domain.PartOfDayFactoryImlp;
import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.domain.strategy.HallReservationPriceStrategyFactory;
import edu.avans.hartigehap.service.CustomerService;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.ManagerService;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;
import edu.avans.hartigehap.web.controller.rs.testutil.RestTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HallReservationAPITest.class })
@WebAppConfiguration
@ImportResource({ "classpath:/test-root-context.xml", "classpath:*servlet-context.xml" })
public class HallReservationAPITest {

    @Autowired
    private HallReservationRS hallReservationRS;

    @Autowired
    private CustomerService customerServiceMock;
    
    @Autowired
    private ManagerService managerServiceMock;
    
    @Autowired
    private HallReservationService hallReservationServiceMock;

    private MockMvc mockMvc;

    private static final String MANAGER_SESSION_ID = "imamanager";
    
    @Before
    public void setUp() {
        Mockito.reset(hallReservationServiceMock);
        Mockito.reset(customerServiceMock);
        Mockito.reset(managerServiceMock);
        
        Manager manager = new Manager();
        manager.setSessionID(MANAGER_SESSION_ID);
        
        Mockito.when(customerServiceMock.findBySessionID(MANAGER_SESSION_ID)).thenReturn(null);
        Mockito.when(managerServiceMock.findBySessionID(MANAGER_SESSION_ID)).thenReturn(manager);
        
        mockMvc = standaloneSetup(hallReservationRS).build();
    }

    @Bean
    public HallReservationService hallReservationService() {
        return Mockito.mock(HallReservationService.class);
    }
    
    @Bean
    public ManagerService managerService() {
        return Mockito.mock(ManagerService.class);
    }
    
    @Bean
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
    
    @Bean
    public HallReservationPriceStrategyFactory strategyFactory() {
        return Mockito.mock(HallReservationPriceStrategyFactory.class);
    }

    @Test
    public void getHallReservation() throws Exception {
        Long id = 1L;
        
        HallReservation hallReservation = getHallReservation(id);
        Mockito.when(hallReservationServiceMock.findById(id)).thenReturn(hallReservation);
        
        mockMvc.perform(get("/rest/v1/hallReservation/1/" + MANAGER_SESSION_ID)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.hallOptions").isArray())
                .andExpect(jsonPath("$.data.hallOptions", hasSize(2)))
                .andExpect(jsonPath("$..hallOptions[0].basePrice").value(5.0))
                .andExpect(jsonPath("$..hallOptions[1].basePrice").value(50.0));
    }

    @Test
    public void updateHallReservation() throws Exception {
        HallReservationRequest request = new HallReservationRequest();    

        Long id = 1L;
        HallReservation hallReservation = getHallReservation(id);
        Mockito.when(hallReservationServiceMock.findById(id)).thenReturn(hallReservation);
        Mockito.when(hallReservationServiceMock.update(any(HallReservation.class), any(HallReservationRequest.class)))
                .thenReturn(hallReservation);
                
        mockMvc.perform(put("/rest/v1/hallReservation/1/" + MANAGER_SESSION_ID).contentType(RestTestUtil.APPLICATION_JSON_UTF8)
                .content(RestTestUtil.convertObjectToJSONContent(request))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.hallOptions").isArray())
                .andExpect(jsonPath("$.data.hallOptions", hasSize(2)))
                .andExpect(jsonPath("$..hallOptions[0].basePrice").value(5.0))
                .andExpect(jsonPath("$..hallOptions[1].basePrice").value(50.0));
    }

    private HallReservation getHallReservation(Long id) {
        Hall hall = new Hall();
        
        HallOption hallOption1 = new HallOption("Wifi", 5.00);
        HallOption hallOption2 = new HallOption("DJ", 50.00);

        // Create reservation
        HallReservation reservation = new ConcreteHallReservation();
        reservation = new HallReservationOption(reservation, hallOption1);
        reservation = new HallReservationOption(reservation, hallOption2);
        
        reservation.setId(id);
        reservation.setHall(hall);
        
        
        // Get partOfDayObjects
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1); // Add one day so it is never in the past

        PartOfDayFactory factory = new PartOfDayFactoryImlp();
        reservation.addPartOfDay(factory.makePartOfDay("Evening", calendar.getTime()));
                
        return reservation;
    }
}