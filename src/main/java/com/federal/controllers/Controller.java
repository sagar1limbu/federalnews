package com.federal.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sagar on 4/17/17.
 */
@RestController
final class Controller {

    // Both RestTemplate and URI instances can be cached
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final URI testUri = URI.create("http://www.nepalipaisa.com/Modules/MarketMovers/Services/MarketMoversServices.asmx/GetTopBrokers");

    // I'm using GET just to simplify the testing using a web browser
    @RequestMapping(method = RequestMethod.GET, value = "/brokerreq")
    public void post(final ServletResponse response)
            throws IOException {
        // Create a POST request entity
        final RequestEntity<?> requestEntity = new RequestEntity<>(getRequest(1, 2000), HttpMethod.POST, testUri);
        // And send the request to the remote server
        final ResponseEntity<Resource> responseEntity = restTemplate.exchange(requestEntity, Resource.class);
        // Now just copy the response input stream to the output stream of this controller
        try ( final InputStream inputStream = responseEntity.getBody().getInputStream() ) {
            // Or provide HttpServletResponse via the controller method to be able to configure the response more accurately
            StreamUtils.copy(inputStream, response.getOutputStream());
        }
    }

    private static Object getRequest(final int offset, final int limit) {
        final Map<String, Object> request = new HashMap<>();
        request.put("offset", offset);
        request.put("limit", limit);
        return request;
    }

}
@ControllerAdvice
final class ExceptionControllerAdvice {

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseBody
    public ResponseEntity<?> handleHttpServerErrorException(final HttpServerErrorException ex) {
        return new ResponseEntity<Object>("Bad gateway: " + ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }

}