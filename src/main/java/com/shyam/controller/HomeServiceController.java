package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homeService")
@RequiredArgsConstructor
@Slf4j
public class HomeServiceController {
    private final HomeService homeService;

    @PostMapping("/getAllServiceRequests")
    public BaseResponseDTO<GetAllHomeServiceResponseDTO> getAllHomeServiceRequests(@RequestHeader String requestId) {
        log.info("Received request for getting all home service request for requestId : {}",requestId);
        var response = homeService.getAllHomeServiceRequests(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/getHomeServiceRequestById")
    public BaseResponseDTO<HomeServiceResponseDTO> getHomeServiceRequestById(
            @RequestHeader String requestId,
            @RequestBody HomeServiceRequestDTO homeServiceRequestDTO
    ) {
        log.info("Received request for getting home service request for requestId : {}",requestId);
        var response = homeService.getHomeServiceRequestById(requestId,homeServiceRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/createHomeServiceRequest")
    public BaseResponseDTO<CreateHomeServiceResponseDTO> createHomeServiceRequests(
            @RequestHeader String requestId,
            @RequestBody CreateHomeServiceRequestDTO createHomeServiceRequestDTO
    ) {
        log.info("Received request for create home service request for requestId : {}",requestId);
        var response = homeService.createHomeServiceRequests(requestId,createHomeServiceRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PutMapping("/editHomeServiceRequest")
    public BaseResponseDTO<EditHomeServiceResponseDTO> editHomeServiceRequest(
            @RequestHeader String requestId,
            @RequestBody EditHomeServiceRequestDTO editHomeServiceRequestDTO
    ) {
        log.info("Received request for edit home service request for requestId : {}",requestId);
        var response = homeService.editHomeServiceRequest(requestId,editHomeServiceRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/searchHomeServiceRequest")
    public BaseResponseDTO<GetAllHomeServiceResponseDTO> searchHomeServiceRequest(
            @RequestHeader String requestId,
            @RequestBody SearchHomeServiceRequestDTO searchHomeServiceRequestDTO
    ) {
        log.info("Received request for search home service request for requestId : {}",requestId);
        var response = homeService.searchHomeServiceRequest(requestId,searchHomeServiceRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @DeleteMapping("/deleteHomeServiceRequest")
    public BaseResponseDTO<DeleteHomeServiceResponseDTO> deleteHomeServiceRequest(
            @RequestHeader String requestId,
            @RequestBody DeleteHomeServiceRequestDTO editHomeServiceRequestDTO
    ) {
        log.info("Received request for delete home service request for requestId : {}",requestId);
        var response = homeService.deleteHomeServiceRequest(requestId,editHomeServiceRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/getAllUserServiceRequests")
    public BaseResponseDTO<GetAllHomeServiceResponseDTO> getAllUserServiceRequests(@RequestHeader String requestId) {
        log.info("Received request for getting all home service request for user with requestId : {}",requestId);
        var response = homeService.getAllUserServiceRequests(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

}
