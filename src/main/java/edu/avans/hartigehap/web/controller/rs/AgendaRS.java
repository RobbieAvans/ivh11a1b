package edu.avans.hartigehap.web.controller.rs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.avans.hartigehap.domain.agenda.AgendaItem;
import edu.avans.hartigehap.domain.agenda.Iterator;
import edu.avans.hartigehap.service.AgendaService;

@Controller
@RequestMapping (value = RSConstants.URL_PREFIX + "/agenda", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgendaRS extends BaseRS {
    
    @Autowired
    private AgendaService agendaService;
    
    @RequestMapping(value = "/{start}/{end}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView agendaItems(@PathVariable String start, @PathVariable String end) {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);
                  
            Iterator<AgendaItem> iterator = agendaService.getItemsBetween(startDate, endDate);
            
            // Fill array with all items
            List<AgendaItem> items = new ArrayList<>();
            while (iterator.hasNext()) {
                items.add(iterator.next());
            }
            
            return createSuccessResponse(items);
        } catch (ParseException e) {
            return createErrorResponse("invalid_date_format");
        }
    }

}
