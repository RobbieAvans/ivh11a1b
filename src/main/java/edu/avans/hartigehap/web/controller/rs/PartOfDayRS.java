package edu.avans.hartigehap.web.controller.rs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.avans.hartigehap.service.PartOfDayService;
import edu.avans.hartigehap.domain.Day;
import edu.avans.hartigehap.domain.DayPart;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class PartOfDayRS extends BaseRS{
    
    @Autowired
    private PartOfDayService partOfDayService;
    
    PartOfDayFactory factory = new PartOfDayFactory();
    
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/partofday/{hallId}/{weekNr}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView getAvailability(@PathVariable int hallId, @PathVariable int weekNr){
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[] parts = {"Morning","Afternoon","Evening"};
        List<Day> response = new ArrayList<>();
        List<PartOfDay> dayParts = partOfDayService.findByWeekAndHall(hallId, weekNr);
        
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setMinimalDaysInFirstWeek(4);
         
        int numWeekofYear = weekNr;  //INPUT
        int year = 2016;         //INPUT
         
        gregorianCalendar.set(Calendar.YEAR , year);
        gregorianCalendar.set(Calendar.WEEK_OF_YEAR , numWeekofYear);
        
        int i = 1;
        for (String dayName : days){
           Day day = new Day(dayName);
           day.setDate((gregorianCalendar.get(Calendar.DAY_OF_MONTH)+i -2)+ "-" + (gregorianCalendar.get(Calendar.MONTH)+ 1 ) + "-" + gregorianCalendar.get(Calendar.YEAR));
           for (String partName : parts){
               DayPart part = new DayPart(partName);
               for(PartOfDay x : dayParts){
                   if(x.getDescription().equals(part.getDescription()) &&Integer.parseInt(new SimpleDateFormat("u").format(x.getStartTime()))==i){
                       part.setAvailable(false);
                   }
               }
               day.addDayPart(part);
           }
           i++;
           response.add(day);    
        }
        return createSuccessResponse(response);
    }
    
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/partofday", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView CreatePartOfDay(Date date, String part){
        try {
            return createSuccessResponse(partOfDayService.save(factory.makePartOfDay(part, date)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return createErrorResponse("Error when creating a new PartOfDay");
        }
    }
    
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/partofday/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void DeletePartOfDay(@PathVariable Long id){
        partOfDayService.delete(id);
    }
    
    
}
